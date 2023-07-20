package org.godotengine.godot.io.file;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.godotengine.godot.io.StorageScope;

/* compiled from: DataAccess.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b \u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010J\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0013J\u0006\u0010\u001b\u001a\u00020\u0013J\u000e\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/godotengine/godot/io/file/DataAccess;", "", "filePath", "", "(Ljava/lang/String;)V", "endOfFile", "", "getEndOfFile$lib_templateDebug", "()Z", "setEndOfFile$lib_templateDebug", "(Z)V", "fileChannel", "Ljava/nio/channels/FileChannel;", "getFileChannel", "()Ljava/nio/channels/FileChannel;", "close", "", "flush", "position", "", "read", "", "buffer", "Ljava/nio/ByteBuffer;", "seek", "seekFromEnd", "positionFromEnd", "size", "write", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class DataAccess {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = DataAccess.class.getSimpleName();
    private boolean endOfFile;
    private final String filePath;

    protected abstract FileChannel getFileChannel();

    public DataAccess(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        this.filePath = filePath;
    }

    /* compiled from: DataAccess.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J(\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013J\u001e\u0010\u0014\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J&\u0010\u0015\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/godotengine/godot/io/file/DataAccess$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "fileExists", "", "storageScope", "Lorg/godotengine/godot/io/StorageScope;", "context", "Landroid/content/Context;", "path", "fileLastModified", "", "generateDataAccess", "Lorg/godotengine/godot/io/file/DataAccess;", "filePath", "accessFlag", "Lorg/godotengine/godot/io/file/FileAccessFlags;", "removeFile", "renameFile", "from", "to", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {

        /* compiled from: DataAccess.kt */
        @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[StorageScope.values().length];
                iArr[StorageScope.APP.ordinal()] = 1;
                iArr[StorageScope.SHARED.ordinal()] = 2;
                iArr[StorageScope.UNKNOWN.ordinal()] = 3;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DataAccess generateDataAccess(StorageScope storageScope, Context context, String filePath, FileAccessFlags accessFlag) {
            Intrinsics.checkNotNullParameter(storageScope, "storageScope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            Intrinsics.checkNotNullParameter(accessFlag, "accessFlag");
            MediaStoreData mediaStoreData = null;
            switch (WhenMappings.$EnumSwitchMapping$0[storageScope.ordinal()]) {
                case 1:
                    return new FileData(filePath, accessFlag);
                case 2:
                    if (Build.VERSION.SDK_INT >= 29) {
                        mediaStoreData = new MediaStoreData(context, filePath, accessFlag);
                    }
                    return mediaStoreData;
                case 3:
                    return null;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public final boolean fileExists(StorageScope storageScope, Context context, String path) {
            Intrinsics.checkNotNullParameter(storageScope, "storageScope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(path, "path");
            switch (WhenMappings.$EnumSwitchMapping$0[storageScope.ordinal()]) {
                case 1:
                    return FileData.Companion.fileExists(path);
                case 2:
                    return MediaStoreData.Companion.fileExists(context, path);
                case 3:
                    return false;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public final long fileLastModified(StorageScope storageScope, Context context, String path) {
            Intrinsics.checkNotNullParameter(storageScope, "storageScope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(path, "path");
            switch (WhenMappings.$EnumSwitchMapping$0[storageScope.ordinal()]) {
                case 1:
                    return FileData.Companion.fileLastModified(path);
                case 2:
                    return MediaStoreData.Companion.fileLastModified(context, path);
                case 3:
                    return 0L;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public final boolean removeFile(StorageScope storageScope, Context context, String path) {
            Intrinsics.checkNotNullParameter(storageScope, "storageScope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(path, "path");
            switch (WhenMappings.$EnumSwitchMapping$0[storageScope.ordinal()]) {
                case 1:
                    return FileData.Companion.delete(path);
                case 2:
                    return MediaStoreData.Companion.delete(context, path);
                case 3:
                    return false;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public final boolean renameFile(StorageScope storageScope, Context context, String from, String to) {
            Intrinsics.checkNotNullParameter(storageScope, "storageScope");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(to, "to");
            switch (WhenMappings.$EnumSwitchMapping$0[storageScope.ordinal()]) {
                case 1:
                    return FileData.Companion.rename(from, to);
                case 2:
                    return MediaStoreData.Companion.rename(context, from, to);
                case 3:
                    return false;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    }

    public final boolean getEndOfFile$lib_templateDebug() {
        return this.endOfFile;
    }

    public final void setEndOfFile$lib_templateDebug(boolean z) {
        this.endOfFile = z;
    }

    public final void close() {
        try {
            getFileChannel().close();
        } catch (IOException e) {
            Log.w(TAG, "Exception when closing file " + this.filePath + ".", e);
        }
    }

    public final void flush() {
        try {
            getFileChannel().force(false);
        } catch (IOException e) {
            Log.w(TAG, "Exception when flushing file " + this.filePath + ".", e);
        }
    }

    public final void seek(long position) {
        try {
            getFileChannel().position(position);
            this.endOfFile = position >= getFileChannel().size();
        } catch (Exception e) {
            Log.w(TAG, "Exception when seeking file " + this.filePath + ".", e);
        }
    }

    public final void seekFromEnd(long positionFromEnd) {
        long positionFromBeginning = Math.max(0L, size() - positionFromEnd);
        seek(positionFromBeginning);
    }

    public final long position() {
        try {
            return getFileChannel().position();
        } catch (IOException e) {
            Log.w(TAG, "Exception when retrieving position for file " + this.filePath + ".", e);
            return 0L;
        }
    }

    public final long size() {
        try {
            return getFileChannel().size();
        } catch (IOException e) {
            Log.w(TAG, "Exception when retrieving size for file " + this.filePath + ".", e);
            return 0L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int read(java.nio.ByteBuffer r8) {
        /*
            r7 = this;
            java.lang.String r0 = "buffer"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            r0 = 0
            java.nio.channels.FileChannel r1 = r7.getFileChannel()     // Catch: java.io.IOException -> L31
            int r1 = r1.read(r8)     // Catch: java.io.IOException -> L31
            r2 = -1
            if (r1 == r2) goto L29
            java.nio.channels.FileChannel r3 = r7.getFileChannel()     // Catch: java.io.IOException -> L31
            long r3 = r3.position()     // Catch: java.io.IOException -> L31
            java.nio.channels.FileChannel r5 = r7.getFileChannel()     // Catch: java.io.IOException -> L31
            long r5 = r5.size()     // Catch: java.io.IOException -> L31
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 < 0) goto L27
            goto L29
        L27:
            r3 = r0
            goto L2a
        L29:
            r3 = 1
        L2a:
            r7.endOfFile = r3     // Catch: java.io.IOException -> L31
            if (r1 != r2) goto L2f
            goto L30
        L2f:
            r0 = r1
        L30:
            goto L56
        L31:
            r1 = move-exception
            java.lang.String r2 = org.godotengine.godot.io.file.DataAccess.TAG
            java.lang.String r3 = r7.filePath
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Exception while reading from file "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r4 = "."
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r4 = r1
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            android.util.Log.w(r2, r3, r4)
        L56:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.godotengine.godot.io.file.DataAccess.read(java.nio.ByteBuffer):int");
    }

    public final void write(ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        try {
            int writtenBytes = getFileChannel().write(buffer);
            if (writtenBytes > 0) {
                this.endOfFile = false;
            }
        } catch (IOException e) {
            Log.w(TAG, "Exception while writing to file " + this.filePath + ".", e);
        }
    }
}