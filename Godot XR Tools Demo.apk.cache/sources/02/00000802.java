package org.godotengine.godot.plugin;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.godotengine.godot.Godot;

/* loaded from: classes.dex */
public final class GodotPluginRegistry {
    private static final String GODOT_PLUGIN_V1_NAME_PREFIX = "org.godotengine.plugin.v1.";
    private static final String TAG = GodotPluginRegistry.class.getSimpleName();
    private static GodotPluginRegistry instance;
    private final ConcurrentHashMap<String, GodotPlugin> registry = new ConcurrentHashMap<>();

    private GodotPluginRegistry(Godot godot) {
        loadPlugins(godot);
    }

    public GodotPlugin getPlugin(String pluginName) {
        return this.registry.get(pluginName);
    }

    public Collection<GodotPlugin> getAllPlugins() {
        return this.registry.values();
    }

    public static GodotPluginRegistry initializePluginRegistry(Godot godot) {
        if (instance == null) {
            instance = new GodotPluginRegistry(godot);
        }
        return instance;
    }

    public static GodotPluginRegistry getPluginRegistry() throws IllegalStateException {
        GodotPluginRegistry godotPluginRegistry = instance;
        if (godotPluginRegistry == null) {
            throw new IllegalStateException("Plugin registry hasn't been initialized.");
        }
        return godotPluginRegistry;
    }

    private void loadPlugins(Godot godot) {
        String str;
        String str2 = GODOT_PLUGIN_V1_NAME_PREFIX;
        try {
            Activity activity = godot.getActivity();
            ApplicationInfo appInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 128);
            Bundle metaData = appInfo.metaData;
            if (metaData != null && !metaData.isEmpty()) {
                int godotPluginV1NamePrefixLength = GODOT_PLUGIN_V1_NAME_PREFIX.length();
                for (String metaDataName : metaData.keySet()) {
                    if (metaDataName.startsWith(str2)) {
                        String pluginName = metaDataName.substring(godotPluginV1NamePrefixLength).trim();
                        String str3 = TAG;
                        Log.i(str3, "Initializing Godot plugin " + pluginName);
                        String pluginHandleClassFullName = metaData.getString(metaDataName);
                        if (TextUtils.isEmpty(pluginHandleClassFullName)) {
                            str = str2;
                            Log.w(str3, "Invalid plugin loader class for " + pluginName);
                        } else {
                            try {
                                GodotPlugin pluginHandle = (GodotPlugin) Class.forName(pluginHandleClassFullName).getConstructor(Godot.class).newInstance(godot);
                                if (!pluginName.equals(pluginHandle.getPluginName())) {
                                    Log.w(str3, "Meta-data plugin name does not match the value returned by the plugin handle: " + pluginName + " =/= " + pluginHandle.getPluginName());
                                }
                                try {
                                    try {
                                        this.registry.put(pluginName, pluginHandle);
                                        str = str2;
                                        try {
                                            Log.i(str3, "Completed initialization for Godot plugin " + pluginHandle.getPluginName());
                                        } catch (ClassNotFoundException e) {
                                            e = e;
                                            Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                            str2 = str;
                                        } catch (IllegalAccessException e2) {
                                            e = e2;
                                            Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                            str2 = str;
                                        } catch (InstantiationException e3) {
                                            e = e3;
                                            Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                            str2 = str;
                                        } catch (NoSuchMethodException e4) {
                                            e = e4;
                                            Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                            str2 = str;
                                        } catch (InvocationTargetException e5) {
                                            e = e5;
                                            Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                            str2 = str;
                                        }
                                    } catch (ClassNotFoundException e6) {
                                        e = e6;
                                        str = str2;
                                        Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                        str2 = str;
                                    } catch (IllegalAccessException e7) {
                                        e = e7;
                                        str = str2;
                                        Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                        str2 = str;
                                    } catch (InstantiationException e8) {
                                        e = e8;
                                        str = str2;
                                        Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                        str2 = str;
                                    } catch (NoSuchMethodException e9) {
                                        e = e9;
                                        str = str2;
                                        Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                        str2 = str;
                                    } catch (InvocationTargetException e10) {
                                        e = e10;
                                        str = str2;
                                        Log.w(TAG, "Unable to load Godot plugin " + pluginName, e);
                                        str2 = str;
                                    }
                                } catch (PackageManager.NameNotFoundException e11) {
                                    e = e11;
                                    Log.e(TAG, "Unable load Godot Android plugins from the manifest file.", e);
                                    return;
                                }
                            } catch (ClassNotFoundException e12) {
                                e = e12;
                            } catch (IllegalAccessException e13) {
                                e = e13;
                            } catch (InstantiationException e14) {
                                e = e14;
                            } catch (NoSuchMethodException e15) {
                                e = e15;
                            } catch (InvocationTargetException e16) {
                                e = e16;
                            }
                        }
                    } else {
                        str = str2;
                    }
                    str2 = str;
                }
            }
        } catch (PackageManager.NameNotFoundException e17) {
            e = e17;
        }
    }
}