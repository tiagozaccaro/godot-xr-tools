package androidx.core.graphics;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public final class BitmapCompat {
    public static boolean hasMipMap(Bitmap bitmap) {
        return bitmap.hasMipMap();
    }

    public static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
        bitmap.setHasMipMap(hasMipMap);
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }

    private BitmapCompat() {
    }
}