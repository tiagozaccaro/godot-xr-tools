package org.godotengine.godot.vulkan;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VkSurfaceView.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0010\u0018\u0000 \u001f2\u00020\u00012\u00020\u0002:\u0001\u001fB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u0007J(\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001aH\u0016J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006 "}, d2 = {"Lorg/godotengine/godot/vulkan/VkSurfaceView;", "Landroid/view/SurfaceView;", "Landroid/view/SurfaceHolder$Callback;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "renderer", "Lorg/godotengine/godot/vulkan/VkRenderer;", "vkThread", "Lorg/godotengine/godot/vulkan/VkThread;", "getVkThread", "()Lorg/godotengine/godot/vulkan/VkThread;", "vkThread$delegate", "Lkotlin/Lazy;", "onDestroy", "", "onPause", "onResume", "queueOnVkThread", "runnable", "Ljava/lang/Runnable;", "startRenderer", "surfaceChanged", "holder", "Landroid/view/SurfaceHolder;", "format", "", "width", "height", "surfaceCreated", "surfaceDestroyed", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public class VkSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final Companion Companion = new Companion(null);
    private VkRenderer renderer;
    private final Lazy vkThread$delegate;

    /* compiled from: VkSurfaceView.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001¨\u0006\b"}, d2 = {"Lorg/godotengine/godot/vulkan/VkSurfaceView$Companion;", "", "()V", "checkState", "", "expression", "", "errorMessage", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void checkState(boolean expression, Object errorMessage) {
            Intrinsics.checkNotNullParameter(errorMessage, "errorMessage");
            if (!expression) {
                throw new IllegalStateException(errorMessage.toString().toString());
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VkSurfaceView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.vkThread$delegate = LazyKt.lazy(new Function0<VkThread>() { // from class: org.godotengine.godot.vulkan.VkSurfaceView$vkThread$2
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VkThread invoke() {
                VkRenderer vkRenderer;
                VkSurfaceView vkSurfaceView = VkSurfaceView.this;
                vkRenderer = vkSurfaceView.renderer;
                if (vkRenderer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("renderer");
                    vkRenderer = null;
                }
                return new VkThread(vkSurfaceView, vkRenderer);
            }
        });
        setClickable(true);
        getHolder().addCallback(this);
    }

    private final VkThread getVkThread() {
        return (VkThread) this.vkThread$delegate.getValue();
    }

    public final void startRenderer(VkRenderer renderer) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Companion.checkState(this.renderer == null, "startRenderer must only be invoked once");
        this.renderer = renderer;
        getVkThread().start();
    }

    public final void queueOnVkThread(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        getVkThread().queueEvent(runnable);
    }

    public void onResume() {
        getVkThread().onResume();
    }

    public void onPause() {
        getVkThread().onPause();
    }

    public final void onDestroy() {
        getVkThread().blockingExit();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        getVkThread().onSurfaceChanged(width, height);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        getVkThread().onSurfaceDestroyed();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        getVkThread().onSurfaceCreated();
    }
}