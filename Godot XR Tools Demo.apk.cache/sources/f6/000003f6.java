package com.google.android.vending.expansion.downloader.impl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.android.vending.expansion.downloader.Constants;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.APKExpansionPolicy;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import java.io.File;

/* loaded from: classes.dex */
public abstract class DownloaderService extends CustomIntentService implements IDownloaderService {
    public static final String ACTION_DOWNLOADS_CHANGED = "downloadsChanged";
    public static final String ACTION_DOWNLOAD_COMPLETE = "lvldownloader.intent.action.DOWNLOAD_COMPLETE";
    public static final String ACTION_DOWNLOAD_STATUS = "lvldownloader.intent.action.DOWNLOAD_STATUS";
    public static final int CONTROL_PAUSED = 1;
    public static final int CONTROL_RUN = 0;
    public static final int DOWNLOAD_REQUIRED = 2;
    public static final String EXTRA_FILE_NAME = "downloadId";
    public static final String EXTRA_IS_WIFI_REQUIRED = "isWifiRequired";
    public static final String EXTRA_MESSAGE_HANDLER = "EMH";
    public static final String EXTRA_PACKAGE_NAME = "EPN";
    public static final String EXTRA_PENDING_INTENT = "EPI";
    public static final String EXTRA_STATUS_CURRENT_FILE_SIZE = "CFS";
    public static final String EXTRA_STATUS_CURRENT_PROGRESS = "CFP";
    public static final String EXTRA_STATUS_STATE = "ESS";
    public static final String EXTRA_STATUS_TOTAL_PROGRESS = "TFP";
    public static final String EXTRA_STATUS_TOTAL_SIZE = "ETS";
    private static final String LOG_TAG = "LVLDL";
    public static final int LVL_CHECK_REQUIRED = 1;
    public static final int NETWORK_CANNOT_USE_ROAMING = 5;
    public static final int NETWORK_MOBILE = 1;
    public static final int NETWORK_NO_CONNECTION = 2;
    public static final int NETWORK_OK = 1;
    public static final int NETWORK_RECOMMENDED_UNUSABLE_DUE_TO_SIZE = 4;
    public static final int NETWORK_TYPE_DISALLOWED_BY_REQUESTOR = 6;
    public static final int NETWORK_UNUSABLE_DUE_TO_SIZE = 3;
    public static final int NETWORK_WIFI = 2;
    public static final int NO_DOWNLOAD_REQUIRED = 0;
    private static final float SMOOTHING_FACTOR = 0.005f;
    public static final int STATUS_CANCELED = 490;
    public static final int STATUS_CANNOT_RESUME = 489;
    public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 499;
    public static final int STATUS_FILE_ALREADY_EXISTS_ERROR = 488;
    public static final int STATUS_FILE_DELIVERED_INCORRECTLY = 487;
    public static final int STATUS_FILE_ERROR = 492;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_HTTP_DATA_ERROR = 495;
    public static final int STATUS_HTTP_EXCEPTION = 496;
    public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 498;
    public static final int STATUS_PAUSED_BY_APP = 193;
    public static final int STATUS_PENDING = 190;
    public static final int STATUS_QUEUED_FOR_WIFI = 197;
    public static final int STATUS_QUEUED_FOR_WIFI_OR_CELLULAR_PERMISSION = 196;
    public static final int STATUS_RUNNING = 192;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_TOO_MANY_REDIRECTS = 497;
    public static final int STATUS_UNHANDLED_HTTP_CODE = 494;
    public static final int STATUS_UNHANDLED_REDIRECT = 493;
    public static final int STATUS_UNKNOWN_ERROR = 491;
    public static final int STATUS_WAITING_FOR_NETWORK = 195;
    public static final int STATUS_WAITING_TO_RETRY = 194;
    private static final String TEMP_EXT = ".tmp";
    public static final int VISIBILITY_HIDDEN = 2;
    public static final int VISIBILITY_VISIBLE = 0;
    public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
    private static boolean sIsRunning;
    private PendingIntent mAlarmIntent;
    float mAverageDownloadSpeed;
    long mBytesAtSample;
    long mBytesSoFar;
    private Messenger mClientMessenger;
    private BroadcastReceiver mConnReceiver;
    private ConnectivityManager mConnectivityManager;
    private int mControl;
    int mFileCount;
    private boolean mIsAtLeast3G;
    private boolean mIsAtLeast4G;
    private boolean mIsCellularConnection;
    private boolean mIsConnected;
    private boolean mIsFailover;
    private boolean mIsRoaming;
    long mMillisecondsAtSample;
    private DownloadNotification mNotification;
    private PackageInfo mPackageInfo;
    private PendingIntent mPendingIntent;
    private final Messenger mServiceMessenger;
    private final IStub mServiceStub;
    private boolean mStateChanged;
    private int mStatus;
    long mTotalLength;
    private WifiManager mWifiManager;

