package org.godotengine.godot.utils;

import android.os.Build;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.godotengine.godot.BuildConfig;
import org.godotengine.godot.io.file.FileAccessFlags;
import org.godotengine.godot.io.file.FileAccessHandler;
import org.json.JSONObject;

/* compiled from: BenchmarkUtils.kt */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0001\u001a\u001e\u0010\u0015\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0007\u001a\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001a\u0010\u0002\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006\"\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u001a"}, d2 = {"TAG", "", "benchmarkFile", "getBenchmarkFile", "()Ljava/lang/String;", "setBenchmarkFile", "(Ljava/lang/String;)V", "benchmarkTracker", "Ljava/util/concurrent/ConcurrentSkipListMap;", "", "startBenchmarkFrom", "", "useBenchmark", "", "getUseBenchmark", "()Z", "setUseBenchmark", "(Z)V", "beginBenchmarkMeasure", "", "label", "dumpBenchmark", "fileAccessHandler", "Lorg/godotengine/godot/io/file/FileAccessHandler;", "filepath", "endBenchmarkMeasure", "lib_templateDebug"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class BenchmarkUtils {
    private static final String TAG = "GodotBenchmark";
    private static boolean useBenchmark;
    private static String benchmarkFile = "";
    private static final ConcurrentSkipListMap<String, Long> startBenchmarkFrom = new ConcurrentSkipListMap<>();
    private static final ConcurrentSkipListMap<String, Double> benchmarkTracker = new ConcurrentSkipListMap<>();

    public static final void dumpBenchmark(FileAccessHandler fileAccessHandler) {
        dumpBenchmark$default(fileAccessHandler, null, 2, null);
    }

    public static final boolean getUseBenchmark() {
        return useBenchmark;
    }

    public static final void setUseBenchmark(boolean z) {
        useBenchmark = z;
    }

    public static final String getBenchmarkFile() {
        return benchmarkFile;
    }

    public static final void setBenchmarkFile(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        benchmarkFile = str;
    }

    public static final void beginBenchmarkMeasure(String label) {
        Intrinsics.checkNotNullParameter(label, "label");
        if (!Intrinsics.areEqual(BuildConfig.FLAVOR, "editor") || !Intrinsics.areEqual("debug", "dev")) {
            return;
        }
        startBenchmarkFrom.put(label, Long.valueOf(SystemClock.elapsedRealtime()));
        if (Build.VERSION.SDK_INT >= 29) {
            Trace.beginAsyncSection(label, 0);
        }
    }

    public static final void endBenchmarkMeasure(String label) {
        Long l;
        Intrinsics.checkNotNullParameter(label, "label");
        if (Intrinsics.areEqual(BuildConfig.FLAVOR, "editor") && Intrinsics.areEqual("debug", "dev") && (l = startBenchmarkFrom.get(label)) != null) {
            long startTime = l.longValue();
            long total = SystemClock.elapsedRealtime() - startTime;
            benchmarkTracker.put(label, Double.valueOf(total / 1000.0d));
            if (Build.VERSION.SDK_INT >= 29) {
                Trace.endAsyncSection(label, 0);
            }
        }
    }

    public static /* synthetic */ void dumpBenchmark$default(FileAccessHandler fileAccessHandler, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = benchmarkFile;
        }
        dumpBenchmark(fileAccessHandler, str);
    }

    public static final void dumpBenchmark(FileAccessHandler fileAccessHandler, String filepath) {
        int fileId;
        if (!Intrinsics.areEqual(BuildConfig.FLAVOR, "editor") || !Intrinsics.areEqual("debug", "dev") || !useBenchmark) {
            return;
        }
        Map $this$map$iv = benchmarkTracker;
        Collection destination$iv$iv = new ArrayList($this$map$iv.size());
        for (Map.Entry item$iv$iv : $this$map$iv.entrySet()) {
            destination$iv$iv.add("\t- " + ((Object) item$iv$iv.getKey()) + " : " + item$iv$iv.getValue() + " sec.");
        }
        String printOut = CollectionsKt.joinToString$default((List) destination$iv$iv, "\n", null, null, 0, null, null, 62, null);
        Log.i(TAG, "BENCHMARK:\n" + printOut);
        if (fileAccessHandler != null) {
            String str = filepath;
            if (!(str == null || StringsKt.isBlank(str)) && (fileId = fileAccessHandler.fileOpen$lib_templateDebug(filepath, FileAccessFlags.WRITE)) != 0) {
                String jsonOutput = new JSONObject(MapsKt.toMap(benchmarkTracker)).toString(4);
                Intrinsics.checkNotNullExpressionValue(jsonOutput, "jsonOutput");
                byte[] bytes = jsonOutput.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                fileAccessHandler.fileWrite(fileId, ByteBuffer.wrap(bytes));
                fileAccessHandler.fileClose(fileId);
            }
        }
    }
}