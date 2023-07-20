package org.godotengine.godot;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SurfaceView;
import java.io.InputStream;
import org.godotengine.godot.gl.GLSurfaceView;
import org.godotengine.godot.gl.GodotRenderer;
import org.godotengine.godot.input.GodotInputHandler;
import org.godotengine.godot.xr.XRMode;
import org.godotengine.godot.xr.ovr.OvrConfigChooser;
import org.godotengine.godot.xr.ovr.OvrContextFactory;
import org.godotengine.godot.xr.ovr.OvrWindowSurfaceFactory;
import org.godotengine.godot.xr.regular.RegularConfigChooser;
import org.godotengine.godot.xr.regular.RegularContextFactory;
import org.godotengine.godot.xr.regular.RegularFallbackConfigChooser;

/* loaded from: classes.dex */
public class GodotGLRenderView extends GLSurfaceView implements GodotRenderView {
    private final SparseArray<PointerIcon> customPointerIcons;
    private final Godot godot;
    private final GodotRenderer godotRenderer;
    private final GodotHost host;
    private final GodotInputHandler inputHandler;

    public GodotGLRenderView(GodotHost host, Godot godot, XRMode xrMode, boolean useDebugOpengl) {
        super(host.getActivity());
        this.customPointerIcons = new SparseArray<>();
        this.host = host;
        this.godot = godot;
        this.inputHandler = new GodotInputHandler(this);
        this.godotRenderer = new GodotRenderer();
        setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
        init(xrMode, false, useDebugOpengl);
    }

    @Override // org.godotengine.godot.GodotRenderView
    public SurfaceView getView() {
        return this;
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void initInputDevices() {
        this.inputHandler.initInputDevices();
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void queueOnRenderThread(Runnable event) {
        queueEvent(event);
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void onActivityPaused() {
        onPause();
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void onActivityResumed() {
        onResume();
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void onBackPressed() {
        this.godot.onBackPressed(this.host);
    }

    @Override // org.godotengine.godot.GodotRenderView
    public GodotInputHandler getInputHandler() {
        return this.inputHandler;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return this.inputHandler.onTouchEvent(event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.inputHandler.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.inputHandler.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        return this.inputHandler.onGenericMotionEvent(event) || super.onGenericMotionEvent(event);
    }

    @Override // android.view.View
    public boolean onCapturedPointerEvent(MotionEvent event) {
        return this.inputHandler.onGenericMotionEvent(event);
    }

    @Override // android.view.View
    public void onPointerCaptureChange(boolean hasCapture) {
        super.onPointerCaptureChange(hasCapture);
        this.inputHandler.onPointerCaptureChange(hasCapture);
    }

    @Override // android.view.View
    public void requestPointerCapture() {
        if (canCapturePointer()) {
            super.requestPointerCapture();
            this.inputHandler.onPointerCaptureChange(true);
        }
    }

    @Override // android.view.View
    public void releasePointerCapture() {
        super.releasePointerCapture();
        this.inputHandler.onPointerCaptureChange(false);
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void configurePointerIcon(int pointerType, String imagePath, float hotSpotX, float hotSpotY) {
        Bitmap bitmap = null;
        try {
            if (!TextUtils.isEmpty(imagePath)) {
                if (this.godot.getDirectoryAccessHandler().filesystemFileExists(imagePath)) {
                    bitmap = BitmapFactory.decodeFile(imagePath);
                } else if (this.godot.getDirectoryAccessHandler().assetsFileExists(imagePath)) {
                    AssetManager am = getContext().getAssets();
                    InputStream imageInputStream = am.open(imagePath);
                    bitmap = BitmapFactory.decodeStream(imageInputStream);
                }
            }
            PointerIcon customPointerIcon = PointerIcon.create(bitmap, hotSpotX, hotSpotY);
            this.customPointerIcons.put(pointerType, customPointerIcon);
        } catch (Exception e) {
            this.customPointerIcons.delete(pointerType);
        }
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void setPointerIcon(int pointerType) {
        PointerIcon pointerIcon = this.customPointerIcons.get(pointerType);
        if (pointerIcon == null) {
            pointerIcon = PointerIcon.getSystemIcon(getContext(), pointerType);
        }
        setPointerIcon(pointerIcon);
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent me, int pointerIndex) {
        return getPointerIcon();
    }

    private void init(XRMode xrMode, boolean translucent, boolean useDebugOpengl) {
        setPreserveEGLContextOnPause(true);
        setFocusableInTouchMode(true);
        switch (AnonymousClass1.$SwitchMap$org$godotengine$godot$xr$XRMode[xrMode.ordinal()]) {
            case 1:
                setEGLConfigChooser(new OvrConfigChooser());
                setEGLContextFactory(new OvrContextFactory());
                setEGLWindowSurfaceFactory(new OvrWindowSurfaceFactory());
                return;
            default:
                if (translucent) {
                    getHolder().setFormat(-3);
                }
                setEGLContextFactory(new RegularContextFactory(useDebugOpengl));
                setEGLConfigChooser(new RegularFallbackConfigChooser(8, 8, 8, 8, 24, 0, new RegularConfigChooser(8, 8, 8, 8, 16, 0)));
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.godotengine.godot.GodotGLRenderView$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$godotengine$godot$xr$XRMode;

        static {
            int[] iArr = new int[XRMode.values().length];
            $SwitchMap$org$godotengine$godot$xr$XRMode = iArr;
            try {
                iArr[XRMode.OPENXR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$godotengine$godot$xr$XRMode[XRMode.REGULAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void startRenderer() {
        setRenderer(this.godotRenderer);
    }

    @Override // org.godotengine.godot.gl.GLSurfaceView
    public void onResume() {
        super.onResume();
        queueEvent(new Runnable() { // from class: org.godotengine.godot.GodotGLRenderView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GodotGLRenderView.this.m1514lambda$onResume$0$orggodotenginegodotGodotGLRenderView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onResume$0$org-godotengine-godot-GodotGLRenderView  reason: not valid java name */
    public /* synthetic */ void m1514lambda$onResume$0$orggodotenginegodotGodotGLRenderView() {
        this.godotRenderer.onActivityResumed();
        GodotLib.focusin();
    }

    @Override // org.godotengine.godot.gl.GLSurfaceView
    public void onPause() {
        super.onPause();
        queueEvent(new Runnable() { // from class: org.godotengine.godot.GodotGLRenderView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GodotGLRenderView.this.m1513lambda$onPause$1$orggodotenginegodotGodotGLRenderView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onPause$1$org-godotengine-godot-GodotGLRenderView  reason: not valid java name */
    public /* synthetic */ void m1513lambda$onPause$1$orggodotenginegodotGodotGLRenderView() {
        GodotLib.focusout();
        this.godotRenderer.onActivityPaused();
    }
}