    /* renamed from: -$$Nest$smisServiceRunning  reason: not valid java name */
    static /* bridge */ /* synthetic */ boolean m13$$Nest$smisServiceRunning() {
        return isServiceRunning();
    }

    public abstract String getAlarmReceiverClassName();

    public abstract String getPublicKey();

    public abstract byte[] getSALT();

    public DownloaderService() {
        super("LVLDownloadService");
        IStub CreateStub = DownloaderServiceMarshaller.CreateStub(this);
        this.mServiceStub = CreateStub;
        this.mServiceMessenger = CreateStub.getMessenger();
    }

    public static boolean isStatusInformational(int status) {
        return status >= 100 && status < 200;
    }

    public static boolean isStatusSuccess(int status) {
        return status >= 200 && status < 300;
    }

    public static boolean isStatusError(int status) {
        return status >= 400 && status < 600;
    }

    public static boolean isStatusClientError(int status) {
        return status >= 400 && status < 500;
    }

    public static boolean isStatusServerError(int status) {
        return status >= 500 && status < 600;
    }

    public static boolean isStatusCompleted(int status) {
        return (status >= 200 && status < 300) || (status >= 400 && status < 600);
    }

    @Override // com.google.android.vending.expansion.downloader.impl.CustomIntentService, android.app.Service
    public IBinder onBind(Intent paramIntent) {
        Log.d("LVLDL", "Service Bound");
        return this.mServiceMessenger.getBinder();
    }

    public boolean isWiFi() {
        return this.mIsConnected && !this.mIsCellularConnection;
    }

