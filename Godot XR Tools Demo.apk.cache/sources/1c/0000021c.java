package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowInsetsCompat {
    private final Object mInsets;

    WindowInsetsCompat(Object insets) {
        this.mInsets = insets;
    }

    public WindowInsetsCompat(WindowInsetsCompat src) {
        this.mInsets = src == null ? null : new WindowInsets((WindowInsets) src.mInsets);
    }

    public int getSystemWindowInsetLeft() {
        return ((WindowInsets) this.mInsets).getSystemWindowInsetLeft();
    }

    public int getSystemWindowInsetTop() {
        return ((WindowInsets) this.mInsets).getSystemWindowInsetTop();
    }

    public int getSystemWindowInsetRight() {
        return ((WindowInsets) this.mInsets).getSystemWindowInsetRight();
    }

    public int getSystemWindowInsetBottom() {
        return ((WindowInsets) this.mInsets).getSystemWindowInsetBottom();
    }

    public boolean hasSystemWindowInsets() {
        return ((WindowInsets) this.mInsets).hasSystemWindowInsets();
    }

    public boolean hasInsets() {
        return ((WindowInsets) this.mInsets).hasInsets();
    }

    public boolean isConsumed() {
        return ((WindowInsets) this.mInsets).isConsumed();
    }

    public boolean isRound() {
        return ((WindowInsets) this.mInsets).isRound();
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return new WindowInsetsCompat(((WindowInsets) this.mInsets).consumeSystemWindowInsets());
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        return new WindowInsetsCompat(((WindowInsets) this.mInsets).replaceSystemWindowInsets(left, top, right, bottom));
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect systemWindowInsets) {
        return new WindowInsetsCompat(((WindowInsets) this.mInsets).replaceSystemWindowInsets(systemWindowInsets));
    }

    public int getStableInsetTop() {
        return ((WindowInsets) this.mInsets).getStableInsetTop();
    }

    public int getStableInsetLeft() {
        return ((WindowInsets) this.mInsets).getStableInsetLeft();
    }

    public int getStableInsetRight() {
        return ((WindowInsets) this.mInsets).getStableInsetRight();
    }

    public int getStableInsetBottom() {
        return ((WindowInsets) this.mInsets).getStableInsetBottom();
    }

    public boolean hasStableInsets() {
        return ((WindowInsets) this.mInsets).hasStableInsets();
    }

    public WindowInsetsCompat consumeStableInsets() {
        return new WindowInsetsCompat(((WindowInsets) this.mInsets).consumeStableInsets());
    }

    public DisplayCutoutCompat getDisplayCutout() {
        if (Build.VERSION.SDK_INT >= 28) {
            return DisplayCutoutCompat.wrap(((WindowInsets) this.mInsets).getDisplayCutout());
        }
        return null;
    }

    public WindowInsetsCompat consumeDisplayCutout() {
        if (Build.VERSION.SDK_INT >= 28) {
            return new WindowInsetsCompat(((WindowInsets) this.mInsets).consumeDisplayCutout());
        }
        return this;
    }

    public Insets getSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Insets.wrap(((WindowInsets) this.mInsets).getSystemWindowInsets());
        }
        return Insets.of(getSystemWindowInsetLeft(), getSystemWindowInsetTop(), getSystemWindowInsetRight(), getSystemWindowInsetBottom());
    }

    public Insets getStableInsets() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Insets.wrap(((WindowInsets) this.mInsets).getStableInsets());
        }
        return Insets.of(getStableInsetLeft(), getStableInsetTop(), getStableInsetRight(), getStableInsetBottom());
    }

    public Insets getMandatorySystemGestureInsets() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Insets.wrap(((WindowInsets) this.mInsets).getMandatorySystemGestureInsets());
        }
        return getSystemWindowInsets();
    }

    public Insets getTappableElementInsets() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Insets.wrap(((WindowInsets) this.mInsets).getTappableElementInsets());
        }
        return getSystemWindowInsets();
    }

    public Insets getSystemGestureInsets() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Insets.wrap(((WindowInsets) this.mInsets).getSystemGestureInsets());
        }
        return getSystemWindowInsets();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WindowInsetsCompat)) {
            return false;
        }
        WindowInsetsCompat other = (WindowInsetsCompat) o;
        return ObjectsCompat.equals(this.mInsets, other.mInsets);
    }

    public int hashCode() {
        Object obj = this.mInsets;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public WindowInsets toWindowInsets() {
        return (WindowInsets) this.mInsets;
    }

    public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets insets) {
        return new WindowInsetsCompat(Objects.requireNonNull(insets));
    }
}