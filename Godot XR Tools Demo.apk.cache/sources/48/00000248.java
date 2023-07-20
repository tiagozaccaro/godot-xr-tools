package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class CompoundButtonCompat {
    private static final String TAG = "CompoundButtonCompat";
    private static Field sButtonDrawableField;
    private static boolean sButtonDrawableFieldFetched;

    private CompoundButtonCompat() {
    }

    public static void setButtonTintList(CompoundButton button, ColorStateList tint) {
        button.setButtonTintList(tint);
    }

    public static ColorStateList getButtonTintList(CompoundButton button) {
        return button.getButtonTintList();
    }

    public static void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
        button.setButtonTintMode(tintMode);
    }

    public static PorterDuff.Mode getButtonTintMode(CompoundButton button) {
        return button.getButtonTintMode();
    }

    public static Drawable getButtonDrawable(CompoundButton button) {
        return button.getButtonDrawable();
    }
}