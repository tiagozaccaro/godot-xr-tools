package com.google.android.vending.licensing;

/* loaded from: classes.dex */
public interface Policy {
    public static final int LICENSED = 256;
    public static final int NOT_LICENSED = 561;
    public static final int RETRY = 291;

    boolean allowAccess();

    String getLicensingUrl();

    void processServerResponse(int i, ResponseData responseData);
}