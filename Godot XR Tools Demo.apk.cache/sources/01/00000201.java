package androidx.core.view;

import android.content.ClipData;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.collection.SimpleArrayMap;
import androidx.core.R;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    @Deprecated
    public static final int LAYER_TYPE_HARDWARE = 2;
    @Deprecated
    public static final int LAYER_TYPE_NONE = 0;
    @Deprecated
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    @Deprecated
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    @Deprecated
    public static final int MEASURED_SIZE_MASK = 16777215;
    @Deprecated
    public static final int MEASURED_STATE_MASK = -16777216;
    @Deprecated
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    @Deprecated
    public static final int OVER_SCROLL_ALWAYS = 0;
    @Deprecated
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    @Deprecated
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    private static final String TAG = "ViewCompat";
    public static final int TYPE_NON_TOUCH = 1;
    public static final int TYPE_TOUCH = 0;
    private static Field sAccessibilityDelegateField;
    private static Method sChildrenDrawingOrderMethod;
    private static Method sDispatchFinishTemporaryDetach;
    private static Method sDispatchStartTemporaryDetach;
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;
    private static Field sMinWidthField;
    private static boolean sMinWidthFieldFetched;
    private static boolean sTempDetachBound;
    private static ThreadLocal<Rect> sThreadLocalRect;
    private static WeakHashMap<View, String> sTransitionNameMap;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private static WeakHashMap<View, ViewPropertyAnimatorCompat> sViewPropertyAnimatorMap = null;
    private static boolean sAccessibilityDelegateCheckFailed = false;
    private static final int[] ACCESSIBILITY_ACTIONS_RESOURCE_IDS = {R.id.accessibility_custom_action_0, R.id.accessibility_custom_action_1, R.id.accessibility_custom_action_2, R.id.accessibility_custom_action_3, R.id.accessibility_custom_action_4, R.id.accessibility_custom_action_5, R.id.accessibility_custom_action_6, R.id.accessibility_custom_action_7, R.id.accessibility_custom_action_8, R.id.accessibility_custom_action_9, R.id.accessibility_custom_action_10, R.id.accessibility_custom_action_11, R.id.accessibility_custom_action_12, R.id.accessibility_custom_action_13, R.id.accessibility_custom_action_14, R.id.accessibility_custom_action_15, R.id.accessibility_custom_action_16, R.id.accessibility_custom_action_17, R.id.accessibility_custom_action_18, R.id.accessibility_custom_action_19, R.id.accessibility_custom_action_20, R.id.accessibility_custom_action_21, R.id.accessibility_custom_action_22, R.id.accessibility_custom_action_23, R.id.accessibility_custom_action_24, R.id.accessibility_custom_action_25, R.id.accessibility_custom_action_26, R.id.accessibility_custom_action_27, R.id.accessibility_custom_action_28, R.id.accessibility_custom_action_29, R.id.accessibility_custom_action_30, R.id.accessibility_custom_action_31};
    private static AccessibilityPaneVisibilityManager sAccessibilityPaneVisibilityManager = new AccessibilityPaneVisibilityManager();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FocusDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FocusRealDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface FocusRelativeDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface NestedScrollType {
    }

    /* loaded from: classes.dex */
    public interface OnUnhandledKeyEventListenerCompat {
        boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ScrollAxis {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ScrollIndicators {
    }

    private static Rect getEmptyTempRect() {
        if (sThreadLocalRect == null) {
            sThreadLocalRect = new ThreadLocal<>();
        }
        Rect rect = sThreadLocalRect.get();
        if (rect == null) {
            rect = new Rect();
            sThreadLocalRect.set(rect);
        }
        rect.setEmpty();
        return rect;
    }

    @Deprecated
    public static boolean canScrollHorizontally(View view, int direction) {
        return view.canScrollHorizontally(direction);
    }

    @Deprecated
    public static boolean canScrollVertically(View view, int direction) {
        return view.canScrollVertically(direction);
    }

    @Deprecated
    public static int getOverScrollMode(View v) {
        return v.getOverScrollMode();
    }

    @Deprecated
    public static void setOverScrollMode(View v, int overScrollMode) {
        v.setOverScrollMode(overScrollMode);
    }

    @Deprecated
    public static void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
        v.onPopulateAccessibilityEvent(event);
    }

    @Deprecated
    public static void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
        v.onInitializeAccessibilityEvent(event);
    }

    public static void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
        v.onInitializeAccessibilityNodeInfo(info.unwrap());
    }

    public static void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
        if (delegate == null && (getAccessibilityDelegateInternal(v) instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter)) {
            delegate = new AccessibilityDelegateCompat();
        }
        v.setAccessibilityDelegate(delegate == null ? null : delegate.getBridge());
    }

    public static void setAutofillHints(View v, String... autofillHints) {
        if (Build.VERSION.SDK_INT >= 26) {
            v.setAutofillHints(autofillHints);
        }
    }

    public static int getImportantForAutofill(View v) {
        if (Build.VERSION.SDK_INT >= 26) {
            return v.getImportantForAutofill();
        }
        return 0;
    }

    public static void setImportantForAutofill(View v, int mode) {
        if (Build.VERSION.SDK_INT >= 26) {
            v.setImportantForAutofill(mode);
        }
    }

    public static boolean isImportantForAutofill(View v) {
        if (Build.VERSION.SDK_INT >= 26) {
            return v.isImportantForAutofill();
        }
        return true;
    }

    public static boolean hasAccessibilityDelegate(View view) {
        return getAccessibilityDelegateInternal(view) != null;
    }

    public static AccessibilityDelegateCompat getAccessibilityDelegate(View view) {
        View.AccessibilityDelegate delegate = getAccessibilityDelegateInternal(view);
        if (delegate == null) {
            return null;
        }
        if (delegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter) {
            return ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) delegate).mCompat;
        }
        return new AccessibilityDelegateCompat(delegate);
    }

    static AccessibilityDelegateCompat getOrCreateAccessibilityDelegateCompat(View v) {
        AccessibilityDelegateCompat delegateCompat = getAccessibilityDelegate(v);
        if (delegateCompat == null) {
            delegateCompat = new AccessibilityDelegateCompat();
        }
        setAccessibilityDelegate(v, delegateCompat);
        return delegateCompat;
    }

    private static View.AccessibilityDelegate getAccessibilityDelegateInternal(View v) {
        if (Build.VERSION.SDK_INT >= 29) {
            return v.getAccessibilityDelegate();
        }
        return getAccessibilityDelegateThroughReflection(v);
    }

    private static View.AccessibilityDelegate getAccessibilityDelegateThroughReflection(View v) {
        if (sAccessibilityDelegateCheckFailed) {
            return null;
        }
        if (sAccessibilityDelegateField == null) {
            try {
                Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate");
                sAccessibilityDelegateField = declaredField;
                declaredField.setAccessible(true);
            } catch (Throwable th) {
                sAccessibilityDelegateCheckFailed = true;
                return null;
            }
        }
        try {
            Object o = sAccessibilityDelegateField.get(v);
            if (o instanceof View.AccessibilityDelegate) {
                return (View.AccessibilityDelegate) o;
            }
            return null;
        } catch (Throwable th2) {
            sAccessibilityDelegateCheckFailed = true;
            return null;
        }
    }

    public static boolean hasTransientState(View view) {
        return view.hasTransientState();
    }

    public static void setHasTransientState(View view, boolean hasTransientState) {
        view.setHasTransientState(hasTransientState);
    }

    public static void postInvalidateOnAnimation(View view) {
        view.postInvalidateOnAnimation();
    }

    public static void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
        view.postInvalidateOnAnimation(left, top, right, bottom);
    }

    public static void postOnAnimation(View view, Runnable action) {
        view.postOnAnimation(action);
    }

    public static void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
        view.postOnAnimationDelayed(action, delayMillis);
    }

    public static int getImportantForAccessibility(View view) {
        return view.getImportantForAccessibility();
    }

    public static void setImportantForAccessibility(View view, int mode) {
        view.setImportantForAccessibility(mode);
    }

    public static boolean isImportantForAccessibility(View view) {
        return view.isImportantForAccessibility();
    }

    public static boolean performAccessibilityAction(View view, int action, Bundle arguments) {
        return view.performAccessibilityAction(action, arguments);
    }

    public static int addAccessibilityAction(View view, CharSequence label, AccessibilityViewCommand command) {
        int actionId = getAvailableActionIdFromResources(view);
        if (actionId != -1) {
            AccessibilityNodeInfoCompat.AccessibilityActionCompat action = new AccessibilityNodeInfoCompat.AccessibilityActionCompat(actionId, label, command);
            addAccessibilityAction(view, action);
        }
        return actionId;
    }

    private static int getAvailableActionIdFromResources(View view) {
        int result = -1;
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = getActionList(view);
        int i = 0;
        while (true) {
            int[] iArr = ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
            if (i >= iArr.length || result != -1) {
                break;
            }
            int id = iArr[i];
            boolean idAvailable = true;
            for (int j = 0; j < actions.size(); j++) {
                idAvailable &= actions.get(j).getId() != id;
            }
            if (idAvailable) {
                result = id;
            }
            i++;
        }
        return result;
    }

    public static void replaceAccessibilityAction(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat replacedAction, CharSequence label, AccessibilityViewCommand command) {
        if (command == null && label == null) {
            removeAccessibilityAction(view, replacedAction.getId());
        } else {
            addAccessibilityAction(view, replacedAction.createReplacementAction(label, command));
        }
    }

    private static void addAccessibilityAction(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat action) {
        getOrCreateAccessibilityDelegateCompat(view);
        removeActionWithId(action.getId(), view);
        getActionList(view).add(action);
        notifyViewAccessibilityStateChangedIfNeeded(view, 0);
    }

    public static void removeAccessibilityAction(View view, int actionId) {
        removeActionWithId(actionId, view);
        notifyViewAccessibilityStateChangedIfNeeded(view, 0);
    }

    private static void removeActionWithId(int actionId, View view) {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = getActionList(view);
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getId() == actionId) {
                actions.remove(i);
                return;
            }
        }
    }

    private static List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList(View view) {
        ArrayList<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = (ArrayList) view.getTag(R.id.tag_accessibility_actions);
        if (actions == null) {
            ArrayList<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions2 = new ArrayList<>();
            view.setTag(R.id.tag_accessibility_actions, actions2);
            return actions2;
        }
        return actions;
    }

    public static void enableAccessibleClickableSpanSupport(View view) {
        getOrCreateAccessibilityDelegateCompat(view);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        AccessibilityNodeProvider provider = view.getAccessibilityNodeProvider();
        if (provider != null) {
            return new AccessibilityNodeProviderCompat(provider);
        }
        return null;
    }

    @Deprecated
    public static float getAlpha(View view) {
        return view.getAlpha();
    }

    @Deprecated
    public static void setLayerType(View view, int layerType, Paint paint) {
        view.setLayerType(layerType, paint);
    }

    @Deprecated
    public static int getLayerType(View view) {
        return view.getLayerType();
    }

    public static int getLabelFor(View view) {
        return view.getLabelFor();
    }

    public static void setLabelFor(View view, int labeledId) {
        view.setLabelFor(labeledId);
    }

    public static void setLayerPaint(View view, Paint paint) {
        view.setLayerPaint(paint);
    }

    public static int getLayoutDirection(View view) {
        return view.getLayoutDirection();
    }

    public static void setLayoutDirection(View view, int layoutDirection) {
        view.setLayoutDirection(layoutDirection);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return view.getParentForAccessibility();
    }

    public static <T extends View> T requireViewById(View view, int id) {
        if (Build.VERSION.SDK_INT >= 28) {
            return (T) view.requireViewById(id);
        }
        T targetView = (T) view.findViewById(id);
        if (targetView == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this View");
        }
        return targetView;
    }

    @Deprecated
    public static boolean isOpaque(View view) {
        return view.isOpaque();
    }

    @Deprecated
    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        return View.resolveSizeAndState(size, measureSpec, childMeasuredState);
    }

    @Deprecated
    public static int getMeasuredWidthAndState(View view) {
        return view.getMeasuredWidthAndState();
    }

    @Deprecated
    public static int getMeasuredHeightAndState(View view) {
        return view.getMeasuredHeightAndState();
    }

    @Deprecated
    public static int getMeasuredState(View view) {
        return view.getMeasuredState();
    }

    @Deprecated
    public static int combineMeasuredStates(int curState, int newState) {
        return View.combineMeasuredStates(curState, newState);
    }

    public static int getAccessibilityLiveRegion(View view) {
        return view.getAccessibilityLiveRegion();
    }

    public static void setAccessibilityLiveRegion(View view, int mode) {
        view.setAccessibilityLiveRegion(mode);
    }

    public static int getPaddingStart(View view) {
        return view.getPaddingStart();
    }

    public static int getPaddingEnd(View view) {
        return view.getPaddingEnd();
    }

    public static void setPaddingRelative(View view, int start, int top, int end, int bottom) {
        view.setPaddingRelative(start, top, end, bottom);
    }

    private static void bindTempDetach() {
        try {
            sDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
            sDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Couldn't find method", e);
        }
        sTempDetachBound = true;
    }

    public static void dispatchStartTemporaryDetach(View view) {
        view.dispatchStartTemporaryDetach();
    }

    public static void dispatchFinishTemporaryDetach(View view) {
        view.dispatchFinishTemporaryDetach();
    }

    @Deprecated
    public static float getTranslationX(View view) {
        return view.getTranslationX();
    }

    @Deprecated
    public static float getTranslationY(View view) {
        return view.getTranslationY();
    }

    @Deprecated
    public static Matrix getMatrix(View view) {
        return view.getMatrix();
    }

    public static int getMinimumWidth(View view) {
        return view.getMinimumWidth();
    }

    public static int getMinimumHeight(View view) {
        return view.getMinimumHeight();
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        if (sViewPropertyAnimatorMap == null) {
            sViewPropertyAnimatorMap = new WeakHashMap<>();
        }
        ViewPropertyAnimatorCompat vpa = sViewPropertyAnimatorMap.get(view);
        if (vpa == null) {
            ViewPropertyAnimatorCompat vpa2 = new ViewPropertyAnimatorCompat(view);
            sViewPropertyAnimatorMap.put(view, vpa2);
            return vpa2;
        }
        return vpa;
    }

    @Deprecated
    public static void setTranslationX(View view, float value) {
        view.setTranslationX(value);
    }

    @Deprecated
    public static void setTranslationY(View view, float value) {
        view.setTranslationY(value);
    }

    @Deprecated
    public static void setAlpha(View view, float value) {
        view.setAlpha(value);
    }

    @Deprecated
    public static void setX(View view, float value) {
        view.setX(value);
    }

    @Deprecated
    public static void setY(View view, float value) {
        view.setY(value);
    }

    @Deprecated
    public static void setRotation(View view, float value) {
        view.setRotation(value);
    }

    @Deprecated
    public static void setRotationX(View view, float value) {
        view.setRotationX(value);
    }

    @Deprecated
    public static void setRotationY(View view, float value) {
        view.setRotationY(value);
    }

    @Deprecated
    public static void setScaleX(View view, float value) {
        view.setScaleX(value);
    }

    @Deprecated
    public static void setScaleY(View view, float value) {
        view.setScaleY(value);
    }

    @Deprecated
    public static float getPivotX(View view) {
        return view.getPivotX();
    }

    @Deprecated
    public static void setPivotX(View view, float value) {
        view.setPivotX(value);
    }

    @Deprecated
    public static float getPivotY(View view) {
        return view.getPivotY();
    }

    @Deprecated
    public static void setPivotY(View view, float value) {
        view.setPivotY(value);
    }

    @Deprecated
    public static float getRotation(View view) {
        return view.getRotation();
    }

    @Deprecated
    public static float getRotationX(View view) {
        return view.getRotationX();
    }

    @Deprecated
    public static float getRotationY(View view) {
        return view.getRotationY();
    }

    @Deprecated
    public static float getScaleX(View view) {
        return view.getScaleX();
    }

    @Deprecated
    public static float getScaleY(View view) {
        return view.getScaleY();
    }

    @Deprecated
    public static float getX(View view) {
        return view.getX();
    }

    @Deprecated
    public static float getY(View view) {
        return view.getY();
    }

    public static void setElevation(View view, float elevation) {
        view.setElevation(elevation);
    }

    public static float getElevation(View view) {
        return view.getElevation();
    }

    public static void setTranslationZ(View view, float translationZ) {
        view.setTranslationZ(translationZ);
    }

    public static float getTranslationZ(View view) {
        return view.getTranslationZ();
    }

    public static void setTransitionName(View view, String transitionName) {
        view.setTransitionName(transitionName);
    }

    public static String getTransitionName(View view) {
        return view.getTransitionName();
    }

    public static int getWindowSystemUiVisibility(View view) {
        return view.getWindowSystemUiVisibility();
    }

    public static void requestApplyInsets(View view) {
        view.requestApplyInsets();
    }

    @Deprecated
    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean enabled) {
        if (sChildrenDrawingOrderMethod == null) {
            try {
                sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "Unable to find childrenDrawingOrderEnabled", e);
            }
            sChildrenDrawingOrderMethod.setAccessible(true);
        }
        try {
            sChildrenDrawingOrderMethod.invoke(viewGroup, Boolean.valueOf(enabled));
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", e2);
        } catch (IllegalArgumentException e3) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", e3);
        } catch (InvocationTargetException e4) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", e4);
        }
    }

    public static boolean getFitsSystemWindows(View v) {
        return v.getFitsSystemWindows();
    }

    @Deprecated
    public static void setFitsSystemWindows(View view, boolean fitSystemWindows) {
        view.setFitsSystemWindows(fitSystemWindows);
    }

    @Deprecated
    public static void jumpDrawablesToCurrentState(View v) {
        v.jumpDrawablesToCurrentState();
    }

    public static void setOnApplyWindowInsetsListener(View v, final OnApplyWindowInsetsListener listener) {
        if (listener == null) {
            v.setOnApplyWindowInsetsListener(null);
        } else {
            v.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: androidx.core.view.ViewCompat.1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                    WindowInsetsCompat compatInsets = WindowInsetsCompat.toWindowInsetsCompat(insets);
                    return OnApplyWindowInsetsListener.this.onApplyWindowInsets(view, compatInsets).toWindowInsets();
                }
            });
        }
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
        WindowInsets unwrapped = insets.toWindowInsets();
        WindowInsets result = view.onApplyWindowInsets(unwrapped);
        if (!result.equals(unwrapped)) {
            unwrapped = new WindowInsets(result);
        }
        return WindowInsetsCompat.toWindowInsetsCompat(unwrapped);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat insets) {
        WindowInsets unwrapped = insets.toWindowInsets();
        WindowInsets result = view.dispatchApplyWindowInsets(unwrapped);
        if (!result.equals(unwrapped)) {
            unwrapped = new WindowInsets(result);
        }
        return WindowInsetsCompat.toWindowInsetsCompat(unwrapped);
    }

    public static void setSystemGestureExclusionRects(View view, List<Rect> rects) {
        if (Build.VERSION.SDK_INT >= 29) {
            view.setSystemGestureExclusionRects(rects);
        }
    }

    public static List<Rect> getSystemGestureExclusionRects(View view) {
        if (Build.VERSION.SDK_INT >= 29) {
            return view.getSystemGestureExclusionRects();
        }
        return Collections.emptyList();
    }

    @Deprecated
    public static void setSaveFromParentEnabled(View v, boolean enabled) {
        v.setSaveFromParentEnabled(enabled);
    }

    @Deprecated
    public static void setActivated(View view, boolean activated) {
        view.setActivated(activated);
    }

    public static boolean hasOverlappingRendering(View view) {
        return view.hasOverlappingRendering();
    }

    public static boolean isPaddingRelative(View view) {
        return view.isPaddingRelative();
    }

    public static void setBackground(View view, Drawable background) {
        view.setBackground(background);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        return view.getBackgroundTintList();
    }

    public static void setBackgroundTintList(View view, ColorStateList tintList) {
        view.setBackgroundTintList(tintList);
    }

    public static PorterDuff.Mode getBackgroundTintMode(View view) {
        return view.getBackgroundTintMode();
    }

    public static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
    }

    public static void setNestedScrollingEnabled(View view, boolean enabled) {
        view.setNestedScrollingEnabled(enabled);
    }

    public static boolean isNestedScrollingEnabled(View view) {
        return view.isNestedScrollingEnabled();
    }

    public static boolean startNestedScroll(View view, int axes) {
        return view.startNestedScroll(axes);
    }

    public static void stopNestedScroll(View view) {
        view.stopNestedScroll();
    }

    public static boolean hasNestedScrollingParent(View view) {
        return view.hasNestedScrollingParent();
    }

    public static boolean dispatchNestedScroll(View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return view.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public static boolean dispatchNestedPreScroll(View view, int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return view.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public static boolean startNestedScroll(View view, int axes, int type) {
        if (view instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).startNestedScroll(axes, type);
        }
        if (type == 0) {
            return startNestedScroll(view, axes);
        }
        return false;
    }

    public static void stopNestedScroll(View view, int type) {
        if (view instanceof NestedScrollingChild2) {
            ((NestedScrollingChild2) view).stopNestedScroll(type);
        } else if (type == 0) {
            stopNestedScroll(view);
        }
    }

    public static boolean hasNestedScrollingParent(View view, int type) {
        if (view instanceof NestedScrollingChild2) {
            ((NestedScrollingChild2) view).hasNestedScrollingParent(type);
            return false;
        } else if (type == 0) {
            return hasNestedScrollingParent(view);
        } else {
            return false;
        }
    }

    public static void dispatchNestedScroll(View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type, int[] consumed) {
        if (view instanceof NestedScrollingChild3) {
            ((NestedScrollingChild3) view).dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
        } else {
            dispatchNestedScroll(view, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
        }
    }

    public static boolean dispatchNestedScroll(View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        if (view instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
        }
        if (type == 0) {
            return dispatchNestedScroll(view, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        }
        return false;
    }

    public static boolean dispatchNestedPreScroll(View view, int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        if (view instanceof NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
        }
        if (type == 0) {
            return dispatchNestedPreScroll(view, dx, dy, consumed, offsetInWindow);
        }
        return false;
    }

    public static boolean dispatchNestedFling(View view, float velocityX, float velocityY, boolean consumed) {
        return view.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public static boolean dispatchNestedPreFling(View view, float velocityX, float velocityY) {
        return view.dispatchNestedPreFling(velocityX, velocityY);
    }

    public static boolean isInLayout(View view) {
        return view.isInLayout();
    }

    public static boolean isLaidOut(View view) {
        return view.isLaidOut();
    }

    public static boolean isLayoutDirectionResolved(View view) {
        return view.isLayoutDirectionResolved();
    }

    public static float getZ(View view) {
        return view.getZ();
    }

    public static void setZ(View view, float z) {
        view.setZ(z);
    }

    public static void offsetTopAndBottom(View view, int offset) {
        view.offsetTopAndBottom(offset);
    }

    private static void compatOffsetTopAndBottom(View view, int offset) {
        view.offsetTopAndBottom(offset);
        if (view.getVisibility() == 0) {
            tickleInvalidationFlag(view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                tickleInvalidationFlag((View) parent);
            }
        }
    }

    public static void offsetLeftAndRight(View view, int offset) {
        view.offsetLeftAndRight(offset);
    }

    private static void compatOffsetLeftAndRight(View view, int offset) {
        view.offsetLeftAndRight(offset);
        if (view.getVisibility() == 0) {
            tickleInvalidationFlag(view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                tickleInvalidationFlag((View) parent);
            }
        }
    }

    private static void tickleInvalidationFlag(View view) {
        float y = view.getTranslationY();
        view.setTranslationY(1.0f + y);
        view.setTranslationY(y);
    }

    public static void setClipBounds(View view, Rect clipBounds) {
        view.setClipBounds(clipBounds);
    }

    public static Rect getClipBounds(View view) {
        return view.getClipBounds();
    }

    public static boolean isAttachedToWindow(View view) {
        return view.isAttachedToWindow();
    }

    public static boolean hasOnClickListeners(View view) {
        return view.hasOnClickListeners();
    }

    public static void setScrollIndicators(View view, int indicators) {
        view.setScrollIndicators(indicators);
    }

    public static void setScrollIndicators(View view, int indicators, int mask) {
        view.setScrollIndicators(indicators, mask);
    }

    public static int getScrollIndicators(View view) {
        return view.getScrollIndicators();
    }

    public static void setPointerIcon(View view, PointerIconCompat pointerIcon) {
        view.setPointerIcon((PointerIcon) (pointerIcon != null ? pointerIcon.getPointerIcon() : null));
    }

    public static Display getDisplay(View view) {
        return view.getDisplay();
    }

    public static void setTooltipText(View view, CharSequence tooltipText) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(tooltipText);
        }
    }

    public static boolean startDragAndDrop(View v, ClipData data, View.DragShadowBuilder shadowBuilder, Object localState, int flags) {
        return v.startDragAndDrop(data, shadowBuilder, localState, flags);
    }

    public static void cancelDragAndDrop(View v) {
        v.cancelDragAndDrop();
    }

    public static void updateDragShadow(View v, View.DragShadowBuilder shadowBuilder) {
        v.updateDragShadow(shadowBuilder);
    }

    public static int getNextClusterForwardId(View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.getNextClusterForwardId();
        }
        return -1;
    }

    public static void setNextClusterForwardId(View view, int nextClusterForwardId) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setNextClusterForwardId(nextClusterForwardId);
        }
    }

    public static boolean isKeyboardNavigationCluster(View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.isKeyboardNavigationCluster();
        }
        return false;
    }

    public static void setKeyboardNavigationCluster(View view, boolean isCluster) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setKeyboardNavigationCluster(isCluster);
        }
    }

    public static boolean isFocusedByDefault(View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.isFocusedByDefault();
        }
        return false;
    }

    public static void setFocusedByDefault(View view, boolean isFocusedByDefault) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setFocusedByDefault(isFocusedByDefault);
        }
    }

    public static View keyboardNavigationClusterSearch(View view, View currentCluster, int direction) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.keyboardNavigationClusterSearch(currentCluster, direction);
        }
        return null;
    }

    public static void addKeyboardNavigationClusters(View view, Collection<View> views, int direction) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.addKeyboardNavigationClusters(views, direction);
        }
    }

    public static boolean restoreDefaultFocus(View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.restoreDefaultFocus();
        }
        return view.requestFocus();
    }

    public static boolean hasExplicitFocusable(View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.hasExplicitFocusable();
        }
        return view.hasFocusable();
    }

    public static int generateViewId() {
        return View.generateViewId();
    }

    public static void addOnUnhandledKeyEventListener(View v, final OnUnhandledKeyEventListenerCompat listener) {
        if (Build.VERSION.SDK_INT >= 28) {
            SimpleArrayMap<OnUnhandledKeyEventListenerCompat, View.OnUnhandledKeyEventListener> viewListeners = (SimpleArrayMap) v.getTag(R.id.tag_unhandled_key_listeners);
            if (viewListeners == null) {
                viewListeners = new SimpleArrayMap<>();
                v.setTag(R.id.tag_unhandled_key_listeners, viewListeners);
            }
            View.OnUnhandledKeyEventListener fwListener = new View.OnUnhandledKeyEventListener() { // from class: androidx.core.view.ViewCompat.2
                @Override // android.view.View.OnUnhandledKeyEventListener
                public boolean onUnhandledKeyEvent(View v2, KeyEvent event) {
                    return OnUnhandledKeyEventListenerCompat.this.onUnhandledKeyEvent(v2, event);
                }
            };
            viewListeners.put(listener, fwListener);
            v.addOnUnhandledKeyEventListener(fwListener);
            return;
        }
        ArrayList<OnUnhandledKeyEventListenerCompat> viewListeners2 = (ArrayList) v.getTag(R.id.tag_unhandled_key_listeners);
        if (viewListeners2 == null) {
            viewListeners2 = new ArrayList<>();
            v.setTag(R.id.tag_unhandled_key_listeners, viewListeners2);
        }
        viewListeners2.add(listener);
        if (viewListeners2.size() == 1) {
            UnhandledKeyEventManager.registerListeningView(v);
        }
    }

    public static void removeOnUnhandledKeyEventListener(View v, OnUnhandledKeyEventListenerCompat listener) {
        View.OnUnhandledKeyEventListener fwListener;
        if (Build.VERSION.SDK_INT >= 28) {
            SimpleArrayMap<OnUnhandledKeyEventListenerCompat, View.OnUnhandledKeyEventListener> viewListeners = (SimpleArrayMap) v.getTag(R.id.tag_unhandled_key_listeners);
            if (viewListeners != null && (fwListener = viewListeners.get(listener)) != null) {
                v.removeOnUnhandledKeyEventListener(fwListener);
                return;
            }
            return;
        }
        ArrayList<OnUnhandledKeyEventListenerCompat> viewListeners2 = (ArrayList) v.getTag(R.id.tag_unhandled_key_listeners);
        if (viewListeners2 != null) {
            viewListeners2.remove(listener);
            if (viewListeners2.size() == 0) {
                UnhandledKeyEventManager.unregisterListeningView(v);
            }
        }
    }

    protected ViewCompat() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean dispatchUnhandledKeyEventBeforeHierarchy(View root, KeyEvent evt) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        return UnhandledKeyEventManager.at(root).preDispatch(evt);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean dispatchUnhandledKeyEventBeforeCallback(View root, KeyEvent evt) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        return UnhandledKeyEventManager.at(root).dispatch(root, evt);
    }

    public static void setScreenReaderFocusable(View view, boolean screenReaderFocusable) {
        screenReaderFocusableProperty().set(view, Boolean.valueOf(screenReaderFocusable));
    }

    public static boolean isScreenReaderFocusable(View view) {
        Boolean result = screenReaderFocusableProperty().get(view);
        if (result == null) {
            return false;
        }
        return result.booleanValue();
    }

    private static AccessibilityViewProperty<Boolean> screenReaderFocusableProperty() {
        return new AccessibilityViewProperty<Boolean>(R.id.tag_screen_reader_focusable, Boolean.class, 28) { // from class: androidx.core.view.ViewCompat.3
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public Boolean frameworkGet(View view) {
                return Boolean.valueOf(view.isScreenReaderFocusable());
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public void frameworkSet(View view, Boolean value) {
                view.setScreenReaderFocusable(value.booleanValue());
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public boolean shouldUpdate(Boolean oldValue, Boolean newValue) {
                return !booleanNullToFalseEquals(oldValue, newValue);
            }
        };
    }

    public static void setAccessibilityPaneTitle(View view, CharSequence accessibilityPaneTitle) {
        paneTitleProperty().set(view, accessibilityPaneTitle);
        if (accessibilityPaneTitle != null) {
            sAccessibilityPaneVisibilityManager.addAccessibilityPane(view);
        } else {
            sAccessibilityPaneVisibilityManager.removeAccessibilityPane(view);
        }
    }

    public static CharSequence getAccessibilityPaneTitle(View view) {
        return paneTitleProperty().get(view);
    }

    private static AccessibilityViewProperty<CharSequence> paneTitleProperty() {
        return new AccessibilityViewProperty<CharSequence>(R.id.tag_accessibility_pane_title, CharSequence.class, 8, 28) { // from class: androidx.core.view.ViewCompat.4
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public CharSequence frameworkGet(View view) {
                return view.getAccessibilityPaneTitle();
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public void frameworkSet(View view, CharSequence value) {
                view.setAccessibilityPaneTitle(value);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public boolean shouldUpdate(CharSequence oldValue, CharSequence newValue) {
                return !TextUtils.equals(oldValue, newValue);
            }
        };
    }

    public static boolean isAccessibilityHeading(View view) {
        Boolean result = accessibilityHeadingProperty().get(view);
        if (result == null) {
            return false;
        }
        return result.booleanValue();
    }

    public static void setAccessibilityHeading(View view, boolean isHeading) {
        accessibilityHeadingProperty().set(view, Boolean.valueOf(isHeading));
    }

    private static AccessibilityViewProperty<Boolean> accessibilityHeadingProperty() {
        return new AccessibilityViewProperty<Boolean>(R.id.tag_accessibility_heading, Boolean.class, 28) { // from class: androidx.core.view.ViewCompat.5
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public Boolean frameworkGet(View view) {
                return Boolean.valueOf(view.isAccessibilityHeading());
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public void frameworkSet(View view, Boolean value) {
                view.setAccessibilityHeading(value.booleanValue());
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // androidx.core.view.ViewCompat.AccessibilityViewProperty
            public boolean shouldUpdate(Boolean oldValue, Boolean newValue) {
                return !booleanNullToFalseEquals(oldValue, newValue);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static abstract class AccessibilityViewProperty<T> {
        private final int mFrameworkMinimumSdk;
        private final int mTagKey;
        private final Class<T> mType;

        abstract T frameworkGet(View view);

        abstract void frameworkSet(View view, T t);

        AccessibilityViewProperty(int tagKey, Class<T> type, int frameworkMinimumSdk) {
            this(tagKey, type, 0, frameworkMinimumSdk);
        }

        AccessibilityViewProperty(int tagKey, Class<T> type, int contentChangeType, int frameworkMinimumSdk) {
            this.mTagKey = tagKey;
            this.mType = type;
            this.mFrameworkMinimumSdk = frameworkMinimumSdk;
        }

        void set(View view, T value) {
            if (frameworkAvailable()) {
                frameworkSet(view, value);
            } else if (extrasAvailable() && shouldUpdate(get(view), value)) {
                ViewCompat.getOrCreateAccessibilityDelegateCompat(view);
                view.setTag(this.mTagKey, value);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            }
        }

        T get(View view) {
            if (frameworkAvailable()) {
                return frameworkGet(view);
            }
            if (extrasAvailable()) {
                T t = (T) view.getTag(this.mTagKey);
                if (this.mType.isInstance(t)) {
                    return t;
                }
                return null;
            }
            return null;
        }

        private boolean frameworkAvailable() {
            return Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk;
        }

        private boolean extrasAvailable() {
            return true;
        }

        boolean shouldUpdate(T oldValue, T newValue) {
            return !newValue.equals(oldValue);
        }

        boolean booleanNullToFalseEquals(Boolean a, Boolean b) {
            boolean aBool = a == null ? false : a.booleanValue();
            boolean bBool = b == null ? false : b.booleanValue();
            return aBool == bBool;
        }
    }

    static void notifyViewAccessibilityStateChangedIfNeeded(View view, int changeType) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (!accessibilityManager.isEnabled()) {
            return;
        }
        boolean isAccessibilityPane = getAccessibilityPaneTitle(view) != null;
        if (getAccessibilityLiveRegion(view) != 0 || (isAccessibilityPane && view.getVisibility() == 0)) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(isAccessibilityPane ? 32 : 2048);
            event.setContentChangeTypes(changeType);
            view.sendAccessibilityEventUnchecked(event);
        } else if (view.getParent() != null) {
            try {
                view.getParent().notifySubtreeAccessibilityStateChanged(view, view, changeType);
            } catch (AbstractMethodError e) {
                Log.e(TAG, view.getParent().getClass().getSimpleName() + " does not fully implement ViewParent", e);
            }
        }
    }

    /* loaded from: classes.dex */
    static class AccessibilityPaneVisibilityManager implements ViewTreeObserver.OnGlobalLayoutListener, View.OnAttachStateChangeListener {
        private WeakHashMap<View, Boolean> mPanesToVisible = new WeakHashMap<>();

        AccessibilityPaneVisibilityManager() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            for (Map.Entry<View, Boolean> entry : this.mPanesToVisible.entrySet()) {
                checkPaneVisibility(entry.getKey(), entry.getValue().booleanValue());
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            registerForLayoutCallback(view);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
        }

        void addAccessibilityPane(View pane) {
            this.mPanesToVisible.put(pane, Boolean.valueOf(pane.getVisibility() == 0));
            pane.addOnAttachStateChangeListener(this);
            if (pane.isAttachedToWindow()) {
                registerForLayoutCallback(pane);
            }
        }

        void removeAccessibilityPane(View pane) {
            this.mPanesToVisible.remove(pane);
            pane.removeOnAttachStateChangeListener(this);
            unregisterForLayoutCallback(pane);
        }

        private void checkPaneVisibility(View pane, boolean oldVisibility) {
            boolean newVisibility = pane.getVisibility() == 0;
            if (oldVisibility != newVisibility) {
                if (newVisibility) {
                    ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(pane, 16);
                }
                this.mPanesToVisible.put(pane, Boolean.valueOf(newVisibility));
            }
        }

        private void registerForLayoutCallback(View view) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        private void unregisterForLayoutCallback(View view) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    /* loaded from: classes.dex */
    static class UnhandledKeyEventManager {
        private static final ArrayList<WeakReference<View>> sViewsWithListeners = new ArrayList<>();
        private WeakHashMap<View, Boolean> mViewsContainingListeners = null;
        private SparseArray<WeakReference<View>> mCapturedKeys = null;
        private WeakReference<KeyEvent> mLastDispatchedPreViewKeyEvent = null;

        UnhandledKeyEventManager() {
        }

        private SparseArray<WeakReference<View>> getCapturedKeys() {
            if (this.mCapturedKeys == null) {
                this.mCapturedKeys = new SparseArray<>();
            }
            return this.mCapturedKeys;
        }

        static UnhandledKeyEventManager at(View root) {
            UnhandledKeyEventManager manager = (UnhandledKeyEventManager) root.getTag(R.id.tag_unhandled_key_event_manager);
            if (manager == null) {
                UnhandledKeyEventManager manager2 = new UnhandledKeyEventManager();
                root.setTag(R.id.tag_unhandled_key_event_manager, manager2);
                return manager2;
            }
            return manager;
        }

        boolean dispatch(View root, KeyEvent event) {
            if (event.getAction() == 0) {
                recalcViewsWithUnhandled();
            }
            View consumer = dispatchInOrder(root, event);
            if (event.getAction() == 0) {
                int keycode = event.getKeyCode();
                if (consumer != null && !KeyEvent.isModifierKey(keycode)) {
                    getCapturedKeys().put(keycode, new WeakReference<>(consumer));
                }
            }
            return consumer != null;
        }

        private View dispatchInOrder(View view, KeyEvent event) {
            WeakHashMap<View, Boolean> weakHashMap = this.mViewsContainingListeners;
            if (weakHashMap == null || !weakHashMap.containsKey(view)) {
                return null;
            }
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = vg.getChildCount() - 1; i >= 0; i--) {
                    View v = vg.getChildAt(i);
                    View consumer = dispatchInOrder(v, event);
                    if (consumer != null) {
                        return consumer;
                    }
                }
            }
            if (onUnhandledKeyEvent(view, event)) {
                return view;
            }
            return null;
        }

        boolean preDispatch(KeyEvent event) {
            int idx;
            WeakReference<KeyEvent> weakReference = this.mLastDispatchedPreViewKeyEvent;
            if (weakReference == null || weakReference.get() != event) {
                this.mLastDispatchedPreViewKeyEvent = new WeakReference<>(event);
                WeakReference<View> currentReceiver = null;
                SparseArray<WeakReference<View>> capturedKeys = getCapturedKeys();
                if (event.getAction() == 1 && (idx = capturedKeys.indexOfKey(event.getKeyCode())) >= 0) {
                    currentReceiver = capturedKeys.valueAt(idx);
                    capturedKeys.removeAt(idx);
                }
                if (currentReceiver == null) {
                    currentReceiver = capturedKeys.get(event.getKeyCode());
                }
                if (currentReceiver != null) {
                    View target = currentReceiver.get();
                    if (target != null && ViewCompat.isAttachedToWindow(target)) {
                        onUnhandledKeyEvent(target, event);
                    }
                    return true;
                }
                return false;
            }
            return false;
        }

        private boolean onUnhandledKeyEvent(View v, KeyEvent event) {
            ArrayList<OnUnhandledKeyEventListenerCompat> viewListeners = (ArrayList) v.getTag(R.id.tag_unhandled_key_listeners);
            if (viewListeners != null) {
                for (int i = viewListeners.size() - 1; i >= 0; i--) {
                    if (viewListeners.get(i).onUnhandledKeyEvent(v, event)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }

        static void registerListeningView(View v) {
            ArrayList<WeakReference<View>> arrayList = sViewsWithListeners;
            synchronized (arrayList) {
                Iterator<WeakReference<View>> it = arrayList.iterator();
                while (it.hasNext()) {
                    WeakReference<View> wv = it.next();
                    if (wv.get() == v) {
                        return;
                    }
                }
                sViewsWithListeners.add(new WeakReference<>(v));
            }
        }

        static void unregisterListeningView(View v) {
            synchronized (sViewsWithListeners) {
                int i = 0;
                while (true) {
                    ArrayList<WeakReference<View>> arrayList = sViewsWithListeners;
                    if (i < arrayList.size()) {
                        if (arrayList.get(i).get() != v) {
                            i++;
                        } else {
                            arrayList.remove(i);
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        private void recalcViewsWithUnhandled() {
            WeakHashMap<View, Boolean> weakHashMap = this.mViewsContainingListeners;
            if (weakHashMap != null) {
                weakHashMap.clear();
            }
            ArrayList<WeakReference<View>> arrayList = sViewsWithListeners;
            if (arrayList.isEmpty()) {
                return;
            }
            synchronized (arrayList) {
                if (this.mViewsContainingListeners == null) {
                    this.mViewsContainingListeners = new WeakHashMap<>();
                }
                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    ArrayList<WeakReference<View>> arrayList2 = sViewsWithListeners;
                    WeakReference<View> vw = arrayList2.get(i);
                    View v = vw.get();
                    if (v == null) {
                        arrayList2.remove(i);
                    } else {
                        this.mViewsContainingListeners.put(v, Boolean.TRUE);
                        for (ViewParent nxt = v.getParent(); nxt instanceof View; nxt = nxt.getParent()) {
                            this.mViewsContainingListeners.put((View) nxt, Boolean.TRUE);
                        }
                    }
                }
            }
        }
    }
}