package androidx.core.app;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private RemoteViews mBigContentView;
    private final Notification.Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private final Bundle mExtras = new Bundle();

    /* JADX INFO: Access modifiers changed from: package-private */
    public NotificationCompatBuilder(NotificationCompat.Builder b) {
        this.mBuilderCompat = b;
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder = new Notification.Builder(b.mContext, b.mChannelId);
        } else {
            this.mBuilder = new Notification.Builder(b.mContext);
        }
        Notification n = b.mNotification;
        this.mBuilder.setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, b.mTickerView).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(b.mContentTitle).setContentText(b.mContentText).setContentInfo(b.mContentInfo).setContentIntent(b.mContentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(b.mFullScreenIntent, (n.flags & 128) != 0).setLargeIcon(b.mLargeIcon).setNumber(b.mNumber).setProgress(b.mProgressMax, b.mProgress, b.mProgressIndeterminate);
        this.mBuilder.setSubText(b.mSubText).setUsesChronometer(b.mUseChronometer).setPriority(b.mPriority);
        Iterator<NotificationCompat.Action> it = b.mActions.iterator();
        while (it.hasNext()) {
            NotificationCompat.Action action = it.next();
            addAction(action);
        }
        if (b.mExtras != null) {
            this.mExtras.putAll(b.mExtras);
        }
        this.mContentView = b.mContentView;
        this.mBigContentView = b.mBigContentView;
        this.mBuilder.setShowWhen(b.mShowWhen);
        this.mBuilder.setLocalOnly(b.mLocalOnly).setGroup(b.mGroupKey).setGroupSummary(b.mGroupSummary).setSortKey(b.mSortKey);
        this.mGroupAlertBehavior = b.mGroupAlertBehavior;
        this.mBuilder.setCategory(b.mCategory).setColor(b.mColor).setVisibility(b.mVisibility).setPublicVersion(b.mPublicVersion).setSound(n.sound, n.audioAttributes);
        Iterator<String> it2 = b.mPeople.iterator();
        while (it2.hasNext()) {
            String person = it2.next();
            this.mBuilder.addPerson(person);
        }
        this.mHeadsUpContentView = b.mHeadsUpContentView;
        if (b.mInvisibleActions.size() > 0) {
            Bundle carExtenderBundle = b.getExtras().getBundle("android.car.EXTENSIONS");
            carExtenderBundle = carExtenderBundle == null ? new Bundle() : carExtenderBundle;
            Bundle listBundle = new Bundle();
            for (int i = 0; i < b.mInvisibleActions.size(); i++) {
                listBundle.putBundle(Integer.toString(i), NotificationCompatJellybean.getBundleForAction(b.mInvisibleActions.get(i)));
            }
            carExtenderBundle.putBundle("invisible_actions", listBundle);
            b.getExtras().putBundle("android.car.EXTENSIONS", carExtenderBundle);
            this.mExtras.putBundle("android.car.EXTENSIONS", carExtenderBundle);
        }
        this.mBuilder.setExtras(b.mExtras).setRemoteInputHistory(b.mRemoteInputHistory);
        if (b.mContentView != null) {
            this.mBuilder.setCustomContentView(b.mContentView);
        }
        if (b.mBigContentView != null) {
            this.mBuilder.setCustomBigContentView(b.mBigContentView);
        }
        if (b.mHeadsUpContentView != null) {
            this.mBuilder.setCustomHeadsUpContentView(b.mHeadsUpContentView);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder.setBadgeIconType(b.mBadgeIcon).setShortcutId(b.mShortcutId).setTimeoutAfter(b.mTimeout).setGroupAlertBehavior(b.mGroupAlertBehavior);
            if (b.mColorizedSet) {
                this.mBuilder.setColorized(b.mColorized);
            }
            if (!TextUtils.isEmpty(b.mChannelId)) {
                this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.mBuilder.setAllowSystemGeneratedContextualActions(b.mAllowSystemGeneratedContextualActions);
            this.mBuilder.setBubbleMetadata(NotificationCompat.BubbleMetadata.toPlatform(b.mBubbleMetadata));
        }
    }

    @Override // androidx.core.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.mBuilder;
    }

    public Notification build() {
        Bundle extras;
        RemoteViews styleHeadsUpContentView;
        RemoteViews styleBigContentView;
        NotificationCompat.Style style = this.mBuilderCompat.mStyle;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews styleContentView = style != null ? style.makeContentView(this) : null;
        Notification n = buildInternal();
        if (styleContentView != null) {
            n.contentView = styleContentView;
        } else if (this.mBuilderCompat.mContentView != null) {
            n.contentView = this.mBuilderCompat.mContentView;
        }
        if (style != null && (styleBigContentView = style.makeBigContentView(this)) != null) {
            n.bigContentView = styleBigContentView;
        }
        if (style != null && (styleHeadsUpContentView = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this)) != null) {
            n.headsUpContentView = styleHeadsUpContentView;
        }
        if (style != null && (extras = NotificationCompat.getExtras(n)) != null) {
            style.addCompatExtras(extras);
        }
        return n;
    }

    private void addAction(NotificationCompat.Action action) {
        Bundle actionExtras;
        android.app.RemoteInput[] fromCompat;
        IconCompat iconCompat = action.getIconCompat();
        Notification.Action.Builder actionBuilder = new Notification.Action.Builder(iconCompat == null ? null : iconCompat.toIcon(), action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (android.app.RemoteInput remoteInput : RemoteInput.fromCompat(action.getRemoteInputs())) {
                actionBuilder.addRemoteInput(remoteInput);
            }
        }
        if (action.getExtras() != null) {
            actionExtras = new Bundle(action.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        actionBuilder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
        actionExtras.putInt("android.support.action.semanticAction", action.getSemanticAction());
        if (Build.VERSION.SDK_INT >= 28) {
            actionBuilder.setSemanticAction(action.getSemanticAction());
        }
        if (Build.VERSION.SDK_INT >= 29) {
            actionBuilder.setContextual(action.isContextual());
        }
        actionExtras.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
        actionBuilder.addExtras(actionExtras);
        this.mBuilder.addAction(actionBuilder.build());
    }

    protected Notification buildInternal() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mBuilder.build();
        }
        Notification notification = this.mBuilder.build();
        if (this.mGroupAlertBehavior != 0) {
            if (notification.getGroup() != null && (notification.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                removeSoundAndVibration(notification);
            }
            if (notification.getGroup() != null && (notification.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                removeSoundAndVibration(notification);
            }
        }
        return notification;
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}