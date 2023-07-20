package androidx.core.app;

import android.app.AlarmManager;
import android.app.PendingIntent;

/* loaded from: classes.dex */
public final class AlarmManagerCompat {
    public static void setAlarmClock(AlarmManager alarmManager, long triggerTime, PendingIntent showIntent, PendingIntent operation) {
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(triggerTime, showIntent), operation);
    }

    public static void setAndAllowWhileIdle(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
        alarmManager.setAndAllowWhileIdle(type, triggerAtMillis, operation);
    }

    public static void setExact(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
        alarmManager.setExact(type, triggerAtMillis, operation);
    }

    public static void setExactAndAllowWhileIdle(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
        alarmManager.setExactAndAllowWhileIdle(type, triggerAtMillis, operation);
    }

    private AlarmManagerCompat() {
    }
}