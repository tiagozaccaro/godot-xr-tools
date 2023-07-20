package com.google.android.vending.licensing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.vending.licensing.ILicenseResultListener;
import com.android.vending.licensing.ILicensingService;
import com.google.android.vending.licensing.util.Base64;
import com.google.android.vending.licensing.util.Base64DecoderException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/* loaded from: classes.dex */
public class LicenseChecker implements ServiceConnection {
    private static final boolean DEBUG_LICENSE_ERROR = false;
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String TAG = "LicenseChecker";
    private static final int TIMEOUT_MS = 10000;
    private final Context mContext;
    private Handler mHandler;
    private final String mPackageName;
    private final Policy mPolicy;
    private PublicKey mPublicKey;
    private ILicensingService mService;
    private final String mVersionCode;
    private final Set<LicenseValidator> mChecksInProgress = new HashSet();
    private final Queue<LicenseValidator> mPendingChecks = new LinkedList();

    public LicenseChecker(Context context, Policy policy, String encodedPublicKey) {
        this.mContext = context;
        this.mPolicy = policy;
        this.mPublicKey = generatePublicKey(encodedPublicKey);
        String packageName = context.getPackageName();
        this.mPackageName = packageName;
        this.mVersionCode = getVersionCode(context, packageName);
        HandlerThread handlerThread = new HandlerThread("background thread");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    private static PublicKey generatePublicKey(String encodedPublicKey) {
        try {
            byte[] decodedKey = Base64.decode(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (Base64DecoderException e) {
            Log.e(TAG, "Could not decode from Base64.");
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        } catch (InvalidKeySpecException e3) {
            Log.e(TAG, "Invalid key specification.");
            throw new IllegalArgumentException(e3);
        }
    }

    public synchronized void checkAccess(LicenseCheckerCallback callback) {
        if (this.mPolicy.allowAccess()) {
            Log.i(TAG, "Using cached license response");
            callback.allow(256);
        } else {
            LicenseValidator validator = new LicenseValidator(this.mPolicy, new NullDeviceLimiter(), callback, generateNonce(), this.mPackageName, this.mVersionCode);
            if (this.mService == null) {
                Log.i(TAG, "Binding to licensing service.");
                try {
                    boolean bindResult = this.mContext.bindService(new Intent(new String(Base64.decode("Y29tLmFuZHJvaWQudmVuZGluZy5saWNlbnNpbmcuSUxpY2Vuc2luZ1NlcnZpY2U="))).setPackage(new String(Base64.decode("Y29tLmFuZHJvaWQudmVuZGluZw=="))), this, 1);
                    if (bindResult) {
                        this.mPendingChecks.offer(validator);
                    } else {
                        Log.e(TAG, "Could not bind to service.");
                        handleServiceConnectionError(validator);
                    }
                } catch (Base64DecoderException e) {
                    e.printStackTrace();
                } catch (SecurityException e2) {
                    callback.applicationError(6);
                }
            } else {
                this.mPendingChecks.offer(validator);
                runChecks();
            }
        }
    }

    public void followLastLicensingUrl(Context context) {
        String licensingUrl = this.mPolicy.getLicensingUrl();
        if (licensingUrl == null) {
            licensingUrl = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
        }
        Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse(licensingUrl));
        context.startActivity(marketIntent);
    }

    private void runChecks() {
        while (true) {
            LicenseValidator validator = this.mPendingChecks.poll();
            if (validator != null) {
                try {
                    Log.i(TAG, "Calling checkLicense on service for " + validator.getPackageName());
                    this.mService.checkLicense(validator.getNonce(), validator.getPackageName(), new ResultListener(validator));
                    this.mChecksInProgress.add(validator);
                } catch (RemoteException e) {
                    Log.w(TAG, "RemoteException in checkLicense call.", e);
                    handleServiceConnectionError(validator);
                }
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void finishCheck(LicenseValidator validator) {
        this.mChecksInProgress.remove(validator);
        if (this.mChecksInProgress.isEmpty()) {
            cleanupService();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ResultListener extends ILicenseResultListener.Stub {
        private static final int ERROR_CONTACTING_SERVER = 257;
        private static final int ERROR_INVALID_PACKAGE_NAME = 258;
        private static final int ERROR_NON_MATCHING_UID = 259;
        private Runnable mOnTimeout;
        private final LicenseValidator mValidator;

        public ResultListener(LicenseValidator validator) {
            this.mValidator = validator;
            this.mOnTimeout = new Runnable() { // from class: com.google.android.vending.licensing.LicenseChecker.ResultListener.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.i(LicenseChecker.TAG, "Check timed out.");
                    LicenseChecker.this.handleServiceConnectionError(ResultListener.this.mValidator);
                    LicenseChecker.this.finishCheck(ResultListener.this.mValidator);
                }
            };
            startTimeout();
        }

        @Override // com.android.vending.licensing.ILicenseResultListener
        public void verifyLicense(final int responseCode, final String signedData, final String signature) {
            LicenseChecker.this.mHandler.post(new Runnable() { // from class: com.google.android.vending.licensing.LicenseChecker.ResultListener.2
                @Override // java.lang.Runnable
                public void run() {
                    Log.i(LicenseChecker.TAG, "Received response.");
                    if (LicenseChecker.this.mChecksInProgress.contains(ResultListener.this.mValidator)) {
                        ResultListener.this.clearTimeout();
                        ResultListener.this.mValidator.verify(LicenseChecker.this.mPublicKey, responseCode, signedData, signature);
                        LicenseChecker.this.finishCheck(ResultListener.this.mValidator);
                    }
                }
            });
        }

        private void startTimeout() {
            Log.i(LicenseChecker.TAG, "Start monitoring timeout.");
            LicenseChecker.this.mHandler.postDelayed(this.mOnTimeout, 10000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeout() {
            Log.i(LicenseChecker.TAG, "Clearing timeout.");
            LicenseChecker.this.mHandler.removeCallbacks(this.mOnTimeout);
        }
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName name, IBinder service) {
        this.mService = ILicensingService.Stub.asInterface(service);
        runChecks();
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceDisconnected(ComponentName name) {
        Log.w(TAG, "Service unexpectedly disconnected.");
        this.mService = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleServiceConnectionError(LicenseValidator validator) {
        this.mPolicy.processServerResponse(Policy.RETRY, null);
        if (this.mPolicy.allowAccess()) {
            validator.getCallback().allow(Policy.RETRY);
        } else {
            validator.getCallback().dontAllow(Policy.RETRY);
        }
    }

    private void cleanupService() {
        if (this.mService != null) {
            try {
                this.mContext.unbindService(this);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Unable to unbind from licensing service (already unbound)");
            }
            this.mService = null;
        }
    }

    public synchronized void onDestroy() {
        cleanupService();
        this.mHandler.getLooper().quit();
    }

    private int generateNonce() {
        return RANDOM.nextInt();
    }

    private static String getVersionCode(Context context, String packageName) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(packageName, 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package not found. could not get version code.");
            return "";
        }
    }
}