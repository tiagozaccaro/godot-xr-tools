package org.godotengine.godot.input;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class Joystick {
    int device_id;
    protected int hatX;
    protected int hatY;
    String name;
    List<Integer> axes = new ArrayList();
    protected boolean hasAxisHat = false;
    protected final SparseArray<Float> axesValues = new SparseArray<>(4);
}