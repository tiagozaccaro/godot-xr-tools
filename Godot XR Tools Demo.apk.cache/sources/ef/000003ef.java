package com.google.android.vending.expansion.downloader.impl;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Messenger;
import androidx.core.app.NotificationCompat;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import org.godotengine.godot.R;

/* loaded from: classes.dex */
public class DownloadNotification implements IDownloaderClient {
    static final String LOGTAG = "DownloadNotification";
    static final int NOTIFICATION_ID = LOGTAG.hashCode();
    private NotificationCompat.Builder mActiveDownloadBuilder;
    private NotificationCompat.Builder mBuilder;
    private IDownloaderClient mClientProxy;
    private PendingIntent mContentIntent;
    private final Context mContext;
    private NotificationCompat.Builder mCurrentBuilder;
    private String mCurrentText;
    private CharSequence mCurrentTitle;
    private CharSequence mLabel;
    private final NotificationManager mNotificationManager;
    private DownloadProgressInfo mProgressInfo;
    private int mState = -1;

    public PendingIntent getClientIntent() {
        return this.mContentIntent;
    }

    public void setClientIntent(PendingIntent clientIntent) {
        this.mBuilder.setContentIntent(clientIntent);
        this.mActiveDownloadBuilder.setContentIntent(clientIntent);
        this.mContentIntent = clientIntent;
    }

    public void resendState() {
        IDownloaderClient iDownloaderClient = this.mClientProxy;
        if (iDownloaderClient != null) {
            iDownloaderClient.onDownloadStateChanged(this.mState);
        }
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderClient
    public void onDownloadStateChanged(int newState) {
        int iconResource;
        int stringDownloadID;
        boolean ongoingEvent;
        IDownloaderClient iDownloaderClient = this.mClientProxy;
        if (iDownloaderClient != null) {
            iDownloaderClient.onDownloadStateChanged(newState);
        }
        if (newState != this.mState) {
            this.mState = newState;
            if (newState == 1 || this.mContentIntent == null) {
                return;
            }
            switch (newState) {
                case 0:
                    iconResource = 17301642;
                    stringDownloadID = R.string.state_unknown;
                    ongoingEvent = false;
                    break;
                case 1:
                case 6:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                default:
                    iconResource = 17301642;
                    stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                    ongoingEvent = true;
                    break;
                case 2:
                case 3:
                    iconResource = 17301634;
                    stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                    ongoingEvent = true;
                    break;
                case 4:
                    iconResource = 17301633;
                    stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                    ongoingEvent = true;
                    break;
                case 5:
                case 7:
                    iconResource = 17301634;
                    stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                    ongoingEvent = false;
                    break;
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    iconResource = 17301642;
                    stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                    ongoingEvent = false;
                    break;
            }
            this.mCurrentText = this.mContext.getString(stringDownloadID);
            this.mCurrentTitle = this.mLabel;
            this.mCurrentBuilder.setTicker(((Object) this.mLabel) + ": " + this.mCurrentText);
            this.mCurrentBuilder.setSmallIcon(iconResource);
            this.mCurrentBuilder.setContentTitle(this.mCurrentTitle);
            this.mCurrentBuilder.setContentText(this.mCurrentText);
            if (ongoingEvent) {
                this.mCurrentBuilder.setOngoing(true);
            } else {
                this.mCurrentBuilder.setOngoing(false);
                this.mCurrentBuilder.setAutoCancel(true);
            }
            this.mNotificationManager.notify(NOTIFICATION_ID, this.mCurrentBuilder.build());
        }
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderClient
    public void onDownloadProgress(DownloadProgressInfo progress) {
        this.mProgressInfo = progress;
        IDownloaderClient iDownloaderClient = this.mClientProxy;
        if (iDownloaderClient != null) {
            iDownloaderClient.onDownloadProgress(progress);
        }
        if (progress.mOverallTotal <= 0) {
            this.mBuilder.setTicker(this.mCurrentTitle);
            this.mBuilder.setSmallIcon(17301633);
            this.mBuilder.setContentTitle(this.mCurrentTitle);
            this.mBuilder.setContentText(this.mCurrentText);
            this.mCurrentBuilder = this.mBuilder;
        } else {
            this.mActiveDownloadBuilder.setProgress((int) progress.mOverallTotal, (int) progress.mOverallProgress, false);
            this.mActiveDownloadBuilder.setContentText(Helpers.getDownloadProgressString(progress.mOverallProgress, progress.mOverallTotal));
            this.mActiveDownloadBuilder.setSmallIcon(17301633);
            this.mActiveDownloadBuilder.setTicker(((Object) this.mLabel) + ": " + this.mCurrentText);
            this.mActiveDownloadBuilder.setContentTitle(this.mLabel);
            this.mActiveDownloadBuilder.setContentInfo(this.mContext.getString(R.string.time_remaining_notification, Helpers.getTimeRemaining(progress.mTimeRemaining)));
            this.mCurrentBuilder = this.mActiveDownloadBuilder;
        }
        this.mNotificationManager.notify(NOTIFICATION_ID, this.mCurrentBuilder.build());
    }

    public void setMessenger(Messenger msg) {
        IDownloaderClient CreateProxy = DownloaderClientMarshaller.CreateProxy(msg);
        this.mClientProxy = CreateProxy;
        DownloadProgressInfo downloadProgressInfo = this.mProgressInfo;
        if (downloadProgressInfo != null) {
            CreateProxy.onDownloadProgress(downloadProgressInfo);
        }
        int i = this.mState;
        if (i != -1) {
            this.mClientProxy.onDownloadStateChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DownloadNotification(Context ctx, CharSequence applicationLabel) {
        this.mContext = ctx;
        this.mLabel = applicationLabel;
        this.mNotificationManager = (NotificationManager) ctx.getSystemService("notification");
        this.mActiveDownloadBuilder = new NotificationCompat.Builder(ctx);
        this.mBuilder = new NotificationCompat.Builder(ctx);
        this.mActiveDownloadBuilder.setPriority(-1);
        this.mActiveDownloadBuilder.setCategory("progress");
        this.mBuilder.setPriority(-1);
        this.mBuilder.setCategory("progress");
        this.mCurrentBuilder = this.mBuilder;
    }

    @Override // com.google.android.vending.expansion.downloader.IDownloaderClient
    public void onServiceConnected(Messenger m) {
    }
}