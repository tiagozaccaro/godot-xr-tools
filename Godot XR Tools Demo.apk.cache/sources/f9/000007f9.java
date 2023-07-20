package org.godotengine.godot.io.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FileData.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/godotengine/godot/io/file/FileData;", "Lorg/godotengine/godot/io/file/DataAccess;", "filePath", "", "accessFlag", "Lorg/godotengine/godot/io/file/FileAccessFlags;", "(Ljava/lang/String;Lorg/godotengine/godot/io/file/FileAccessFlags;)V", "fileChannel", "Ljava/nio/channels/FileChannel;", "getFileChannel", "()Ljava/nio/channels/FileChannel;", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FileData extends DataAccess {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = FileData.class.getSimpleName();
    private final FileChannel fileChannel;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileData(String filePath, FileAccessFlags accessFlag) {
        super(filePath);
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(accessFlag, "accessFlag");
        if (accessFlag == FileAccessFlags.WRITE) {
            FileChannel channel = new FileOutputStream(filePath, !accessFlag.shouldTruncate()).getChannel();
            Intrinsics.checkNotNullExpressionValue(channel, "FileOutputStream(filePat…shouldTruncate()).channel");
            this.fileChannel = channel;
        } else {
            FileChannel channel2 = new RandomAccessFile(filePath, accessFlag.getMode()).getChannel();
            Intrinsics.checkNotNullExpressionValue(channel2, "RandomAccessFile(filePat…ssFlag.getMode()).channel");
            this.fileChannel = channel2;
        }
        if (!accessFlag.shouldTruncate()) {
            return;
        }
        getFileChannel().truncate(0L);
    }

    /* compiled from: FileData.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/godotengine/godot/io/file/FileData$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "delete", "", "filepath", "fileExists", "path", "fileLastModified", "", "rename", "from", "to", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean fileExists(String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            try {
                return new File(path).isFile();
            } catch (SecurityException e) {
                return false;
            }
        }

        public final long fileLastModified(String filepath) {
            Intrinsics.checkNotNullParameter(filepath, "filepath");
            try {
                return new File(filepath).lastModified();
            } catch (SecurityException e) {
                return 0L;
            }
        }

        public final boolean delete(String filepath) {
            Intrinsics.checkNotNullParameter(filepath, "filepath");
            try {
                return new File(filepath).delete();
            } catch (Exception e) {
                return false;
            }
        }

        public final boolean rename(String from, String to) {
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(to, "to");
            try {
                File fromFile = new File(from);
                return fromFile.renameTo(new File(to));
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override // org.godotengine.godot.io.file.DataAccess
    protected FileChannel getFileChannel() {
        return this.fileChannel;
    }
}