    private void updateNetworkType(int type, int subType) {
        switch (type) {
            case 0:
                this.mIsCellularConnection = true;
                switch (subType) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        this.mIsAtLeast3G = false;
                        this.mIsAtLeast4G = false;
                        return;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                        this.mIsAtLeast3G = true;
                        this.mIsAtLeast4G = false;
                        return;
                    case 12:
                    default:
                        this.mIsCellularConnection = false;
                        this.mIsAtLeast3G = false;
                        this.mIsAtLeast4G = false;
                        return;
                    case 13:
                    case 14:
                    case 15:
                        this.mIsAtLeast3G = true;
                        this.mIsAtLeast4G = true;
                        return;
                }
            case 1:
            case 7:
            case 9:
                this.mIsCellularConnection = false;
                this.mIsAtLeast3G = false;
                this.mIsAtLeast4G = false;
                return;
            case 6:
                this.mIsCellularConnection = true;
                this.mIsAtLeast3G = true;
                this.mIsAtLeast4G = true;
                return;
            default:
                return;
        }
    }

    private void updateNetworkState(NetworkInfo info) {
        boolean isConnected = this.mIsConnected;
        boolean isFailover = this.mIsFailover;
        boolean isCellularConnection = this.mIsCellularConnection;
        boolean isRoaming = this.mIsRoaming;
        boolean isAtLeast3G = this.mIsAtLeast3G;
        boolean z = false;
        if (info != null) {
            this.mIsRoaming = info.isRoaming();
            this.mIsFailover = info.isFailover();
            this.mIsConnected = info.isConnected();
            updateNetworkType(info.getType(), info.getSubtype());
        } else {
            this.mIsRoaming = false;
            this.mIsFailover = false;
            this.mIsConnected = false;
            updateNetworkType(-1, -1);
        }
        this.mStateChanged = (!this.mStateChanged && isConnected == this.mIsConnected && isFailover == this.mIsFailover && isCellularConnection == this.mIsCellularConnection && isRoaming == this.mIsRoaming && isAtLeast3G == this.mIsAtLeast3G) ? true : true;
    }

    void pollNetworkState() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) getSystemService("connectivity");
        }
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
        }
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager == null) {
            Log.w("LVLDL", "couldn't get connectivity manager to poll network state");
            return;
        }
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        updateNetworkState(activeInfo);
    }

    private static boolean isLVLCheckRequired(DownloadsDB db, PackageInfo pi) {
        if (db.mVersionCode != pi.versionCode) {
            return true;
        }
        return false;
    }

    private static synchronized boolean isServiceRunning() {
        boolean z;
        synchronized (DownloaderService.class) {
            z = sIsRunning;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void setServiceRunning(boolean isRunning) {
        synchronized (DownloaderService.class) {
            sIsRunning = isRunning;
        }
    }

    public static int startDownloadServiceIfRequired(Context context, Intent intent, Class<?> serviceClass) throws PackageManager.NameNotFoundException {
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra(EXTRA_PENDING_INTENT);
        return startDownloadServiceIfRequired(context, pendingIntent, serviceClass);
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent pendingIntent, Class<?> serviceClass) throws PackageManager.NameNotFoundException {
        String packageName = context.getPackageName();
        String className = serviceClass.getName();
        return startDownloadServiceIfRequired(context, pendingIntent, packageName, className);
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent pendingIntent, String classPackage, String className) throws PackageManager.NameNotFoundException {
        int i = 0;
        PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        int status = 0;
        DownloadsDB db = DownloadsDB.getDB(context);
        if (isLVLCheckRequired(db, pi)) {
            status = 1;
        }
        if (db.mStatus == 0) {
            DownloadInfo[] infos = db.getDownloads();
            if (infos != null) {
                int length = infos.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    DownloadInfo info = infos[i];
                    if (Helpers.doesFileExist(context, info.mFileName, info.mTotalBytes, true)) {
                        i++;
                    } else {
                        status = 2;
                        db.updateStatus(-1);
                        break;
                    }
                }
            }
        } else {
            status = 2;
        }
        switch (status) {
            case 1:
            case 2:
                Intent fileIntent = new Intent();
                fileIntent.setClassName(classPackage, className);
                fileIntent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
                context.startService(fileIntent);
                break;
        }
        return status;
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderService
    public void requestAbortDownload() {
        this.mControl = 1;
        this.mStatus = 490;
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderService
    public void requestPauseDownload() {
        this.mControl = 1;
        this.mStatus = STATUS_PAUSED_BY_APP;
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderService
    public void setDownloadFlags(int flags) {
        DownloadsDB.getDB(this).updateFlags(flags);
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderService
    public void requestContinueDownload() {
        if (this.mControl == 1) {
            this.mControl = 0;
        }
        Intent fileIntent = new Intent(this, getClass());
        fileIntent.putExtra(EXTRA_PENDING_INTENT, this.mPendingIntent);
        startService(fileIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class LVLRunnable implements Runnable {
        final Context mContext;

        LVLRunnable(Context context, PendingIntent intent) {
            this.mContext = context;
            DownloaderService.this.mPendingIntent = intent;
        }

        @Override // java.lang.Runnable
        public void run() {
            DownloaderService.setServiceRunning(true);
            DownloaderService.this.mNotification.onDownloadStateChanged(2);
            String deviceId = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
            final APKExpansionPolicy aep = new APKExpansionPolicy(this.mContext, new AESObfuscator(DownloaderService.this.getSALT(), this.mContext.getPackageName(), deviceId));
            aep.resetPolicy();
            LicenseChecker checker = new LicenseChecker(this.mContext, aep, DownloaderService.this.getPublicKey());
            checker.checkAccess(new LicenseCheckerCallback() { // from class: com.google.android.vending.expansion.downloader.impl.DownloaderService.LVLRunnable.1
                @Override // com.google.android.vending.licensing.LicenseCheckerCallback
                public void allow(int reason) {
                    int status;
                    try {
                        int count = aep.getExpansionURLCount();
                        DownloadsDB db = DownloadsDB.getDB(LVLRunnable.this.mContext);
                        int status2 = 0;
                        if (count == 0) {
                            status = 0;
                        } else {
                            for (int i = 0; i < count; i++) {
                                String currentFileName = aep.getExpansionFileName(i);
                                if (currentFileName != null) {
                                    DownloadInfo di = new DownloadInfo(i, currentFileName, LVLRunnable.this.mContext.getPackageName());
                                    long fileSize = aep.getExpansionFileSize(i);
                                    if (DownloaderService.this.handleFileUpdated(db, i, currentFileName, fileSize)) {
                                        status2 |= -1;
                                        di.resetDownload();
                                        di.mUri = aep.getExpansionURL(i);
                                        di.mTotalBytes = fileSize;
                                        di.mStatus = status2;
                                        db.updateDownload(di);
                                    } else {
                                        DownloadInfo dbdi = db.getDownloadInfoByFileName(di.mFileName);
                                        if (dbdi == null) {
                                            Log.d("LVLDL", "file " + di.mFileName + " found. Not downloading.");
                                            di.mStatus = 200;
                                            di.mTotalBytes = fileSize;
                                            di.mCurrentBytes = fileSize;
                                            di.mUri = aep.getExpansionURL(i);
                                            db.updateDownload(di);
                                        } else if (dbdi.mStatus != 200) {
                                            dbdi.mUri = aep.getExpansionURL(i);
                                            db.updateDownload(dbdi);
                                            status2 |= -1;
                                        }
                                    }
                                }
                            }
                            status = status2;
                        }
                        try {
                            PackageInfo pi = LVLRunnable.this.mContext.getPackageManager().getPackageInfo(LVLRunnable.this.mContext.getPackageName(), 0);
                            db.updateMetadata(pi.versionCode, status);
                            Class<?> serviceClass = DownloaderService.this.getClass();
                            switch (DownloaderService.startDownloadServiceIfRequired(LVLRunnable.this.mContext, DownloaderService.this.mPendingIntent, serviceClass)) {
                                case 0:
                                    DownloaderService.this.mNotification.onDownloadStateChanged(5);
                                    break;
                                case 1:
                                    Log.e("LVLDL", "In LVL checking loop!");
                                    DownloaderService.this.mNotification.onDownloadStateChanged(15);
                                    throw new RuntimeException("Error with LVL checking and database integrity");
                            }
                        } catch (PackageManager.NameNotFoundException e1) {
                            e1.printStackTrace();
                            throw new RuntimeException("Error with getting information from package name");
                        }
                    } finally {
                        DownloaderService.setServiceRunning(false);
                    }
                }

                @Override // com.google.android.vending.licensing.LicenseCheckerCallback
                public void dontAllow(int reason) {
                    try {
                        switch (reason) {
                            case Policy.RETRY /* 291 */:
                                DownloaderService.this.mNotification.onDownloadStateChanged(16);
                                break;
                            case Policy.NOT_LICENSED /* 561 */:
                                DownloaderService.this.mNotification.onDownloadStateChanged(15);
                                break;
                            default:
                        }
                    } finally {
                        DownloaderService.setServiceRunning(false);
                    }
                }

                @Override // com.google.android.vending.licensing.LicenseCheckerCallback
                public void applicationError(int errorCode) {
                    try {
                        DownloaderService.this.mNotification.onDownloadStateChanged(16);
                    } finally {
                        DownloaderService.setServiceRunning(false);
                    }
                }
            });
        }
    }

    public void updateLVL(Context context) {
        Context c = context.getApplicationContext();
        Handler h = new Handler(c.getMainLooper());
        h.post(new LVLRunnable(c, this.mPendingIntent));
    }

    public boolean handleFileUpdated(DownloadsDB db, int index, String filename, long fileSize) {
        String oldFile;
        DownloadInfo di = db.getDownloadInfoByFileName(filename);
        if (di != null && (oldFile = di.mFileName) != null) {
            if (filename.equals(oldFile)) {
                return false;
            }
            String deleteFile = Helpers.generateSaveFileName(this, oldFile);
            File f = new File(deleteFile);
            if (f.exists()) {
                f.delete();
            }
        }
        return true ^ Helpers.doesFileExist(this, filename, fileSize, true);
    }

    private void scheduleAlarm(long wakeUp) {
        AlarmManager alarms = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarms == null) {
            Log.e("LVLDL", "couldn't get alarm manager");
            return;
        }
        String className = getAlarmReceiverClassName();
        Intent intent = new Intent(Constants.ACTION_RETRY);
        intent.putExtra(EXTRA_PENDING_INTENT, this.mPendingIntent);
        intent.setClassName(getPackageName(), className);
        this.mAlarmIntent = PendingIntent.getBroadcast(this, 0, intent, 1073741824);
        alarms.set(0, System.currentTimeMillis() + wakeUp, this.mAlarmIntent);
    }

    private void cancelAlarms() {
        if (this.mAlarmIntent != null) {
            AlarmManager alarms = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarms == null) {
                Log.e("LVLDL", "couldn't get alarm manager");
                return;
            }
            alarms.cancel(this.mAlarmIntent);
            this.mAlarmIntent = null;
        }
    }

    /* loaded from: classes.dex */
    private class InnerBroadcastReceiver extends BroadcastReceiver {
        final Service mService;

        InnerBroadcastReceiver(Service service) {
            this.mService = service;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            DownloaderService.this.pollNetworkState();
            if (DownloaderService.this.mStateChanged && !DownloaderService.m13$$Nest$smisServiceRunning()) {
                Log.d("LVLDL", "InnerBroadcastReceiver Called");
                Intent fileIntent = new Intent(context, this.mService.getClass());
                fileIntent.putExtra(DownloaderService.EXTRA_PENDING_INTENT, DownloaderService.this.mPendingIntent);
                context.startService(fileIntent);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0110 A[Catch: all -> 0x0139, TryCatch #0 {all -> 0x0139, blocks: (B:5:0x000f, B:7:0x0017, B:11:0x0028, B:13:0x0030, B:16:0x0037, B:19:0x004a, B:21:0x0050, B:23:0x005a, B:24:0x005e, B:25:0x0071, B:27:0x0078, B:28:0x0090, B:30:0x0094, B:32:0x009c, B:33:0x00b1, B:34:0x00b7, B:54:0x0110, B:56:0x011a, B:55:0x0117, B:39:0x00ca, B:40:0x00d5, B:44:0x00df, B:45:0x00f4, B:47:0x00f9, B:59:0x0123, B:8:0x001f, B:10:0x0023, B:62:0x012e), top: B:71:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0117 A[Catch: all -> 0x0139, TryCatch #0 {all -> 0x0139, blocks: (B:5:0x000f, B:7:0x0017, B:11:0x0028, B:13:0x0030, B:16:0x0037, B:19:0x004a, B:21:0x0050, B:23:0x005a, B:24:0x005e, B:25:0x0071, B:27:0x0078, B:28:0x0090, B:30:0x0094, B:32:0x009c, B:33:0x00b1, B:34:0x00b7, B:54:0x0110, B:56:0x011a, B:55:0x0117, B:39:0x00ca, B:40:0x00d5, B:44:0x00df, B:45:0x00f4, B:47:0x00f9, B:59:0x0123, B:8:0x001f, B:10:0x0023, B:62:0x012e), top: B:71:0x000f }] */
    @Override // com.google.android.vending.expansion.downloader.impl.CustomIntentService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onHandleIntent(android.content.Intent r17) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.vending.expansion.downloader.impl.DownloaderService.onHandleIntent(android.content.Intent):void");
    }

    @Override // com.google.android.vending.expansion.downloader.impl.CustomIntentService, android.app.Service
    public void onDestroy() {
        BroadcastReceiver broadcastReceiver = this.mConnReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            this.mConnReceiver = null;
        }
        this.mServiceStub.disconnect(this);
        super.onDestroy();
    }

    public int getNetworkAvailabilityState(DownloadsDB db) {
        if (this.mIsConnected) {
            if (this.mIsCellularConnection) {
                int flags = db.mFlags;
                if (this.mIsRoaming) {
                    return 5;
                }
                return (flags & 1) != 0 ? 1 : 6;
            }
            return 1;
        }
        return 2;
    }

    @Override // com.google.android.vending.expansion.downloader.impl.CustomIntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            this.mPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            ApplicationInfo ai = getApplicationInfo();
            CharSequence applicationLabel = getPackageManager().getApplicationLabel(ai);
            this.mNotification = new DownloadNotification(this, applicationLabel);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes.dex */
    public static class GenerateSaveFileError extends Exception {
        private static final long serialVersionUID = 3465966015408936540L;
        String mMessage;
        int mStatus;

        public GenerateSaveFileError(int status, String message) {
            this.mStatus = status;
            this.mMessage = message;
        }
    }

    public String generateTempSaveFileName(String fileName) {
        String path = Helpers.getSaveFilePath(this) + File.separator + fileName + TEMP_EXT;
        return path;
    }

    public String generateSaveFile(String filename, long filesize) throws GenerateSaveFileError {
        String path = generateTempSaveFileName(filename);
        File expPath = new File(path);
        if (!Helpers.isExternalMediaMounted()) {
            Log.d("LVLDL", "External media not mounted: " + path);
            throw new GenerateSaveFileError(499, "external media is not yet mounted");
        } else if (expPath.exists()) {
            Log.d("LVLDL", "File already exists: " + path);
            throw new GenerateSaveFileError(488, "requested destination file already exists");
        } else if (Helpers.getAvailableBytes(Helpers.getFilesystemRoot(path)) < filesize) {
            throw new GenerateSaveFileError(498, "insufficient space on external storage");
        } else {
            return path;
        }
    }

    public String getLogMessageForNetworkError(int networkError) {
        switch (networkError) {
            case 2:
                return "no network connection available";
            case 3:
                return "download size exceeds limit for mobile network";
            case 4:
                return "download size exceeds recommended limit for mobile network";
            case 5:
                return "download cannot use the current network connection because it is roaming";
            case 6:
                return "download was requested to not use the current network type";
            default:
                return "unknown error with network connectivity";
        }
    }

    public int getControl() {
        return this.mControl;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void notifyUpdateBytes(long totalBytesSoFar) {
        long timeRemaining;
        long currentTime = SystemClock.uptimeMillis();
        long j = this.mMillisecondsAtSample;
        if (0 != j) {
            long timePassed = currentTime - j;
            long bytesInSample = totalBytesSoFar - this.mBytesAtSample;
            float currentSpeedSample = ((float) bytesInSample) / ((float) timePassed);
            float f = this.mAverageDownloadSpeed;
            if (0.0f != f) {
                this.mAverageDownloadSpeed = (SMOOTHING_FACTOR * currentSpeedSample) + (f * 0.995f);
            } else {
                this.mAverageDownloadSpeed = currentSpeedSample;
            }
            long timePassed2 = ((float) (this.mTotalLength - totalBytesSoFar)) / this.mAverageDownloadSpeed;
            timeRemaining = timePassed2;
        } else {
            timeRemaining = -1;
        }
        this.mMillisecondsAtSample = currentTime;
        this.mBytesAtSample = totalBytesSoFar;
        this.mNotification.onDownloadProgress(new DownloadProgressInfo(this.mTotalLength, totalBytesSoFar, timeRemaining, this.mAverageDownloadSpeed));
    }

    @Override // com.google.android.vending.expansion.downloader.impl.CustomIntentService
    protected boolean shouldStop() {
        DownloadsDB db = DownloadsDB.getDB(this);
        if (db.mStatus == 0) {
            return true;
        }
        return false;
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderService
    public void requestDownloadStatus() {
        this.mNotification.resendState();
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderService
    public void onClientUpdated(Messenger clientMessenger) {
        this.mClientMessenger = clientMessenger;
        this.mNotification.setMessenger(clientMessenger);
    }
}