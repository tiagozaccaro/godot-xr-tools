package org.godotengine.godot;

import android.view.SurfaceView;
import org.godotengine.godot.input.GodotInputHandler;

/* loaded from: classes.dex */
public interface GodotRenderView {
    void configurePointerIcon(int i, String str, float f, float f2);

    GodotInputHandler getInputHandler();

    SurfaceView getView();

    void initInputDevices();

    void onActivityPaused();

    void onActivityResumed();

    void onBackPressed();

    void queueOnRenderThread(Runnable runnable);

    void setPointerIcon(int i);

    void startRenderer();

    default boolean canCapturePointer() {
        return getInputHandler().canCapturePointer();
    }
}