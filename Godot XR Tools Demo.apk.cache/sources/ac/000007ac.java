package org.godotengine.godot;

import android.app.Activity;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public interface GodotHost {
    Activity getActivity();

    default List<String> getCommandLine() {
        return Collections.emptyList();
    }

    default void onGodotSetupCompleted() {
    }

    default void onGodotMainLoopStarted() {
    }

    default void onGodotForceQuit(Godot instance) {
    }

    default boolean onGodotForceQuit(int godotInstanceId) {
        return false;
    }

    default void onGodotRestartRequested(Godot instance) {
    }

    default int onNewGodotInstanceRequested(String[] args) {
        return 0;
    }
}