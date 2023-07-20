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
import org.godotengine.godot.input.GodotInputHandler;
import org.godotengine.godot.vulkan.VkRenderer;
import org.godotengine.godot.vulkan.VkSurfaceView;

/* loaded from: classes.dex */
public class GodotVulkanRenderView extends VkSurfaceView implements GodotRenderView {
    private final SparseArray<PointerIcon> customPointerIcons;
    private final Godot godot;
    private final GodotHost host;
    private final GodotInputHandler mInputHandler;
    private final VkRenderer mRenderer;

    public GodotVulkanRenderView(GodotHost host, Godot godot) {
        super(host.getActivity());
        this.customPointerIcons = new SparseArray<>();
        this.host = host;
        this.godot = godot;
        this.mInputHandler = new GodotInputHandler(this);
        this.mRenderer = new VkRenderer();
        setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
        setFocusableInTouchMode(true);
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void startRenderer() {
        startRenderer(this.mRenderer);
    }

    @Override // org.godotengine.godot.GodotRenderView
    public SurfaceView getView() {
        return this;
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void initInputDevices() {
        this.mInputHandler.initInputDevices();
    }

    @Override // org.godotengine.godot.GodotRenderView
    public void queueOnRenderThread(Runnable event) {
        queueOnVkThread(event);
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
        return this.mInputHandler;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return this.mInputHandler.onTouchEvent(event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mInputHandler.onKeyUp(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mInputHandler.onKeyDown(keyCode, event);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        return this.mInputHandler.onGenericMotionEvent(event);
    }

    @Override // android.view.View
    public boolean onCapturedPointerEvent(MotionEvent event) {
        return this.mInputHandler.onGenericMotionEvent(event);
    }

    @Override // android.view.View
    public void requestPointerCapture() {
        if (canCapturePointer()) {
            super.requestPointerCapture();
            this.mInputHandler.onPointerCaptureChange(true);
        }
    }

    @Override // android.view.View
    public void releasePointerCapture() {
        super.releasePointerCapture();
        this.mInputHandler.onPointerCaptureChange(false);
    }

    @Override // android.view.View
    public void onPointerCaptureChange(boolean hasCapture) {
        super.onPointerCaptureChange(hasCapture);
        this.mInputHandler.onPointerCaptureChange(hasCapture);
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

    @Override // org.godotengine.godot.vulkan.VkSurfaceView
    public void onResume() {
        super.onResume();
        queueOnVkThread(new Runnable() { // from class: org.godotengine.godot.GodotVulkanRenderView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GodotVulkanRenderView.this.m1516lambda$onResume$0$orggodotenginegodotGodotVulkanRenderView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onResume$0$org-godotengine-godot-GodotVulkanRenderView  reason: not valid java name */
    public /* synthetic */ void m1516lambda$onResume$0$orggodotenginegodotGodotVulkanRenderView() {
        this.mRenderer.onVkResume();
        GodotLib.focusin();
    }

    @Override // org.godotengine.godot.vulkan.VkSurfaceView
    public void onPause() {
        super.onPause();
        queueOnVkThread(new Runnable() { // from class: org.godotengine.godot.GodotVulkanRenderView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GodotVulkanRenderView.this.m1515lambda$onPause$1$orggodotenginegodotGodotVulkanRenderView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onPause$1$org-godotengine-godot-GodotVulkanRenderView  reason: not valid java name */
    public /* synthetic */ void m1515lambda$onPause$1$orggodotenginegodotGodotVulkanRenderView() {
        GodotLib.focusout();
        this.mRenderer.onVkPause();
    }
}