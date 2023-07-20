package org.godotengine.godot.plugin;

import java.util.Collections;
import java.util.Set;

/* loaded from: classes.dex */
public interface GodotPluginInfoProvider {
    String getPluginName();

    default Set<SignalInfo> getPluginSignals() {
        return Collections.emptySet();
    }

    default Set<String> getPluginGDExtensionLibrariesPaths() {
        return Collections.emptySet();
    }

    default void onPluginRegistered() {
    }
}