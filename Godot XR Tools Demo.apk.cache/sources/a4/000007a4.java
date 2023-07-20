package org.godotengine.godot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;

/* loaded from: classes.dex */
public class GodotDownloaderAlarmReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.d("GODOT", "Alarma recivida");
        try {
            DownloaderClientMarshaller.startDownloadServiceIfRequired(context, intent, GodotDownloaderService.class);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("GODOT", "Exception: " + e.getClass().getName() + ":" + e.getMessage());
        }
    }
}