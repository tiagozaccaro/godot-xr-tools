package org.godotengine.godot.io;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: StorageScope.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0006B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0007"}, d2 = {"Lorg/godotengine/godot/io/StorageScope;", "", "(Ljava/lang/String;I)V", "APP", "SHARED", "UNKNOWN", "Identifier", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public enum StorageScope {
    APP,
    SHARED,
    UNKNOWN;

    /* compiled from: StorageScope.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/godotengine/godot/io/StorageScope$Identifier;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "documentsSharedDir", "", "downloadsSharedDir", "externalAppDir", "internalAppDir", "internalCacheDir", "sharedDir", "identifyStorageScope", "Lorg/godotengine/godot/io/StorageScope;", "path", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Identifier {
        private final String documentsSharedDir;
        private final String downloadsSharedDir;
        private final String externalAppDir;
        private final String internalAppDir;
        private final String internalCacheDir;
        private final String sharedDir;

        public Identifier(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.internalAppDir = context.getFilesDir().getCanonicalPath();
            this.internalCacheDir = context.getCacheDir().getCanonicalPath();
            File externalFilesDir = context.getExternalFilesDir(null);
            this.externalAppDir = externalFilesDir != null ? externalFilesDir.getCanonicalPath() : null;
            this.sharedDir = Environment.getExternalStorageDirectory().getCanonicalPath();
            this.downloadsSharedDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getCanonicalPath();
            this.documentsSharedDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getCanonicalPath();
        }

        public final StorageScope identifyStorageScope(String path) {
            String str;
            if (path == null) {
                return StorageScope.UNKNOWN;
            }
            File pathFile = new File(path);
            if (!pathFile.isAbsolute()) {
                return StorageScope.UNKNOWN;
            }
            if (Build.VERSION.SDK_INT >= 30 && Environment.isExternalStorageManager()) {
                return StorageScope.APP;
            }
            String canonicalPathFile = pathFile.getCanonicalPath();
            if (this.internalAppDir != null) {
                Intrinsics.checkNotNullExpressionValue(canonicalPathFile, "canonicalPathFile");
                if (StringsKt.startsWith$default(canonicalPathFile, this.internalAppDir, false, 2, (Object) null)) {
                    return StorageScope.APP;
                }
            }
            if (this.internalCacheDir != null) {
                Intrinsics.checkNotNullExpressionValue(canonicalPathFile, "canonicalPathFile");
                if (StringsKt.startsWith$default(canonicalPathFile, this.internalCacheDir, false, 2, (Object) null)) {
                    return StorageScope.APP;
                }
            }
            if (this.externalAppDir != null) {
                Intrinsics.checkNotNullExpressionValue(canonicalPathFile, "canonicalPathFile");
                if (StringsKt.startsWith$default(canonicalPathFile, this.externalAppDir, false, 2, (Object) null)) {
                    return StorageScope.APP;
                }
            }
            String rootDir = System.getenv("ANDROID_ROOT");
            if (rootDir != null) {
                Intrinsics.checkNotNullExpressionValue(canonicalPathFile, "canonicalPathFile");
                if (StringsKt.startsWith$default(canonicalPathFile, rootDir, false, 2, (Object) null)) {
                    return StorageScope.APP;
                }
            }
            if (this.sharedDir != null) {
                Intrinsics.checkNotNullExpressionValue(canonicalPathFile, "canonicalPathFile");
                if (StringsKt.startsWith$default(canonicalPathFile, this.sharedDir, false, 2, (Object) null)) {
                    if (Build.VERSION.SDK_INT < 30) {
                        return StorageScope.APP;
                    }
                    String str2 = this.downloadsSharedDir;
                    if ((str2 != null && StringsKt.startsWith$default(canonicalPathFile, str2, false, 2, (Object) null)) || ((str = this.documentsSharedDir) != null && StringsKt.startsWith$default(canonicalPathFile, str, false, 2, (Object) null))) {
                        return StorageScope.APP;
                    }
                    return StorageScope.SHARED;
                }
            }
            return StorageScope.UNKNOWN;
        }
    }
}