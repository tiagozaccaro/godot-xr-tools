package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.godotengine.godot.utils.ProcessPhoenix;

/* compiled from: GodotActivity.kt */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\b&\u0018\u0000 *2\u00020\u00012\u00020\u0002:\u0001*B\u0005¢\u0006\u0002\u0010\u0003J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0005H\u0014J\"\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u000eH\u0015J\b\u0010\u0017\u001a\u00020\fH\u0016J\u0012\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\fH\u0014J\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\u000eH\u0014J+\u0010\"\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'H\u0017¢\u0006\u0002\u0010(J\u0010\u0010)\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\"\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005@BX\u0084\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006+"}, d2 = {"Lorg/godotengine/godot/GodotActivity;", "Landroidx/fragment/app/FragmentActivity;", "Lorg/godotengine/godot/GodotHost;", "()V", "<set-?>", "Lorg/godotengine/godot/GodotFragment;", "godotFragment", "getGodotFragment", "()Lorg/godotengine/godot/GodotFragment;", "getActivity", "Landroid/app/Activity;", "handleStartIntent", "", "intent", "Landroid/content/Intent;", "newLaunch", "", "initGodotInstance", "onActivityResult", "requestCode", "", "resultCode", "data", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onGodotForceQuit", "instance", "Lorg/godotengine/godot/Godot;", "onGodotRestartRequested", "onNewIntent", "newIntent", "onRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "terminateGodotInstance", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class GodotActivity extends FragmentActivity implements GodotHost {
    private GodotFragment godotFragment;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = GodotActivity.class.getSimpleName();
    private static final String EXTRA_FORCE_QUIT = "force_quit_requested";
    private static final String EXTRA_NEW_LAUNCH = "new_launch_requested";

    protected static final String getEXTRA_FORCE_QUIT() {
        return Companion.getEXTRA_FORCE_QUIT();
    }

    protected static final String getEXTRA_NEW_LAUNCH() {
        return Companion.getEXTRA_NEW_LAUNCH();
    }

    /* compiled from: GodotActivity.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0004X\u0085D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u00020\u00048\u0004X\u0085D¢\u0006\u000e\n\u0000\u0012\u0004\b\t\u0010\u0002\u001a\u0004\b\n\u0010\u0007R\u0016\u0010\u000b\u001a\n \f*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/godotengine/godot/GodotActivity$Companion;", "", "()V", "EXTRA_FORCE_QUIT", "", "getEXTRA_FORCE_QUIT$annotations", "getEXTRA_FORCE_QUIT", "()Ljava/lang/String;", "EXTRA_NEW_LAUNCH", "getEXTRA_NEW_LAUNCH$annotations", "getEXTRA_NEW_LAUNCH", "TAG", "kotlin.jvm.PlatformType", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        protected static /* synthetic */ void getEXTRA_FORCE_QUIT$annotations() {
        }

        @JvmStatic
        protected static /* synthetic */ void getEXTRA_NEW_LAUNCH$annotations() {
        }

        private Companion() {
        }

        protected final String getEXTRA_FORCE_QUIT() {
            return GodotActivity.EXTRA_FORCE_QUIT;
        }

        protected final String getEXTRA_NEW_LAUNCH() {
            return GodotActivity.EXTRA_NEW_LAUNCH;
        }
    }

    protected final GodotFragment getGodotFragment() {
        return this.godotFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.godot_app_layout);
        Intent intent = getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "intent");
        handleStartIntent(intent, true);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.godot_fragment_container);
        if (currentFragment instanceof GodotFragment) {
            Log.v(TAG, "Reusing existing Godot fragment instance.");
            this.godotFragment = (GodotFragment) currentFragment;
            return;
        }
        Log.v(TAG, "Creating new Godot fragment instance.");
        this.godotFragment = initGodotInstance();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        int i = R.id.godot_fragment_container;
        GodotFragment godotFragment = this.godotFragment;
        Intrinsics.checkNotNull(godotFragment);
        beginTransaction.replace(i, godotFragment).setPrimaryNavigationFragment(this.godotFragment).commitNowAllowingStateLoss();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Log.v(TAG, "Destroying Godot app...");
        super.onDestroy();
        GodotFragment godotFragment = this.godotFragment;
        if (godotFragment != null) {
            Intrinsics.checkNotNull(godotFragment);
            Godot godot = godotFragment.getGodot();
            Intrinsics.checkNotNullExpressionValue(godot, "godotFragment!!.godot");
            terminateGodotInstance(godot);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onGodotForceQuit$lambda-0  reason: not valid java name */
    public static final void m1511onGodotForceQuit$lambda0(GodotActivity this$0, Godot instance) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(instance, "$instance");
        this$0.terminateGodotInstance(instance);
    }

    @Override // org.godotengine.godot.GodotHost
    public void onGodotForceQuit(final Godot instance) {
        Intrinsics.checkNotNullParameter(instance, "instance");
        runOnUiThread(new Runnable() { // from class: org.godotengine.godot.GodotActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GodotActivity.m1511onGodotForceQuit$lambda0(GodotActivity.this, instance);
            }
        });
    }

    private final void terminateGodotInstance(Godot instance) {
        GodotFragment godotFragment = this.godotFragment;
        if (godotFragment != null) {
            Intrinsics.checkNotNull(godotFragment);
            if (instance == godotFragment.getGodot()) {
                Log.v(TAG, "Force quitting Godot instance");
                ProcessPhoenix.forceQuit(this);
            }
        }
    }

    @Override // org.godotengine.godot.GodotHost
    public void onGodotRestartRequested(final Godot instance) {
        Intrinsics.checkNotNullParameter(instance, "instance");
        runOnUiThread(new Runnable() { // from class: org.godotengine.godot.GodotActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GodotActivity.m1512onGodotRestartRequested$lambda1(GodotActivity.this, instance);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onGodotRestartRequested$lambda-1  reason: not valid java name */
    public static final void m1512onGodotRestartRequested$lambda1(GodotActivity this$0, Godot instance) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(instance, "$instance");
        GodotFragment godotFragment = this$0.godotFragment;
        if (godotFragment != null) {
            Intrinsics.checkNotNull(godotFragment);
            if (instance == godotFragment.getGodot()) {
                Log.v(TAG, "Restarting Godot instance...");
                ProcessPhoenix.triggerRebirth(this$0);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onNewIntent(Intent newIntent) {
        Intrinsics.checkNotNullParameter(newIntent, "newIntent");
        super.onNewIntent(newIntent);
        setIntent(newIntent);
        handleStartIntent(newIntent, false);
        GodotFragment godotFragment = this.godotFragment;
        if (godotFragment != null) {
            godotFragment.onNewIntent(newIntent);
        }
    }

    private final void handleStartIntent(Intent intent, boolean newLaunch) {
        boolean forceQuitRequested = intent.getBooleanExtra(EXTRA_FORCE_QUIT, false);
        if (forceQuitRequested) {
            Log.d(TAG, "Force quit requested, terminating..");
            ProcessPhoenix.forceQuit(this);
        } else if (!newLaunch) {
            String str = EXTRA_NEW_LAUNCH;
            boolean newLaunchRequested = intent.getBooleanExtra(str, false);
            if (newLaunchRequested) {
                Log.d(TAG, "New launch requested, restarting..");
                Intent restartIntent = new Intent(intent).putExtra(str, false);
                Intrinsics.checkNotNullExpressionValue(restartIntent, "Intent(intent).putExtra(EXTRA_NEW_LAUNCH, false)");
                ProcessPhoenix.triggerRebirth(this, restartIntent);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GodotFragment godotFragment = this.godotFragment;
        if (godotFragment != null) {
            godotFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GodotFragment godotFragment = this.godotFragment;
        if (godotFragment != null) {
            godotFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Unit unit;
        GodotFragment godotFragment = this.godotFragment;
        if (godotFragment != null) {
            godotFragment.onBackPressed();
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            super.onBackPressed();
        }
    }

    @Override // org.godotengine.godot.GodotHost
    public Activity getActivity() {
        return this;
    }

    protected GodotFragment initGodotInstance() {
        return new GodotFragment();
    }
}