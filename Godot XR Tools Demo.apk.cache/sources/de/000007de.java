package org.godotengine.godot.input;

import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.godotengine.godot.GodotLib;

/* compiled from: GodotGestureHandler.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0000\u0018\u0000 (2\u00020\u00012\u00020\u0002:\u0001(B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0005J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010 \u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J(\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0016J\u0010\u0010'\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/godotengine/godot/input/GodotGestureHandler;", "Landroid/view/GestureDetector$SimpleOnGestureListener;", "Landroid/view/ScaleGestureDetector$OnScaleGestureListener;", "()V", "contextClickInProgress", "", "dragInProgress", "nextDownIsDoubleTap", "panningAndScalingEnabled", "getPanningAndScalingEnabled", "()Z", "setPanningAndScalingEnabled", "(Z)V", "pointerCaptureInProgress", "scaleInProgress", "contextClickRouter", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "onActionMove", "onActionUp", "onDoubleTap", "onDoubleTapEvent", "onDown", "onLongPress", "onMotionEvent", "onPointerCaptureChange", "hasCapture", "onScale", "detector", "Landroid/view/ScaleGestureDetector;", "onScaleBegin", "onScaleEnd", "onScroll", "originEvent", "terminusEvent", "distanceX", "", "distanceY", "onSingleTapUp", "Companion", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class GodotGestureHandler extends GestureDetector.SimpleOnGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = GodotGestureHandler.class.getSimpleName();
    private boolean contextClickInProgress;
    private boolean dragInProgress;
    private boolean nextDownIsDoubleTap;
    private boolean panningAndScalingEnabled;
    private boolean pointerCaptureInProgress;
    private boolean scaleInProgress;

    /* compiled from: GodotGestureHandler.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/godotengine/godot/input/GodotGestureHandler$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean getPanningAndScalingEnabled() {
        return this.panningAndScalingEnabled;
    }

    public final void setPanningAndScalingEnabled(boolean z) {
        this.panningAndScalingEnabled = z;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GodotInputHandler.handleMotionEvent(event.getSource(), 0, event.getButtonState(), event.getX(), event.getY(), this.nextDownIsDoubleTap);
        this.nextDownIsDoubleTap = false;
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GodotInputHandler.handleMotionEvent(event);
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        contextClickRouter(event);
    }

    private final void contextClickRouter(MotionEvent event) {
        if (this.scaleInProgress || this.nextDownIsDoubleTap) {
            return;
        }
        GodotInputHandler.handleMotionEvent(event.getSource(), 3, event.getButtonState(), event.getX(), event.getY());
        GodotInputHandler.handleMouseEvent(0, 2, event.getX(), event.getY());
        this.contextClickInProgress = true;
    }

    public final void onPointerCaptureChange(boolean hasCapture) {
        if (this.pointerCaptureInProgress == hasCapture) {
            return;
        }
        if (!hasCapture) {
            GodotInputHandler.handleMouseEvent(1, 0, 0.0f, 0.0f, 0.0f, 0.0f, false, true);
        }
        this.pointerCaptureInProgress = hasCapture;
    }

    public final boolean onMotionEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        switch (event.getActionMasked()) {
            case 1:
            case 3:
            case 12:
                return onActionUp(event);
            case 2:
                return onActionMove(event);
            default:
                return false;
        }
    }

    private final boolean onActionUp(MotionEvent event) {
        boolean sourceMouseRelative;
        if (event.getActionMasked() == 3 && this.pointerCaptureInProgress) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            sourceMouseRelative = event.isFromSource(131076);
        } else {
            sourceMouseRelative = false;
        }
        if (this.pointerCaptureInProgress || this.dragInProgress || this.contextClickInProgress) {
            if (this.contextClickInProgress || GodotInputHandler.isMouseEvent(event)) {
                GodotInputHandler.handleMouseEvent(1, event.getButtonState(), event.getX(), event.getY(), 0.0f, 0.0f, false, sourceMouseRelative);
            } else {
                GodotInputHandler.handleTouchEvent(event);
            }
            this.pointerCaptureInProgress = false;
            this.dragInProgress = false;
            this.contextClickInProgress = false;
            return true;
        }
        return false;
    }

    private final boolean onActionMove(MotionEvent event) {
        boolean sourceMouseRelative;
        if (this.contextClickInProgress) {
            if (Build.VERSION.SDK_INT >= 26) {
                sourceMouseRelative = event.isFromSource(131076);
            } else {
                sourceMouseRelative = false;
            }
            GodotInputHandler.handleMouseEvent(event.getActionMasked(), 2, event.getX(), event.getY(), 0.0f, 0.0f, false, sourceMouseRelative);
            return true;
        }
        return false;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getActionMasked() == 1) {
            this.nextDownIsDoubleTap = false;
            GodotInputHandler.handleMotionEvent(event);
        } else if (event.getActionMasked() == 2 && !this.panningAndScalingEnabled) {
            GodotInputHandler.handleMotionEvent(event);
        }
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.nextDownIsDoubleTap = true;
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent originEvent, MotionEvent terminusEvent, float distanceX, float distanceY) {
        Intrinsics.checkNotNullParameter(originEvent, "originEvent");
        Intrinsics.checkNotNullParameter(terminusEvent, "terminusEvent");
        if (this.scaleInProgress && this.dragInProgress) {
            GodotInputHandler.handleMotionEvent(originEvent.getSource(), 3, originEvent.getButtonState(), originEvent.getX(), originEvent.getY());
            this.dragInProgress = false;
        }
        float x = terminusEvent.getX();
        float y = terminusEvent.getY();
        if (terminusEvent.getPointerCount() >= 2 && this.panningAndScalingEnabled && !this.pointerCaptureInProgress && !this.dragInProgress) {
            GodotLib.pan(x, y, distanceX / 5.0f, distanceY / 5.0f);
        } else if (!this.scaleInProgress) {
            this.dragInProgress = true;
            GodotInputHandler.handleMotionEvent(terminusEvent);
        }
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector detector) {
        Intrinsics.checkNotNullParameter(detector, "detector");
        if (!this.panningAndScalingEnabled || this.pointerCaptureInProgress || this.dragInProgress) {
            return false;
        }
        if (detector.getScaleFactor() >= 0.8f) {
            if (!(detector.getScaleFactor() == 1.0f) && detector.getScaleFactor() <= 1.2f) {
                GodotLib.magnify(detector.getFocusX(), detector.getFocusY(), detector.getScaleFactor());
            }
        }
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Intrinsics.checkNotNullParameter(detector, "detector");
        if (!this.panningAndScalingEnabled || this.pointerCaptureInProgress || this.dragInProgress) {
            return false;
        }
        this.scaleInProgress = true;
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector detector) {
        Intrinsics.checkNotNullParameter(detector, "detector");
        this.scaleInProgress = false;
    }
}