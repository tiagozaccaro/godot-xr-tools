package com.godot.game;

import android.os.Bundle;
import org.godotengine.godot.GodotActivity;

/* loaded from: classes3.dex */
public class GodotApp extends GodotActivity {
    @Override // org.godotengine.godot.GodotActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        setTheme(org.godotengine.godotxrtoolsdemo.R.style.GodotAppMainTheme);
        super.onCreate(savedInstanceState);
    }
}