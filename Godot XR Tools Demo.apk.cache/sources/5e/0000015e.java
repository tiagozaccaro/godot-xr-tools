package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.vending.expansion.downloader.Constants;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + Constants.FILENAME_SEQUENCE_SEPARATOR + Process.myTid() + Constants.FILENAME_SEQUENCE_SEPARATOR;
        for (int i = 0; i < 100; i++) {
            File file = new File(cacheDir, prefix + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    private static ByteBuffer mmap(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            long size = channel.size();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            fis.close();
            return map;
        } catch (IOException e) {
            return null;
        }
    }

    public static ByteBuffer mmap(Context context, CancellationSignal cancellationSignal, Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r", cancellationSignal);
            if (pfd != null) {
                FileInputStream fis = new FileInputStream(pfd.getFileDescriptor());
                FileChannel channel = fis.getChannel();
                long size = channel.size();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
                fis.close();
                if (pfd != null) {
                    pfd.close();
                }
                return map;
            }
            if (pfd != null) {
                pfd.close();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (!copyToFile(tmpFile, res, id)) {
                return null;
            }
            return mmap(tmpFile);
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        StrictMode.ThreadPolicy old = StrictMode.allowThreadDiskWrites();
        try {
            try {
                os = new FileOutputStream(file, false);
                byte[] buffer = new byte[1024];
                while (true) {
                    int readLen = is.read(buffer);
                    if (readLen == -1) {
                        closeQuietly(os);
                        StrictMode.setThreadPolicy(old);
                        return true;
                    }
                    os.write(buffer, 0, readLen);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
                closeQuietly(os);
                StrictMode.setThreadPolicy(old);
                return false;
            }
        } catch (Throwable th) {
            closeQuietly(os);
            StrictMode.setThreadPolicy(old);
            throw th;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}