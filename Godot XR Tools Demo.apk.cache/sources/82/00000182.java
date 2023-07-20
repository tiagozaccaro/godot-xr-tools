package androidx.core.os;

import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    public static String getStorageState(File path) {
        return Environment.getExternalStorageState(path);
    }

    private EnvironmentCompat() {
    }
}