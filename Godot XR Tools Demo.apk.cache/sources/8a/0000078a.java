package org.godotengine.godot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.core.app.NotificationCompat;
import com.google.android.vending.expansion.downloader.Helpers;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.godotengine.godot.input.GodotEditText;
import org.godotengine.godot.io.directory.DirectoryAccessHandler;
import org.godotengine.godot.io.file.FileAccessHandler;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.GodotPluginRegistry;
import org.godotengine.godot.tts.GodotTTS;
import org.godotengine.godot.utils.BenchmarkUtils;
import org.godotengine.godot.utils.GodotNetUtils;
import org.godotengine.godot.utils.PermissionsUtil;
import org.godotengine.godot.xr.XRMode;

/* compiled from: Godot.kt */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u0000 ©\u00012\u00020\u0001:\u0002©\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010S\u001a\u00020T2\b\b\u0001\u0010U\u001a\u00020V2\b\b\u0001\u0010W\u001a\u00020V2\b\u0010X\u001a\u0004\u0018\u00010YH\u0002J\u0018\u0010S\u001a\u00020T2\u0006\u0010Z\u001a\u00020\u00072\u0006\u0010[\u001a\u00020\u0007H\u0003J\"\u0010S\u001a\u00020T2\u0006\u0010Z\u001a\u00020\u00072\u0006\u0010[\u001a\u00020\u00072\b\u0010X\u001a\u0004\u0018\u00010YH\u0002J\u001b\u0010\\\u001a\u00020V2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00070^H\u0003¢\u0006\u0002\u0010_J\b\u0010`\u001a\u00020TH\u0002J\u0010\u0010`\u001a\u00020\u00142\u0006\u0010a\u001a\u00020VH\u0003J\b\u0010b\u001a\u0004\u0018\u00010cJ\b\u0010d\u001a\u00020\u0007H\u0003J\b\u0010e\u001a\u0004\u0018\u00010\u0007J\u000e\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0015\u0010g\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010^¢\u0006\u0002\u0010hJ\n\u0010i\u001a\u0004\u0018\u00010\u0007H\u0003J\u0014\u0010j\u001a\u0004\u0018\u00010k2\b\u0010l\u001a\u0004\u0018\u00010kH\u0002J\u0006\u0010m\u001a\u00020\u0014J\b\u0010n\u001a\u00020TH\u0003J\u0006\u0010o\u001a\u00020\u0014J\b\u0010p\u001a\u00020\u0014H\u0002J\b\u0010q\u001a\u00020\u0014H\u0002J\u0012\u0010r\u001a\u00020\u00142\b\u0010s\u001a\u0004\u0018\u00010tH\u0002J\u0010\u0010u\u001a\u00020T2\u0006\u0010v\u001a\u00020\u0007H\u0003J\u0010\u0010w\u001a\u00020T2\u0006\u0010x\u001a\u00020\u0007H\u0003J\u0010\u0010y\u001a\u00020T2\u0006\u0010v\u001a\u00020\u0007H\u0003J\u0018\u0010z\u001a\u00020\u00142\u0006\u0010{\u001a\u00020\u00072\u0006\u0010|\u001a\u00020\u0007H\u0002J\u001a\u0010}\u001a\u00020T2\b\u0010~\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u007f\u001a\u00020VH\u0016J%\u0010\u0080\u0001\u001a\u00020T2\u0007\u0010\u0081\u0001\u001a\u00020V2\u0007\u0010\u0082\u0001\u001a\u00020V2\n\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001J\u0010\u0010\u0085\u0001\u001a\u00020T2\u0007\u0010\u0086\u0001\u001a\u00020@J\u000f\u0010\u0087\u0001\u001a\u00020T2\u0006\u0010?\u001a\u00020@J\u000f\u0010\u0088\u0001\u001a\u00020T2\u0006\u0010?\u001a\u00020@J\t\u0010\u0089\u0001\u001a\u00020TH\u0002J\t\u0010\u008a\u0001\u001a\u00020TH\u0002J\u0010\u0010\u008b\u0001\u001a\u00020\u00142\u0007\u0010\u0086\u0001\u001a\u00020@J\u001f\u0010\u008c\u0001\u001a\u0004\u0018\u00010\t2\u0007\u0010\u0086\u0001\u001a\u00020@2\t\b\u0002\u0010\u008d\u0001\u001a\u00020\tH\u0007J\u0010\u0010\u008e\u0001\u001a\u00020T2\u0007\u0010\u0086\u0001\u001a\u00020@J1\u0010\u008f\u0001\u001a\u00020T2\u0007\u0010\u0081\u0001\u001a\u00020V2\u000f\u0010\u0090\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070^2\b\u0010\u0091\u0001\u001a\u00030\u0092\u0001¢\u0006\u0003\u0010\u0093\u0001J\u0010\u0010\u0094\u0001\u001a\u00020T2\u0007\u0010\u0086\u0001\u001a\u00020@J\u0013\u0010\u0095\u0001\u001a\u00020T2\b\u0010\u0096\u0001\u001a\u00030\u0097\u0001H\u0016J\u000f\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\t\u0010\u0099\u0001\u001a\u00020TH\u0002J\u0012\u0010\u009a\u0001\u001a\u00020\u00142\t\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0007J\u0007\u0010\u009c\u0001\u001a\u00020\u0014J\t\u0010\u009d\u0001\u001a\u00020cH\u0002J\t\u0010\u009e\u0001\u001a\u00020TH\u0002J\u0010\u0010\u009f\u0001\u001a\u00020T2\u0007\u0010 \u0001\u001a\u00020YJ\u0010\u0010¡\u0001\u001a\u00020T2\u0007\u0010 \u0001\u001a\u00020YJ\u0012\u0010¢\u0001\u001a\u00020T2\t\u0010£\u0001\u001a\u0004\u0018\u00010\u0007J\u0012\u0010¤\u0001\u001a\u00020T2\u0007\u0010¥\u0001\u001a\u00020\u0014H\u0002J\t\u0010¦\u0001\u001a\u00020\u0014H\u0002J\u0012\u0010§\u0001\u001a\u00020T2\u0007\u0010¨\u0001\u001a\u00020VH\u0003R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b\u001d\u0010\u001eR\u001b\u0010!\u001a\u00020\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010 \u001a\u0004\b#\u0010$R\u001b\u0010&\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010 \u001a\u0004\b'\u0010\u001eR\u001b\u0010)\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010 \u001a\u0004\b*\u0010\u001eR\u001b\u0010,\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010 \u001a\u0004\b-\u0010\u001eR\u001b\u0010/\u001a\u0002008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b3\u0010 \u001a\u0004\b1\u00102R\u000e\u00104\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u00106\u001a\u000207¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u001b\u0010:\u001a\u00020;8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b>\u0010 \u001a\u0004\b<\u0010=R\u0010\u0010?\u001a\u0004\u0018\u00010@X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010A\u001a\u0004\u0018\u00010BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u000e\u0010G\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010H\u001a\u00020I¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010KR\u000e\u0010L\u001a\u00020MX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020RX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006ª\u0001"}, d2 = {"Lorg/godotengine/godot/Godot;", "Landroid/hardware/SensorEventListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "commandLine", "", "", "containerLayout", "Landroid/widget/FrameLayout;", "directoryAccessHandler", "Lorg/godotengine/godot/io/directory/DirectoryAccessHandler;", "getDirectoryAccessHandler", "()Lorg/godotengine/godot/io/directory/DirectoryAccessHandler;", "expansionPackPath", "fileAccessHandler", "Lorg/godotengine/godot/io/file/FileAccessHandler;", "getFileAccessHandler", "()Lorg/godotengine/godot/io/file/FileAccessHandler;", "initializationStarted", "", "io", "Lorg/godotengine/godot/GodotIO;", "getIo", "()Lorg/godotengine/godot/GodotIO;", "setIo", "(Lorg/godotengine/godot/GodotIO;)V", "mAccelerometer", "Landroid/hardware/Sensor;", "getMAccelerometer", "()Landroid/hardware/Sensor;", "mAccelerometer$delegate", "Lkotlin/Lazy;", "mClipboard", "Landroid/content/ClipboardManager;", "getMClipboard", "()Landroid/content/ClipboardManager;", "mClipboard$delegate", "mGravity", "getMGravity", "mGravity$delegate", "mGyroscope", "getMGyroscope", "mGyroscope$delegate", "mMagnetometer", "getMMagnetometer", "mMagnetometer$delegate", "mSensorManager", "Landroid/hardware/SensorManager;", "getMSensorManager", "()Landroid/hardware/SensorManager;", "mSensorManager$delegate", "nativeLayerInitializeCompleted", "nativeLayerSetupCompleted", "netUtils", "Lorg/godotengine/godot/utils/GodotNetUtils;", "getNetUtils", "()Lorg/godotengine/godot/utils/GodotNetUtils;", "pluginRegistry", "Lorg/godotengine/godot/plugin/GodotPluginRegistry;", "getPluginRegistry", "()Lorg/godotengine/godot/plugin/GodotPluginRegistry;", "pluginRegistry$delegate", "primaryHost", "Lorg/godotengine/godot/GodotHost;", "renderView", "Lorg/godotengine/godot/GodotRenderView;", "getRenderView", "()Lorg/godotengine/godot/GodotRenderView;", "setRenderView", "(Lorg/godotengine/godot/GodotRenderView;)V", "renderViewInitialized", "tts", "Lorg/godotengine/godot/tts/GodotTTS;", "getTts", "()Lorg/godotengine/godot/tts/GodotTTS;", "uiChangeListener", "Landroid/view/View$OnSystemUiVisibilityChangeListener;", "useApkExpansion", "useDebugOpengl", "useImmersive", "xrMode", "Lorg/godotengine/godot/xr/XRMode;", "alert", "", "messageResId", "", "titleResId", "okCallback", "Ljava/lang/Runnable;", "message", "title", "createNewGodotInstance", "args", "", "([Ljava/lang/String;)I", "forceQuit", "instanceId", "getActivity", "Landroid/app/Activity;", "getCACertificates", "getClipboard", "getCommandLine", "getGrantedPermissions", "()[Ljava/lang/String;", "getInputFallbackMapping", "getRotatedValues", "", "values", "hasClipboard", "initInputDevices", "isInitialized", "isNativeInitialized", "isOnUiThread", "meetsVulkanRequirements", "packageManager", "Landroid/content/pm/PackageManager;", "nativeBeginBenchmarkMeasure", "label", "nativeDumpBenchmark", "benchmarkFile", "nativeEndBenchmarkMeasure", "obbIsCorrupted", "f", "mainPackMd5", "onAccuracyChanged", "sensor", "accuracy", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "host", "onCreate", "onDestroy", "onGodotMainLoopStarted", "onGodotSetupCompleted", "onInitNativeLayer", "onInitRenderView", "providedContainerLayout", "onPause", "onRequestPermissionsResult", "permissions", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "onSensorChanged", NotificationCompat.CATEGORY_EVENT, "Landroid/hardware/SensorEvent;", "parseCommandLine", "registerUiChangeListener", "requestPermission", "name", "requestPermissions", "requireActivity", "restart", "runOnRenderThread", "action", "runOnUiThread", "setClipboard", "text", "setKeepScreenOn", "p_enabled", "usesVulkan", "vibrate", "durationMs", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Godot implements SensorEventListener {
    private static final Companion Companion = new Companion(null);
    @Deprecated
    private static final String TAG = Godot.class.getSimpleName();
    private List<String> commandLine;
    private FrameLayout containerLayout;
    private final Context context;
    private final DirectoryAccessHandler directoryAccessHandler;
    private String expansionPackPath;
    private final FileAccessHandler fileAccessHandler;
    private boolean initializationStarted;
    private GodotIO io;
    private final Lazy mAccelerometer$delegate;
    private final Lazy mClipboard$delegate;
    private final Lazy mGravity$delegate;
    private final Lazy mGyroscope$delegate;
    private final Lazy mMagnetometer$delegate;
    private final Lazy mSensorManager$delegate;
    private boolean nativeLayerInitializeCompleted;
    private boolean nativeLayerSetupCompleted;
    private final GodotNetUtils netUtils;
    private final Lazy pluginRegistry$delegate;
    private GodotHost primaryHost;
    private GodotRenderView renderView;
    private boolean renderViewInitialized;
    private final GodotTTS tts;
    private final View.OnSystemUiVisibilityChangeListener uiChangeListener;
    private boolean useApkExpansion;
    private boolean useDebugOpengl;
    private boolean useImmersive;
    private XRMode xrMode;

    public final FrameLayout onInitRenderView(GodotHost host) {
        Intrinsics.checkNotNullParameter(host, "host");
        return onInitRenderView$default(this, host, null, 2, null);
    }

    public Godot(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.pluginRegistry$delegate = LazyKt.lazy(new Function0<GodotPluginRegistry>() { // from class: org.godotengine.godot.Godot$pluginRegistry$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final GodotPluginRegistry invoke() {
                return GodotPluginRegistry.initializePluginRegistry(Godot.this);
            }
        });
        this.mSensorManager$delegate = LazyKt.lazy(new Function0<SensorManager>() { // from class: org.godotengine.godot.Godot$mSensorManager$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SensorManager invoke() {
                Activity requireActivity;
                requireActivity = Godot.this.requireActivity();
                Object systemService = requireActivity.getSystemService("sensor");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.SensorManager");
                return (SensorManager) systemService;
            }
        });
        this.mAccelerometer$delegate = LazyKt.lazy(new Function0<Sensor>() { // from class: org.godotengine.godot.Godot$mAccelerometer$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Sensor invoke() {
                SensorManager mSensorManager;
                mSensorManager = Godot.this.getMSensorManager();
                return mSensorManager.getDefaultSensor(1);
            }
        });
        this.mGravity$delegate = LazyKt.lazy(new Function0<Sensor>() { // from class: org.godotengine.godot.Godot$mGravity$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Sensor invoke() {
                SensorManager mSensorManager;
                mSensorManager = Godot.this.getMSensorManager();
                return mSensorManager.getDefaultSensor(9);
            }
        });
        this.mMagnetometer$delegate = LazyKt.lazy(new Function0<Sensor>() { // from class: org.godotengine.godot.Godot$mMagnetometer$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Sensor invoke() {
                SensorManager mSensorManager;
                mSensorManager = Godot.this.getMSensorManager();
                return mSensorManager.getDefaultSensor(2);
            }
        });
        this.mGyroscope$delegate = LazyKt.lazy(new Function0<Sensor>() { // from class: org.godotengine.godot.Godot$mGyroscope$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Sensor invoke() {
                SensorManager mSensorManager;
                mSensorManager = Godot.this.getMSensorManager();
                return mSensorManager.getDefaultSensor(4);
            }
        });
        this.mClipboard$delegate = LazyKt.lazy(new Function0<ClipboardManager>() { // from class: org.godotengine.godot.Godot$mClipboard$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ClipboardManager invoke() {
                Activity requireActivity;
                requireActivity = Godot.this.requireActivity();
                Object systemService = requireActivity.getSystemService("clipboard");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
                return (ClipboardManager) systemService;
            }
        });
        this.uiChangeListener = new View.OnSystemUiVisibilityChangeListener() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda0
            @Override // android.view.View.OnSystemUiVisibilityChangeListener
            public final void onSystemUiVisibilityChange(int i) {
                Godot.m1509uiChangeListener$lambda0(Godot.this, i);
            }
        };
        this.tts = new GodotTTS(context);
        this.directoryAccessHandler = new DirectoryAccessHandler(context);
        this.fileAccessHandler = new FileAccessHandler(context);
        this.netUtils = new GodotNetUtils(context);
        this.commandLine = new ArrayList();
        this.xrMode = XRMode.REGULAR;
        this.expansionPackPath = "";
    }

    /* compiled from: Godot.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/godotengine/godot/Godot$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final GodotPluginRegistry getPluginRegistry() {
        Object value = this.pluginRegistry$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-pluginRegistry>(...)");
        return (GodotPluginRegistry) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SensorManager getMSensorManager() {
        return (SensorManager) this.mSensorManager$delegate.getValue();
    }

    private final Sensor getMAccelerometer() {
        Object value = this.mAccelerometer$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mAccelerometer>(...)");
        return (Sensor) value;
    }

    private final Sensor getMGravity() {
        Object value = this.mGravity$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mGravity>(...)");
        return (Sensor) value;
    }

    private final Sensor getMMagnetometer() {
        Object value = this.mMagnetometer$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mMagnetometer>(...)");
        return (Sensor) value;
    }

    private final Sensor getMGyroscope() {
        Object value = this.mGyroscope$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mGyroscope>(...)");
        return (Sensor) value;
    }

    private final ClipboardManager getMClipboard() {
        return (ClipboardManager) this.mClipboard$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: uiChangeListener$lambda-0  reason: not valid java name */
    public static final void m1509uiChangeListener$lambda0(Godot this$0, int visibility) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if ((visibility & 4) == 0) {
            View decorView = this$0.requireActivity().getWindow().getDecorView();
            Intrinsics.checkNotNullExpressionValue(decorView, "requireActivity().window.decorView");
            decorView.setSystemUiVisibility(5894);
        }
    }

    public final GodotTTS getTts() {
        return this.tts;
    }

    public final DirectoryAccessHandler getDirectoryAccessHandler() {
        return this.directoryAccessHandler;
    }

    public final FileAccessHandler getFileAccessHandler() {
        return this.fileAccessHandler;
    }

    public final GodotNetUtils getNetUtils() {
        return this.netUtils;
    }

    public final GodotIO getIo() {
        return this.io;
    }

    public final void setIo(GodotIO godotIO) {
        this.io = godotIO;
    }

    public final GodotRenderView getRenderView() {
        return this.renderView;
    }

    public final void setRenderView(GodotRenderView godotRenderView) {
        this.renderView = godotRenderView;
    }

    private final boolean isNativeInitialized() {
        return this.nativeLayerInitializeCompleted && this.nativeLayerSetupCompleted;
    }

    public final boolean isInitialized() {
        return this.initializationStarted && isNativeInitialized() && this.renderViewInitialized;
    }

    public final Activity getActivity() {
        GodotHost godotHost = this.primaryHost;
        if (godotHost != null) {
            return godotHost.getActivity();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Activity requireActivity() {
        Activity activity = getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException("Host activity must be non-null");
    }

    public final void onCreate(GodotHost primaryHost) {
        Intrinsics.checkNotNullParameter(primaryHost, "primaryHost");
        if (this.primaryHost != null || this.initializationStarted) {
            Log.d(TAG, "OnCreate already invoked");
            return;
        }
        BenchmarkUtils.beginBenchmarkMeasure("Godot::onCreate");
        try {
            try {
                this.primaryHost = primaryHost;
                Activity activity = requireActivity();
                Window window = activity.getWindow();
                window.addFlags(2097152);
                GodotPluginRegistry.initializePluginRegistry(this);
                if (this.io == null) {
                    this.io = new GodotIO(activity);
                }
                this.commandLine = getCommandLine();
                String mainPackMd5 = null;
                String mainPackKey = null;
                List newArgs = new ArrayList();
                int i = 0;
                while (i < this.commandLine.size()) {
                    boolean hasExtra = i < this.commandLine.size() - 1;
                    if (Intrinsics.areEqual(this.commandLine.get(i), XRMode.REGULAR.cmdLineArg)) {
                        this.xrMode = XRMode.REGULAR;
                    } else if (Intrinsics.areEqual(this.commandLine.get(i), XRMode.OPENXR.cmdLineArg)) {
                        this.xrMode = XRMode.OPENXR;
                    } else if (Intrinsics.areEqual(this.commandLine.get(i), "--debug_opengl")) {
                        this.useDebugOpengl = true;
                    } else if (Intrinsics.areEqual(this.commandLine.get(i), "--use_immersive")) {
                        this.useImmersive = true;
                        window.getDecorView().setSystemUiVisibility(5894);
                        registerUiChangeListener();
                    } else if (Intrinsics.areEqual(this.commandLine.get(i), "--use_apk_expansion")) {
                        this.useApkExpansion = true;
                    } else if (hasExtra && Intrinsics.areEqual(this.commandLine.get(i), "--apk_expansion_md5")) {
                        mainPackMd5 = this.commandLine.get(i + 1);
                        i++;
                    } else if (hasExtra && Intrinsics.areEqual(this.commandLine.get(i), "--apk_expansion_key")) {
                        mainPackKey = this.commandLine.get(i + 1);
                        SharedPreferences prefs = activity.getSharedPreferences("app_data_keys", 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("store_public_key", mainPackKey);
                        editor.apply();
                        i++;
                    } else if (Intrinsics.areEqual(this.commandLine.get(i), "--benchmark")) {
                        BenchmarkUtils.setUseBenchmark(true);
                        newArgs.add(this.commandLine.get(i));
                    } else if (hasExtra && Intrinsics.areEqual(this.commandLine.get(i), "--benchmark-file")) {
                        BenchmarkUtils.setUseBenchmark(true);
                        newArgs.add(this.commandLine.get(i));
                        BenchmarkUtils.setBenchmarkFile(this.commandLine.get(i + 1));
                        newArgs.add(this.commandLine.get(i + 1));
                        i++;
                    } else {
                        if (StringsKt.trim((CharSequence) this.commandLine.get(i)).toString().length() > 0) {
                            newArgs.add(this.commandLine.get(i));
                        }
                    }
                    i++;
                }
                if (newArgs.isEmpty()) {
                    this.commandLine = new ArrayList();
                } else {
                    this.commandLine = newArgs;
                }
                if (this.useApkExpansion && mainPackMd5 != null && mainPackKey != null) {
                    try {
                        String saveFilePath = Helpers.getSaveFilePath(this.context);
                        Intrinsics.checkNotNullExpressionValue(saveFilePath, "getSaveFilePath(context)");
                        this.expansionPackPath = saveFilePath;
                        this.expansionPackPath = saveFilePath + "/main." + activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode + "." + activity.getPackageName() + ".obb";
                    } catch (Exception e) {
                        Log.e(TAG, "Unable to build full path to the app's expansion files", e);
                    }
                    File f = new File(this.expansionPackPath);
                    boolean packValid = true;
                    if (!f.exists()) {
                        packValid = false;
                    } else if (obbIsCorrupted(this.expansionPackPath, mainPackMd5)) {
                        packValid = false;
                        try {
                            f.delete();
                        } catch (Exception e2) {
                        }
                    }
                    if (!packValid) {
                        throw new IllegalArgumentException("Invalid expansion pack");
                    }
                }
                this.initializationStarted = true;
            } catch (Exception e3) {
                this.primaryHost = null;
                this.initializationStarted = false;
                throw e3;
            }
        } finally {
            BenchmarkUtils.endBenchmarkMeasure("Godot::onCreate");
        }
    }

    public final boolean onInitNativeLayer(GodotHost host) {
        Intrinsics.checkNotNullParameter(host, "host");
        if (!this.initializationStarted) {
            throw new IllegalStateException("OnCreate must be invoked successfully prior to initializing the native layer");
        }
        if (isNativeInitialized()) {
            Log.d(TAG, "OnInitNativeLayer already invoked");
            return true;
        } else if (!Intrinsics.areEqual(host, this.primaryHost)) {
            Log.e(TAG, "Native initialization is only supported for the primary host");
            return false;
        } else {
            if (this.expansionPackPath.length() > 0) {
                this.commandLine.add("--main-pack");
                this.commandLine.add(this.expansionPackPath);
            }
            Activity activity = requireActivity();
            if (!this.nativeLayerInitializeCompleted) {
                this.nativeLayerInitializeCompleted = GodotLib.initialize(activity, this, activity.getAssets(), this.io, this.netUtils, this.directoryAccessHandler, this.fileAccessHandler, this.useApkExpansion);
            }
            if (this.nativeLayerInitializeCompleted && !this.nativeLayerSetupCompleted) {
                Collection $this$toTypedArray$iv = this.commandLine;
                Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
                Intrinsics.checkNotNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                boolean upVar = GodotLib.setup((String[]) array, this.tts);
                this.nativeLayerSetupCompleted = upVar;
                if (!upVar) {
                    Log.e(TAG, "Unable to setup the Godot engine! Aborting...");
                    alert(R.string.error_engine_setup_message, R.string.text_error_title, new Godot$$ExternalSyntheticLambda3(this));
                }
            }
            return isNativeInitialized();
        }
    }

    public static /* synthetic */ FrameLayout onInitRenderView$default(Godot godot, GodotHost godotHost, FrameLayout frameLayout, int i, Object obj) {
        if ((i & 2) != 0) {
            frameLayout = new FrameLayout(godotHost.getActivity());
        }
        return godot.onInitRenderView(godotHost, frameLayout);
    }

    public final FrameLayout onInitRenderView(GodotHost host, FrameLayout providedContainerLayout) {
        GodotGLRenderView godotGLRenderView;
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(providedContainerLayout, "providedContainerLayout");
        if (!isNativeInitialized()) {
            throw new IllegalStateException("onInitNativeLayer() must be invoked successfully prior to initializing the render view");
        }
        try {
            Activity activity = host.getActivity();
            Intrinsics.checkNotNullExpressionValue(activity, "host.activity");
            this.containerLayout = providedContainerLayout;
            if (providedContainerLayout != null) {
                providedContainerLayout.removeAllViews();
            }
            FrameLayout frameLayout = this.containerLayout;
            if (frameLayout != null) {
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            }
            GodotEditText editText = new GodotEditText(activity);
            editText.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) activity.getResources().getDimension(R.dimen.text_edit_height)));
            FrameLayout frameLayout2 = this.containerLayout;
            if (frameLayout2 != null) {
                frameLayout2.addView(editText);
            }
            if (usesVulkan()) {
                if (!meetsVulkanRequirements(activity.getPackageManager())) {
                    alert(R.string.error_missing_vulkan_requirements_message, R.string.text_error_title, new Godot$$ExternalSyntheticLambda3(this));
                    return null;
                }
                godotGLRenderView = new GodotVulkanRenderView(host, this);
            } else {
                godotGLRenderView = new GodotGLRenderView(host, this, this.xrMode, this.useDebugOpengl);
            }
            this.renderView = godotGLRenderView;
            if (Intrinsics.areEqual(host, this.primaryHost)) {
                GodotRenderView godotRenderView = this.renderView;
                Intrinsics.checkNotNull(godotRenderView);
                godotRenderView.startRenderer();
            }
            GodotRenderView godotRenderView2 = this.renderView;
            Intrinsics.checkNotNull(godotRenderView2);
            View view = godotRenderView2.getView();
            Intrinsics.checkNotNullExpressionValue(view, "renderView!!.view");
            View view2 = view;
            FrameLayout frameLayout3 = this.containerLayout;
            if (frameLayout3 != null) {
                frameLayout3.addView(view2, new ViewGroup.LayoutParams(-1, -1));
            }
            editText.setView(this.renderView);
            GodotIO godotIO = this.io;
            if (godotIO != null) {
                godotIO.setEdit(editText);
            }
            if (Build.VERSION.SDK_INT >= 30) {
                final View decorView = activity.getWindow().getDecorView();
                Intrinsics.checkNotNullExpressionValue(decorView, "activity.window.decorView");
                decorView.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback() { // from class: org.godotengine.godot.Godot$onInitRenderView$2
                    private int endBottom;
                    private int startBottom;

                    /* JADX INFO: Access modifiers changed from: package-private */
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    public final int getStartBottom() {
                        return this.startBottom;
                    }

                    public final void setStartBottom(int i) {
                        this.startBottom = i;
                    }

                    public final int getEndBottom() {
                        return this.endBottom;
                    }

                    public final void setEndBottom(int i) {
                        this.endBottom = i;
                    }

                    @Override // android.view.WindowInsetsAnimation.Callback
                    public void onPrepare(WindowInsetsAnimation animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        this.startBottom = decorView.getRootWindowInsets().getInsets(WindowInsets.Type.ime()).bottom;
                    }

                    @Override // android.view.WindowInsetsAnimation.Callback
                    public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation animation, WindowInsetsAnimation.Bounds bounds) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Intrinsics.checkNotNullParameter(bounds, "bounds");
                        this.endBottom = decorView.getRootWindowInsets().getInsets(WindowInsets.Type.ime()).bottom;
                        return bounds;
                    }

                    @Override // android.view.WindowInsetsAnimation.Callback
                    public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                        Intrinsics.checkNotNullParameter(windowInsets, "windowInsets");
                        Intrinsics.checkNotNullParameter(list, "list");
                        WindowInsetsAnimation imeAnimation = null;
                        Iterator<WindowInsetsAnimation> it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            WindowInsetsAnimation animation = it.next();
                            if ((animation.getTypeMask() & WindowInsets.Type.ime()) != 0) {
                                imeAnimation = animation;
                                break;
                            }
                        }
                        if (imeAnimation != null) {
                            float interpolatedFraction = imeAnimation.getInterpolatedFraction();
                            float keyboardHeight = (this.startBottom * (1.0f - interpolatedFraction)) + (this.endBottom * interpolatedFraction);
                            GodotLib.setVirtualKeyboardHeight((int) keyboardHeight);
                        }
                        return windowInsets;
                    }

                    @Override // android.view.WindowInsetsAnimation.Callback
                    public void onEnd(WindowInsetsAnimation animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }
                });
            } else {
                view2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.godotengine.godot.Godot$onInitRenderView$3
                    private final Rect visibleSize = new Rect();

                    public final Rect getVisibleSize() {
                        return this.visibleSize;
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        GodotRenderView renderView = Godot.this.getRenderView();
                        Intrinsics.checkNotNull(renderView);
                        SurfaceView surfaceView = renderView.getView();
                        surfaceView.getWindowVisibleDisplayFrame(this.visibleSize);
                        int keyboardHeight = surfaceView.getHeight() - this.visibleSize.bottom;
                        GodotLib.setVirtualKeyboardHeight(keyboardHeight);
                    }
                });
            }
            if (Intrinsics.areEqual(host, this.primaryHost)) {
                GodotRenderView godotRenderView3 = this.renderView;
                Intrinsics.checkNotNull(godotRenderView3);
                godotRenderView3.queueOnRenderThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Godot.m1503onInitRenderView$lambda1(Godot.this);
                    }
                });
                for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
                    View pluginView = plugin.onMainCreate(activity);
                    if (pluginView != null) {
                        if (plugin.shouldBeOnTop()) {
                            FrameLayout frameLayout4 = this.containerLayout;
                            if (frameLayout4 != null) {
                                frameLayout4.addView(pluginView);
                            }
                        } else {
                            FrameLayout frameLayout5 = this.containerLayout;
                            if (frameLayout5 != null) {
                                frameLayout5.addView(pluginView, 0);
                            }
                        }
                    }
                }
            }
            this.renderViewInitialized = true;
            return this.containerLayout;
        } finally {
            if (!this.renderViewInitialized) {
                FrameLayout frameLayout6 = this.containerLayout;
                if (frameLayout6 != null) {
                    frameLayout6.removeAllViews();
                }
                this.containerLayout = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onInitRenderView$lambda-1  reason: not valid java name */
    public static final void m1503onInitRenderView$lambda1(Godot this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        for (GodotPlugin plugin : this$0.getPluginRegistry().getAllPlugins()) {
            plugin.onRegisterPluginWithGodotNative();
        }
        this$0.setKeepScreenOn(Boolean.parseBoolean(GodotLib.getGlobal("display/window/energy_saving/keep_screen_on")));
    }

    public final void onResume(GodotHost host) {
        Intrinsics.checkNotNullParameter(host, "host");
        if (!Intrinsics.areEqual(host, this.primaryHost)) {
            return;
        }
        GodotRenderView godotRenderView = this.renderView;
        Intrinsics.checkNotNull(godotRenderView);
        godotRenderView.onActivityResumed();
        getMSensorManager().registerListener(this, getMAccelerometer(), 1);
        getMSensorManager().registerListener(this, getMGravity(), 1);
        getMSensorManager().registerListener(this, getMMagnetometer(), 1);
        getMSensorManager().registerListener(this, getMGyroscope(), 1);
        if (this.useImmersive) {
            Window window = requireActivity().getWindow();
            window.getDecorView().setSystemUiVisibility(5894);
        }
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onMainResume();
        }
    }

    public final void onPause(GodotHost host) {
        Intrinsics.checkNotNullParameter(host, "host");
        if (!Intrinsics.areEqual(host, this.primaryHost)) {
            return;
        }
        GodotRenderView godotRenderView = this.renderView;
        Intrinsics.checkNotNull(godotRenderView);
        godotRenderView.onActivityPaused();
        getMSensorManager().unregisterListener(this);
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onMainPause();
        }
    }

    public final void onDestroy(GodotHost primaryHost) {
        Intrinsics.checkNotNullParameter(primaryHost, "primaryHost");
        if (!Intrinsics.areEqual(this.primaryHost, primaryHost)) {
            return;
        }
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onMainDestroy();
        }
        GodotLib.ondestroy();
        forceQuit();
    }

    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onMainActivityResult(requestCode, resultCode, data);
        }
    }

    public final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onMainRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        int length = permissions.length;
        for (int i = 0; i < length; i++) {
            GodotLib.requestPermissionResult(permissions[i], grantResults[i] == 0);
        }
    }

    private final void onGodotSetupCompleted() {
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onGodotSetupCompleted();
        }
        GodotHost godotHost = this.primaryHost;
        if (godotHost != null) {
            godotHost.onGodotSetupCompleted();
        }
    }

    private final void onGodotMainLoopStarted() {
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            plugin.onGodotMainLoopStarted();
        }
        GodotHost godotHost = this.primaryHost;
        if (godotHost != null) {
            godotHost.onGodotMainLoopStarted();
        }
    }

    private final void restart() {
        GodotHost godotHost = this.primaryHost;
        if (godotHost != null) {
            godotHost.onGodotRestartRequested(this);
        }
    }

    private final void registerUiChangeListener() {
        View decorView = requireActivity().getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "requireActivity().window.decorView");
        decorView.setOnSystemUiVisibilityChangeListener(this.uiChangeListener);
    }

    private final void alert(String message, String title) {
        alert(message, title, (Runnable) null);
    }

    private final void alert(int messageResId, int titleResId, Runnable okCallback) {
        Activity activity = getActivity();
        Resources res = activity != null ? activity.getResources() : null;
        if (res == null) {
            return;
        }
        String string = res.getString(messageResId);
        Intrinsics.checkNotNullExpressionValue(string, "res.getString(messageResId)");
        String string2 = res.getString(titleResId);
        Intrinsics.checkNotNullExpressionValue(string2, "res.getString(titleResId)");
        alert(string, string2, okCallback);
    }

    private final void alert(final String message, final String title, final Runnable okCallback) {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                Godot.m1500alert$lambda3(activity, message, title, okCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: alert$lambda-3  reason: not valid java name */
    public static final void m1500alert$lambda3(Activity activity, String message, String title, final Runnable $okCallback) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(message, "$message");
        Intrinsics.checkNotNullParameter(title, "$title");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Godot.m1501alert$lambda3$lambda2($okCallback, dialogInterface, i);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: alert$lambda-3$lambda-2  reason: not valid java name */
    public static final void m1501alert$lambda3$lambda2(Runnable $okCallback, DialogInterface dialog, int id) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        if ($okCallback != null) {
            $okCallback.run();
        }
        dialog.cancel();
    }

    public final void runOnRenderThread(Runnable action) {
        Intrinsics.checkNotNullParameter(action, "action");
        GodotRenderView godotRenderView = this.renderView;
        if (godotRenderView != null) {
            Intrinsics.checkNotNull(godotRenderView);
            godotRenderView.queueOnRenderThread(action);
        }
    }

    public final void runOnUiThread(Runnable action) {
        Intrinsics.checkNotNullParameter(action, "action");
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(action);
    }

    private final boolean isOnUiThread() {
        return Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper());
    }

    private final boolean usesVulkan() {
        String renderer = GodotLib.getGlobal("rendering/renderer/rendering_method");
        String renderingDevice = GodotLib.getGlobal("rendering/rendering_device/driver");
        return (Intrinsics.areEqual("forward_plus", renderer) || Intrinsics.areEqual("mobile", renderer)) && Intrinsics.areEqual("vulkan", renderingDevice);
    }

    private final boolean meetsVulkanRequirements(PackageManager packageManager) {
        if (packageManager == null) {
            return false;
        }
        if (!packageManager.hasSystemFeature("android.hardware.vulkan.level", 1)) {
            Log.w(TAG, "The vulkan hardware level does not meet the minimum requirement: 1");
        }
        return packageManager.hasSystemFeature("android.hardware.vulkan.version", 4194307);
    }

    private final void setKeepScreenOn(final boolean p_enabled) {
        runOnUiThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                Godot.m1508setKeepScreenOn$lambda4(p_enabled, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setKeepScreenOn$lambda-4  reason: not valid java name */
    public static final void m1508setKeepScreenOn$lambda4(boolean $p_enabled, Godot this$0) {
        Window window;
        Window window2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if ($p_enabled) {
            Activity activity = this$0.getActivity();
            if (activity != null && (window2 = activity.getWindow()) != null) {
                window2.addFlags(128);
                return;
            }
            return;
        }
        Activity activity2 = this$0.getActivity();
        if (activity2 != null && (window = activity2.getWindow()) != null) {
            window.clearFlags(128);
        }
    }

    public final boolean hasClipboard() {
        return getMClipboard().hasPrimaryClip();
    }

    public final String getClipboard() {
        CharSequence text;
        ClipData clipData = getMClipboard().getPrimaryClip();
        return (clipData == null || (text = clipData.getItemAt(0).getText()) == null) ? "" : text.toString();
    }

    public final void setClipboard(String text) {
        ClipData clip = ClipData.newPlainText("myLabel", text);
        getMClipboard().setPrimaryClip(clip);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forceQuit() {
        forceQuit(0);
    }

    private final boolean forceQuit(int instanceId) {
        GodotHost godotHost = this.primaryHost;
        if (godotHost == null) {
            return false;
        }
        if (instanceId == 0) {
            Intrinsics.checkNotNull(godotHost);
            godotHost.onGodotForceQuit(this);
            return true;
        }
        Intrinsics.checkNotNull(godotHost);
        return godotHost.onGodotForceQuit(instanceId);
    }

    public final void onBackPressed(GodotHost host) {
        GodotRenderView godotRenderView;
        Intrinsics.checkNotNullParameter(host, "host");
        if (!Intrinsics.areEqual(host, this.primaryHost)) {
            return;
        }
        boolean shouldQuit = true;
        for (GodotPlugin plugin : getPluginRegistry().getAllPlugins()) {
            if (plugin.onMainBackPressed()) {
                shouldQuit = false;
            }
        }
        if (shouldQuit && (godotRenderView = this.renderView) != null) {
            Intrinsics.checkNotNull(godotRenderView);
            godotRenderView.queueOnRenderThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    GodotLib.back();
                }
            });
        }
    }

    private final float[] getRotatedValues(float[] values) {
        if (values == null || values.length != 3) {
            return values;
        }
        Object systemService = requireActivity().getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        Display display = ((WindowManager) systemService).getDefaultDisplay();
        int displayRotation = display.getRotation();
        float[] rotatedValues = new float[3];
        switch (displayRotation) {
            case 0:
                rotatedValues[0] = values[0];
                rotatedValues[1] = values[1];
                rotatedValues[2] = values[2];
                break;
            case 1:
                rotatedValues[0] = -values[1];
                rotatedValues[1] = values[0];
                rotatedValues[2] = values[2];
                break;
            case 2:
                rotatedValues[0] = -values[0];
                rotatedValues[1] = -values[1];
                rotatedValues[2] = values[2];
                break;
            case 3:
                rotatedValues[0] = values[1];
                rotatedValues[1] = -values[0];
                rotatedValues[2] = values[2];
                break;
        }
        return rotatedValues;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.renderView == null) {
            return;
        }
        switch (event.sensor.getType()) {
            case 1:
                final float[] rotatedValues = getRotatedValues(event.values);
                GodotRenderView godotRenderView = this.renderView;
                Intrinsics.checkNotNull(godotRenderView);
                godotRenderView.queueOnRenderThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        Godot.m1504onSensorChanged$lambda6(rotatedValues);
                    }
                });
                return;
            case 2:
                float[] rotatedValues2 = event.values;
                final float[] rotatedValues3 = getRotatedValues(rotatedValues2);
                GodotRenderView godotRenderView2 = this.renderView;
                Intrinsics.checkNotNull(godotRenderView2);
                godotRenderView2.queueOnRenderThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        Godot.m1506onSensorChanged$lambda8(rotatedValues3);
                    }
                });
                return;
            case 4:
                final float[] rotatedValues4 = getRotatedValues(event.values);
                GodotRenderView godotRenderView3 = this.renderView;
                Intrinsics.checkNotNull(godotRenderView3);
                godotRenderView3.queueOnRenderThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        Godot.m1507onSensorChanged$lambda9(rotatedValues4);
                    }
                });
                return;
            case 9:
                final float[] rotatedValues5 = getRotatedValues(event.values);
                GodotRenderView godotRenderView4 = this.renderView;
                Intrinsics.checkNotNull(godotRenderView4);
                godotRenderView4.queueOnRenderThread(new Runnable() { // from class: org.godotengine.godot.Godot$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        Godot.m1505onSensorChanged$lambda7(rotatedValues5);
                    }
                });
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSensorChanged$lambda-6  reason: not valid java name */
    public static final void m1504onSensorChanged$lambda6(float[] $rotatedValues) {
        Intrinsics.checkNotNull($rotatedValues);
        GodotLib.accelerometer(-$rotatedValues[0], -$rotatedValues[1], -$rotatedValues[2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSensorChanged$lambda-7  reason: not valid java name */
    public static final void m1505onSensorChanged$lambda7(float[] $rotatedValues) {
        Intrinsics.checkNotNull($rotatedValues);
        GodotLib.gravity(-$rotatedValues[0], -$rotatedValues[1], -$rotatedValues[2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSensorChanged$lambda-8  reason: not valid java name */
    public static final void m1506onSensorChanged$lambda8(float[] $rotatedValues) {
        Intrinsics.checkNotNull($rotatedValues);
        GodotLib.magnetometer(-$rotatedValues[0], -$rotatedValues[1], -$rotatedValues[2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSensorChanged$lambda-9  reason: not valid java name */
    public static final void m1507onSensorChanged$lambda9(float[] $rotatedValues) {
        Intrinsics.checkNotNull($rotatedValues);
        GodotLib.gyroscope($rotatedValues[0], $rotatedValues[1], $rotatedValues[2]);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private final void vibrate(int durationMs) {
        if (durationMs > 0 && requestPermission("VIBRATE")) {
            Activity activity = getActivity();
            Vibrator vibratorService = (Vibrator) (activity != null ? activity.getSystemService("vibrator") : null);
            if (vibratorService == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 26) {
                vibratorService.vibrate(VibrationEffect.createOneShot(durationMs, -1));
            } else {
                vibratorService.vibrate(durationMs);
            }
        }
    }

    private final List<String> getCommandLine() {
        List original = parseCommandLine();
        GodotHost godotHost = this.primaryHost;
        List hostCommandLine = godotHost != null ? godotHost.getCommandLine() : null;
        if (hostCommandLine != null && (!hostCommandLine.isEmpty())) {
            original.addAll(hostCommandLine);
        }
        return original;
    }

    private final List<String> parseCommandLine() {
        try {
            InputStream inputStream = requireActivity().getAssets().open("_cl_");
            Intrinsics.checkNotNullExpressionValue(inputStream, "requireActivity().assets.open(\"_cl_\")");
            byte[] len = new byte[4];
            int r = inputStream.read(len);
            if (r < 4) {
                return new ArrayList();
            }
            int argc = ((len[3] & UByte.MAX_VALUE) << 24) | ((len[2] & UByte.MAX_VALUE) << 16) | ((len[1] & UByte.MAX_VALUE) << 8) | (len[0] & UByte.MAX_VALUE);
            ArrayList cmdline = new ArrayList(argc);
            for (int i = 0; i < argc; i++) {
                int r2 = inputStream.read(len);
                if (r2 < 4) {
                    return new ArrayList();
                }
                int strlen = ((len[3] & 255) << 24) | ((len[2] & 255) << 16) | ((len[1] & 255) << 8) | (len[0] & 255);
                if (strlen > 65535) {
                    return new ArrayList();
                }
                byte[] arg = new byte[strlen];
                int r3 = inputStream.read(arg);
                if (r3 == strlen) {
                    Charset UTF_8 = StandardCharsets.UTF_8;
                    Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                    cmdline.set(i, new String(arg, UTF_8));
                }
            }
            return cmdline;
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    private final String getInputFallbackMapping() {
        return this.xrMode.inputFallbackMapping;
    }

    public final boolean requestPermission(String name) {
        return PermissionsUtil.requestPermission(name, getActivity());
    }

    public final boolean requestPermissions() {
        return PermissionsUtil.requestManifestPermissions(getActivity());
    }

    public final String[] getGrantedPermissions() {
        return PermissionsUtil.getGrantedPermissions(getActivity());
    }

    private final String getCACertificates() {
        String cACertificates = GodotNetUtils.getCACertificates();
        Intrinsics.checkNotNullExpressionValue(cACertificates, "getCACertificates()");
        return cACertificates;
    }

    private final boolean obbIsCorrupted(String f, String mainPackMd5) {
        int numRead;
        int i;
        try {
            InputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[16384];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
            fis.close();
            byte[] messageDigest = complete.digest();
            StringBuilder hexString = new StringBuilder();
            Intrinsics.checkNotNullExpressionValue(messageDigest, "messageDigest");
            for (byte b : messageDigest) {
                String s = Integer.toHexString(b & UByte.MAX_VALUE);
                if (s.length() == 1) {
                    s = "0" + s;
                }
                hexString.append(s);
            }
            String md5str = hexString.toString();
            Intrinsics.checkNotNullExpressionValue(md5str, "hexString.toString()");
            return true ^ Intrinsics.areEqual(md5str, mainPackMd5);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private final void initInputDevices() {
        GodotRenderView godotRenderView = this.renderView;
        Intrinsics.checkNotNull(godotRenderView);
        godotRenderView.initInputDevices();
    }

    private final int createNewGodotInstance(String[] args) {
        GodotHost godotHost = this.primaryHost;
        if (godotHost != null) {
            return godotHost.onNewGodotInstanceRequested(args);
        }
        return 0;
    }

    private final void nativeBeginBenchmarkMeasure(String label) {
        BenchmarkUtils.beginBenchmarkMeasure(label);
    }

    private final void nativeEndBenchmarkMeasure(String label) {
        BenchmarkUtils.endBenchmarkMeasure(label);
    }

    private final void nativeDumpBenchmark(String benchmarkFile) {
        BenchmarkUtils.dumpBenchmark(this.fileAccessHandler, benchmarkFile);
    }
}