package org.godotengine.godot.io.directory;

import android.content.Context;
import android.util.Log;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DirectoryAccessHandler.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 *2\u00020\u0001:\u0003)*+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010J\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\fJ\u0016\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010J\u0016\u0010\u0015\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010J\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\fJ\u0018\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\fJ\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\fJ\u0016\u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J\u0016\u0010!\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010J\u0016\u0010\"\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\fJ\u0016\u0010$\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010%\u001a\u00020\fJ\u001e\u0010&\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010'\u001a\u00020\f2\u0006\u0010(\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/godotengine/godot/io/directory/DirectoryAccessHandler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "assetsDirAccess", "Lorg/godotengine/godot/io/directory/AssetsDirectoryAccess;", "fileSystemDirAccess", "Lorg/godotengine/godot/io/directory/FilesystemDirectoryAccess;", "assetsFileExists", "", "assetsPath", "", "dirClose", "", "nativeAccessType", "", "dirId", "dirExists", "path", "dirIsDir", "dirNext", "dirOpen", "fileExists", "filesystemFileExists", "getDrive", "drive", "getDriveCount", "getSpaceLeft", "", "hasDirId", "accessType", "Lorg/godotengine/godot/io/directory/DirectoryAccessHandler$AccessType;", "isCurrentHidden", "makeDir", "dir", "remove", "filename", "rename", "from", "to", "AccessType", "Companion", "DirectoryAccess", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class DirectoryAccessHandler {
    public static final int INVALID_DIR_ID = -1;
    public static final int STARTING_DIR_ID = 1;
    private final AssetsDirectoryAccess assetsDirAccess;
    private final FilesystemDirectoryAccess fileSystemDirAccess;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = DirectoryAccessHandler.class.getSimpleName();

    /* compiled from: DirectoryAccessHandler.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\n\b`\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0005H&J\b\u0010\u0010\u001a\u00020\u0005H&J\b\u0010\u0011\u001a\u00020\u0012H&J\u0010\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\tH&J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\tH&J\u0018\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH&¨\u0006\u001c"}, d2 = {"Lorg/godotengine/godot/io/directory/DirectoryAccessHandler$DirectoryAccess;", "", "dirClose", "", "dirId", "", "dirExists", "", "path", "", "dirIsDir", "dirNext", "dirOpen", "fileExists", "getDrive", "drive", "getDriveCount", "getSpaceLeft", "", "hasDirId", "isCurrentHidden", "makeDir", "dir", "remove", "filename", "rename", "from", "to", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface DirectoryAccess {
        void dirClose(int i);

        boolean dirExists(String str);

        boolean dirIsDir(int i);

        String dirNext(int i);

        int dirOpen(String str);

        boolean fileExists(String str);

        String getDrive(int i);

        int getDriveCount();

        long getSpaceLeft();

        boolean hasDirId(int i);

        boolean isCurrentHidden(int i);

        boolean makeDir(String str);

        boolean remove(String str);

        boolean rename(String str, String str2);
    }

    /* compiled from: DirectoryAccessHandler.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AccessType.values().length];
            iArr[AccessType.ACCESS_RESOURCES.ordinal()] = 1;
            iArr[AccessType.ACCESS_FILESYSTEM.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DirectoryAccessHandler(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.assetsDirAccess = new AssetsDirectoryAccess(context);
        this.fileSystemDirAccess = new FilesystemDirectoryAccess(context);
    }

    /* compiled from: DirectoryAccessHandler.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/godotengine/godot/io/directory/DirectoryAccessHandler$Companion;", "", "()V", "INVALID_DIR_ID", "", "STARTING_DIR_ID", "TAG", "", "kotlin.jvm.PlatformType", "getAccessTypeFromNative", "Lorg/godotengine/godot/io/directory/DirectoryAccessHandler$AccessType;", "accessType", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AccessType getAccessTypeFromNative(int accessType) {
            if (accessType == AccessType.ACCESS_RESOURCES.getNativeValue()) {
                return AccessType.ACCESS_RESOURCES;
            }
            if (accessType == AccessType.ACCESS_FILESYSTEM.getNativeValue()) {
                return AccessType.ACCESS_FILESYSTEM;
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DirectoryAccessHandler.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/godotengine/godot/io/directory/DirectoryAccessHandler$AccessType;", "", "nativeValue", "", "(Ljava/lang/String;II)V", "getNativeValue", "()I", "ACCESS_RESOURCES", "ACCESS_FILESYSTEM", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum AccessType {
        ACCESS_RESOURCES(0),
        ACCESS_FILESYSTEM(2);
        
        private final int nativeValue;

        AccessType(int nativeValue) {
            this.nativeValue = nativeValue;
        }

        public final int getNativeValue() {
            return this.nativeValue;
        }
    }

    public final boolean assetsFileExists(String assetsPath) {
        Intrinsics.checkNotNullParameter(assetsPath, "assetsPath");
        return this.assetsDirAccess.fileExists(assetsPath);
    }

    public final boolean filesystemFileExists(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return this.fileSystemDirAccess.fileExists(path);
    }

    private final boolean hasDirId(AccessType accessType, int dirId) {
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.hasDirId(dirId);
            case 2:
                return this.fileSystemDirAccess.hasDirId(dirId);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final int dirOpen(int nativeAccessType, String path) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (path == null || accessType == null) {
            return -1;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.dirOpen(path);
            case 2:
                return this.fileSystemDirAccess.dirOpen(path);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final String dirNext(int nativeAccessType, int dirId) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null || !hasDirId(accessType, dirId)) {
            Log.w(TAG, "dirNext: Invalid dir id: " + dirId);
            return "";
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.dirNext(dirId);
            case 2:
                return this.fileSystemDirAccess.dirNext(dirId);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final void dirClose(int nativeAccessType, int dirId) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null || !hasDirId(accessType, dirId)) {
            Log.w(TAG, "dirClose: Invalid dir id: " + dirId);
            return;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                this.assetsDirAccess.dirClose(dirId);
                return;
            case 2:
                this.fileSystemDirAccess.dirClose(dirId);
                return;
            default:
                return;
        }
    }

    public final boolean dirIsDir(int nativeAccessType, int dirId) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null || !hasDirId(accessType, dirId)) {
            Log.w(TAG, "dirIsDir: Invalid dir id: " + dirId);
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.dirIsDir(dirId);
            case 2:
                return this.fileSystemDirAccess.dirIsDir(dirId);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean isCurrentHidden(int nativeAccessType, int dirId) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null || !hasDirId(accessType, dirId)) {
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.isCurrentHidden(dirId);
            case 2:
                return this.fileSystemDirAccess.isCurrentHidden(dirId);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean dirExists(int nativeAccessType, String path) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (path == null || accessType == null) {
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.dirExists(path);
            case 2:
                return this.fileSystemDirAccess.dirExists(path);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean fileExists(int nativeAccessType, String path) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (path == null || accessType == null) {
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.fileExists(path);
            case 2:
                return this.fileSystemDirAccess.fileExists(path);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final int getDriveCount(int nativeAccessType) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null) {
            return 0;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.getDriveCount();
            case 2:
                return this.fileSystemDirAccess.getDriveCount();
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final String getDrive(int nativeAccessType, int drive) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null) {
            return "";
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.getDrive(drive);
            case 2:
                return this.fileSystemDirAccess.getDrive(drive);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean makeDir(int nativeAccessType, String dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null) {
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.makeDir(dir);
            case 2:
                return this.fileSystemDirAccess.makeDir(dir);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final long getSpaceLeft(int nativeAccessType) {
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null) {
            return 0L;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.getSpaceLeft();
            case 2:
                return this.fileSystemDirAccess.getSpaceLeft();
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean rename(int nativeAccessType, String from, String to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null) {
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.rename(from, to);
            case 2:
                return this.fileSystemDirAccess.rename(from, to);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean remove(int nativeAccessType, String filename) {
        Intrinsics.checkNotNullParameter(filename, "filename");
        AccessType accessType = Companion.getAccessTypeFromNative(nativeAccessType);
        if (accessType == null) {
            return false;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[accessType.ordinal()]) {
            case 1:
                return this.assetsDirAccess.remove(filename);
            case 2:
                return this.fileSystemDirAccess.remove(filename);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}