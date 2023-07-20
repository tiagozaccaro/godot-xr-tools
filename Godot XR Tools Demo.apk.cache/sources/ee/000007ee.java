package org.godotengine.godot.io.directory;

import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.util.SparseArray;
import java.io.File;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.godotengine.godot.io.StorageScope;
import org.godotengine.godot.io.directory.DirectoryAccessHandler;
import org.godotengine.godot.io.file.FileAccessHandler;

/* compiled from: FilesystemDirectoryAccess.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\r\b\u0000\u0018\u0000 (2\u00020\u0001:\u0002()B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\tH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\tH\u0016J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\tH\u0016J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\tH\u0016J\b\u0010\u001b\u001a\u00020\tH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0017J\u0010\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\tH\u0016J\u0010\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010 \u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\tH\u0016J\u0010\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u0014H\u0016J\u0010\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0014H\u0016J\u0018\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0014H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/godotengine/godot/io/directory/FilesystemDirectoryAccess;", "Lorg/godotengine/godot/io/directory/DirectoryAccessHandler$DirectoryAccess;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dirs", "Landroid/util/SparseArray;", "Lorg/godotengine/godot/io/directory/FilesystemDirectoryAccess$DirData;", "lastDirId", "", "storageManager", "Landroid/os/storage/StorageManager;", "storageScopeIdentifier", "Lorg/godotengine/godot/io/StorageScope$Identifier;", "dirClose", "", "dirId", "dirExists", "", "path", "", "dirIsDir", "dirNext", "dirOpen", "fileExists", "getDrive", "drive", "getDriveCount", "getSpaceLeft", "", "hasDirId", "inScope", "isCurrentHidden", "makeDir", "dir", "remove", "filename", "rename", "from", "to", "Companion", "DirData", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FilesystemDirectoryAccess implements DirectoryAccessHandler.DirectoryAccess {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = FilesystemDirectoryAccess.class.getSimpleName();
    private final Context context;
    private final SparseArray<DirData> dirs;
    private int lastDirId;
    private final StorageManager storageManager;
    private final StorageScope.Identifier storageScopeIdentifier;

    public FilesystemDirectoryAccess(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.storageScopeIdentifier = new StorageScope.Identifier(context);
        Object systemService = context.getSystemService("storage");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.storage.StorageManager");
        this.storageManager = (StorageManager) systemService;
        this.lastDirId = 1;
        this.dirs = new SparseArray<>();
    }

    /* compiled from: FilesystemDirectoryAccess.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/godotengine/godot/io/directory/FilesystemDirectoryAccess$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FilesystemDirectoryAccess.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003¢\u0006\u0002\u0010\u0010J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J2\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001¢\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lorg/godotengine/godot/io/directory/FilesystemDirectoryAccess$DirData;", "", "dirFile", "Ljava/io/File;", "files", "", "current", "", "(Ljava/io/File;[Ljava/io/File;I)V", "getCurrent", "()I", "setCurrent", "(I)V", "getDirFile", "()Ljava/io/File;", "getFiles", "()[Ljava/io/File;", "[Ljava/io/File;", "component1", "component2", "component3", "copy", "(Ljava/io/File;[Ljava/io/File;I)Lorg/godotengine/godot/io/directory/FilesystemDirectoryAccess$DirData;", "equals", "", "other", "hashCode", "toString", "", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class DirData {
        private int current;
        private final File dirFile;
        private final File[] files;

        public static /* synthetic */ DirData copy$default(DirData dirData, File file, File[] fileArr, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                file = dirData.dirFile;
            }
            if ((i2 & 2) != 0) {
                fileArr = dirData.files;
            }
            if ((i2 & 4) != 0) {
                i = dirData.current;
            }
            return dirData.copy(file, fileArr, i);
        }

        public final File component1() {
            return this.dirFile;
        }

        public final File[] component2() {
            return this.files;
        }

        public final int component3() {
            return this.current;
        }

        public final DirData copy(File dirFile, File[] files, int i) {
            Intrinsics.checkNotNullParameter(dirFile, "dirFile");
            Intrinsics.checkNotNullParameter(files, "files");
            return new DirData(dirFile, files, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof DirData) {
                DirData dirData = (DirData) obj;
                return Intrinsics.areEqual(this.dirFile, dirData.dirFile) && Intrinsics.areEqual(this.files, dirData.files) && this.current == dirData.current;
            }
            return false;
        }

        public int hashCode() {
            return (((this.dirFile.hashCode() * 31) + Arrays.hashCode(this.files)) * 31) + Integer.hashCode(this.current);
        }

        public String toString() {
            File file = this.dirFile;
            String arrays = Arrays.toString(this.files);
            return "DirData(dirFile=" + file + ", files=" + arrays + ", current=" + this.current + ")";
        }

        public DirData(File dirFile, File[] files, int current) {
            Intrinsics.checkNotNullParameter(dirFile, "dirFile");
            Intrinsics.checkNotNullParameter(files, "files");
            this.dirFile = dirFile;
            this.files = files;
            this.current = current;
        }

        public /* synthetic */ DirData(File file, File[] fileArr, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(file, fileArr, (i2 & 4) != 0 ? 0 : i);
        }

        public final int getCurrent() {
            return this.current;
        }

        public final File getDirFile() {
            return this.dirFile;
        }

        public final File[] getFiles() {
            return this.files;
        }

        public final void setCurrent(int i) {
            this.current = i;
        }
    }

    private final boolean inScope(String path) {
        return this.storageScopeIdentifier.identifyStorageScope(path) != StorageScope.UNKNOWN;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean hasDirId(int dirId) {
        return this.dirs.indexOfKey(dirId) >= 0;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public int dirOpen(String path) {
        File[] files;
        Intrinsics.checkNotNullParameter(path, "path");
        if (!inScope(path)) {
            Log.w(TAG, "Path " + path + " is not accessible.");
            return -1;
        }
        File dirFile = new File(path);
        if (dirFile.isDirectory() && (files = dirFile.listFiles()) != null) {
            DirData dirData = new DirData(dirFile, files, 0, 4, null);
            SparseArray<DirData> sparseArray = this.dirs;
            int i = this.lastDirId + 1;
            this.lastDirId = i;
            sparseArray.put(i, dirData);
            return this.lastDirId;
        }
        return -1;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean dirExists(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (!inScope(path)) {
            Log.w(TAG, "Path " + path + " is not accessible.");
            return false;
        }
        try {
            return new File(path).isDirectory();
        } catch (SecurityException e) {
            return false;
        }
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean fileExists(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return FileAccessHandler.Companion.fileExists$lib_templateDebug(this.context, this.storageScopeIdentifier, path);
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public String dirNext(int dirId) {
        DirData dirData = this.dirs.get(dirId);
        if (dirData.getCurrent() >= dirData.getFiles().length) {
            dirData.setCurrent(dirData.getCurrent() + 1);
            return "";
        }
        File[] files = dirData.getFiles();
        int current = dirData.getCurrent();
        dirData.setCurrent(current + 1);
        String name = files[current].getName();
        Intrinsics.checkNotNullExpressionValue(name, "dirData.files[dirData.current++].name");
        return name;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public void dirClose(int dirId) {
        this.dirs.remove(dirId);
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean dirIsDir(int dirId) {
        DirData dirData = this.dirs.get(dirId);
        int index = dirData.getCurrent();
        if (index > 0) {
            index--;
        }
        if (index >= dirData.getFiles().length) {
            return false;
        }
        return dirData.getFiles()[index].isDirectory();
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean isCurrentHidden(int dirId) {
        DirData dirData = this.dirs.get(dirId);
        int index = dirData.getCurrent();
        if (index > 0) {
            index--;
        }
        if (index >= dirData.getFiles().length) {
            return false;
        }
        return dirData.getFiles()[index].isHidden();
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public int getDriveCount() {
        return this.storageManager.getStorageVolumes().size();
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public String getDrive(int drive) {
        if (drive < 0 || drive >= this.storageManager.getStorageVolumes().size()) {
            return "";
        }
        StorageVolume storageVolume = this.storageManager.getStorageVolumes().get(drive);
        String description = storageVolume.getDescription(this.context);
        Intrinsics.checkNotNullExpressionValue(description, "storageVolume.getDescription(context)");
        return description;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean makeDir(String dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        if (!inScope(dir)) {
            Log.w(TAG, "Directory " + dir + " is not accessible.");
            return false;
        }
        try {
            File dirFile = new File(dir);
            if (!dirFile.isDirectory()) {
                if (!dirFile.mkdirs()) {
                    return false;
                }
            }
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public long getSpaceLeft() {
        File externalFilesDir = this.context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            return externalFilesDir.getUsableSpace();
        }
        return 0L;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean rename(String from, String to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        boolean z = false;
        if (!inScope(from) || !inScope(to)) {
            Log.w(TAG, "Argument filenames are not accessible:\nfrom: " + from + "\nto: " + to);
            return false;
        }
        try {
            File fromFile = new File(from);
            if (fromFile.isDirectory()) {
                z = fromFile.renameTo(new File(to));
            } else {
                z = FileAccessHandler.Companion.renameFile$lib_templateDebug(this.context, this.storageScopeIdentifier, from, to);
            }
        } catch (SecurityException e) {
        }
        return z;
    }

    @Override // org.godotengine.godot.io.directory.DirectoryAccessHandler.DirectoryAccess
    public boolean remove(String filename) {
        Intrinsics.checkNotNullParameter(filename, "filename");
        boolean z = false;
        if (!inScope(filename)) {
            Log.w(TAG, "Filename " + filename + " is not accessible.");
            return false;
        }
        try {
            File deleteFile = new File(filename);
            if (deleteFile.exists()) {
                if (deleteFile.isDirectory()) {
                    z = deleteFile.delete();
                } else {
                    z = FileAccessHandler.Companion.removeFile$lib_templateDebug(this.context, this.storageScopeIdentifier, filename);
                }
            } else {
                z = true;
            }
        } catch (SecurityException e) {
        }
        return z;
    }
}