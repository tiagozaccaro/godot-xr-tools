package org.godotengine.godot.io.file;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.godotengine.godot.io.StorageScope;
import org.godotengine.godot.io.file.DataAccess;

/* compiled from: FileAccessHandler.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 +2\u00020\u0001:\u0001+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\u0010\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u000bJ\u0010\u0010\u0019\u001a\u00020\u00172\b\u0010\u001a\u001a\u0004\u0018\u00010\u0014J\u0018\u0010\u001b\u001a\u00020\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001c\u001a\u00020\u000bJ\u001f\u0010\u001b\u001a\u00020\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0000¢\u0006\u0002\b\u001fJ\u0018\u0010 \u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\b\u0010!\u001a\u0004\u0018\u00010\"J\u0016\u0010#\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u0017J\u0016\u0010%\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u0017J\u0018\u0010&\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\b\u0010!\u001a\u0004\u0018\u00010\"J\u0010\u0010'\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u000bH\u0002J\u000e\u0010(\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u000bJ\u0016\u0010)\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/godotengine/godot/io/file/FileAccessHandler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "files", "Landroid/util/SparseArray;", "Lorg/godotengine/godot/io/file/DataAccess;", "lastFileId", "", "storageScopeIdentifier", "Lorg/godotengine/godot/io/StorageScope$Identifier;", "fileClose", "", "fileId", "fileExists", "", "path", "", "fileFlush", "fileGetPosition", "", "fileGetSize", "fileLastModified", "filepath", "fileOpen", "modeFlags", "accessFlag", "Lorg/godotengine/godot/io/file/FileAccessFlags;", "fileOpen$lib_templateDebug", "fileRead", "byteBuffer", "Ljava/nio/ByteBuffer;", "fileSeek", "position", "fileSeekFromEnd", "fileWrite", "hasFileId", "isFileEof", "setFileEof", "eof", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FileAccessHandler {
    private static final int FILE_NOT_FOUND_ERROR_ID = -1;
    public static final int INVALID_FILE_ID = 0;
    private static final int STARTING_FILE_ID = 1;
    private final Context context;
    private final SparseArray<DataAccess> files;
    private int lastFileId;
    private final StorageScope.Identifier storageScopeIdentifier;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = FileAccessHandler.class.getSimpleName();

    public FileAccessHandler(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.storageScopeIdentifier = new StorageScope.Identifier(context);
        this.files = new SparseArray<>();
        this.lastFileId = 1;
    }

    public final Context getContext() {
        return this.context;
    }

    /* compiled from: FileAccessHandler.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\bH\u0000¢\u0006\u0002\b\u0011J'\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\bH\u0000¢\u0006\u0002\b\u0013J1\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\bH\u0000¢\u0006\u0002\b\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/godotengine/godot/io/file/FileAccessHandler$Companion;", "", "()V", "FILE_NOT_FOUND_ERROR_ID", "", "INVALID_FILE_ID", "STARTING_FILE_ID", "TAG", "", "kotlin.jvm.PlatformType", "fileExists", "", "context", "Landroid/content/Context;", "storageScopeIdentifier", "Lorg/godotengine/godot/io/StorageScope$Identifier;", "path", "fileExists$lib_templateDebug", "removeFile", "removeFile$lib_templateDebug", "renameFile", "from", "to", "renameFile$lib_templateDebug", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean fileExists$lib_templateDebug(Context context, StorageScope.Identifier storageScopeIdentifier, String path) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(storageScopeIdentifier, "storageScopeIdentifier");
            StorageScope storageScope = storageScopeIdentifier.identifyStorageScope(path);
            if (storageScope == StorageScope.UNKNOWN) {
                return false;
            }
            try {
                DataAccess.Companion companion = DataAccess.Companion;
                Intrinsics.checkNotNull(path);
                return companion.fileExists(storageScope, context, path);
            } catch (SecurityException e) {
                return false;
            }
        }

        public final boolean removeFile$lib_templateDebug(Context context, StorageScope.Identifier storageScopeIdentifier, String path) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(storageScopeIdentifier, "storageScopeIdentifier");
            StorageScope storageScope = storageScopeIdentifier.identifyStorageScope(path);
            if (storageScope == StorageScope.UNKNOWN) {
                return false;
            }
            try {
                DataAccess.Companion companion = DataAccess.Companion;
                Intrinsics.checkNotNull(path);
                return companion.removeFile(storageScope, context, path);
            } catch (Exception e) {
                return false;
            }
        }

        public final boolean renameFile$lib_templateDebug(Context context, StorageScope.Identifier storageScopeIdentifier, String from, String to) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(storageScopeIdentifier, "storageScopeIdentifier");
            StorageScope storageScope = storageScopeIdentifier.identifyStorageScope(from);
            if (storageScope == StorageScope.UNKNOWN) {
                return false;
            }
            try {
                DataAccess.Companion companion = DataAccess.Companion;
                Intrinsics.checkNotNull(from);
                Intrinsics.checkNotNull(to);
                return companion.renameFile(storageScope, context, from, to);
            } catch (Exception e) {
                return false;
            }
        }
    }

    private final boolean hasFileId(int fileId) {
        return this.files.indexOfKey(fileId) >= 0;
    }

    public final int fileOpen(String path, int modeFlags) {
        FileAccessFlags accessFlag = FileAccessFlags.Companion.fromNativeModeFlags(modeFlags);
        if (accessFlag == null) {
            return 0;
        }
        return fileOpen$lib_templateDebug(path, accessFlag);
    }

    public final int fileOpen$lib_templateDebug(String path, FileAccessFlags accessFlag) {
        Intrinsics.checkNotNullParameter(accessFlag, "accessFlag");
        StorageScope storageScope = this.storageScopeIdentifier.identifyStorageScope(path);
        if (storageScope == StorageScope.UNKNOWN) {
            return 0;
        }
        try {
            DataAccess.Companion companion = DataAccess.Companion;
            Context context = this.context;
            Intrinsics.checkNotNull(path);
            DataAccess dataAccess = companion.generateDataAccess(storageScope, context, path, accessFlag);
            if (dataAccess == null) {
                return 0;
            }
            SparseArray<DataAccess> sparseArray = this.files;
            int i = this.lastFileId + 1;
            this.lastFileId = i;
            sparseArray.put(i, dataAccess);
            return this.lastFileId;
        } catch (FileNotFoundException e) {
            return -1;
        } catch (Exception e2) {
            Log.w(TAG, "Error while opening " + path, e2);
            return 0;
        }
    }

    public final long fileGetSize(int fileId) {
        if (!hasFileId(fileId)) {
            return 0L;
        }
        return this.files.get(fileId).size();
    }

    public final void fileSeek(int fileId, long position) {
        if (!hasFileId(fileId)) {
            return;
        }
        this.files.get(fileId).seek(position);
    }

    public final void fileSeekFromEnd(int fileId, long position) {
        if (!hasFileId(fileId)) {
            return;
        }
        this.files.get(fileId).seekFromEnd(position);
    }

    public final int fileRead(int fileId, ByteBuffer byteBuffer) {
        if (!hasFileId(fileId) || byteBuffer == null) {
            return 0;
        }
        return this.files.get(fileId).read(byteBuffer);
    }

    public final void fileWrite(int fileId, ByteBuffer byteBuffer) {
        if (!hasFileId(fileId) || byteBuffer == null) {
            return;
        }
        this.files.get(fileId).write(byteBuffer);
    }

    public final void fileFlush(int fileId) {
        if (!hasFileId(fileId)) {
            return;
        }
        this.files.get(fileId).flush();
    }

    public final boolean fileExists(String path) {
        return Companion.fileExists$lib_templateDebug(this.context, this.storageScopeIdentifier, path);
    }

    public final long fileLastModified(String filepath) {
        StorageScope storageScope = this.storageScopeIdentifier.identifyStorageScope(filepath);
        if (storageScope == StorageScope.UNKNOWN) {
            return 0L;
        }
        try {
            DataAccess.Companion companion = DataAccess.Companion;
            Context context = this.context;
            Intrinsics.checkNotNull(filepath);
            return companion.fileLastModified(storageScope, context, filepath);
        } catch (SecurityException e) {
            return 0L;
        }
    }

    public final long fileGetPosition(int fileId) {
        if (!hasFileId(fileId)) {
            return 0L;
        }
        return this.files.get(fileId).position();
    }

    public final boolean isFileEof(int fileId) {
        if (!hasFileId(fileId)) {
            return false;
        }
        return this.files.get(fileId).getEndOfFile$lib_templateDebug();
    }

    public final void setFileEof(int fileId, boolean eof) {
        DataAccess file = this.files.get(fileId);
        if (file == null) {
            return;
        }
        file.setEndOfFile$lib_templateDebug(eof);
    }

    public final void fileClose(int fileId) {
        if (hasFileId(fileId)) {
            this.files.get(fileId).close();
            this.files.remove(fileId);
        }
    }
}