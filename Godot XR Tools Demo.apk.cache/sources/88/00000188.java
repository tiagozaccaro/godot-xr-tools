package androidx.core.os;

import android.os.Message;

/* loaded from: classes.dex */
public final class MessageCompat {
    private static boolean sTrySetAsynchronous = true;
    private static boolean sTryIsAsynchronous = true;

    public static void setAsynchronous(Message message, boolean async) {
        message.setAsynchronous(async);
    }

    public static boolean isAsynchronous(Message message) {
        return message.isAsynchronous();
    }

    private MessageCompat() {
    }
}