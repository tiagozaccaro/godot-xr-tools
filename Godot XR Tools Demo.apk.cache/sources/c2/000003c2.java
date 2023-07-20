package com.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ILicenseResultListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.vending.licensing.ILicenseResultListener";

    void verifyLicense(int i, String str, String str2) throws RemoteException;

    /* loaded from: classes.dex */
    public static class Default implements ILicenseResultListener {
        @Override // com.android.vending.licensing.ILicenseResultListener
        public void verifyLicense(int responseCode, String signedData, String signature) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements ILicenseResultListener {
        static final int TRANSACTION_verifyLicense = 1;

        public Stub() {
            attachInterface(this, ILicenseResultListener.DESCRIPTOR);
        }

        public static ILicenseResultListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILicenseResultListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ILicenseResultListener)) {
                return (ILicenseResultListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ILicenseResultListener.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(ILicenseResultListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            String _arg1 = data.readString();
                            String _arg2 = data.readString();
                            verifyLicense(_arg0, _arg1, _arg2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements ILicenseResultListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILicenseResultListener.DESCRIPTOR;
            }

            @Override // com.android.vending.licensing.ILicenseResultListener
            public void verifyLicense(int responseCode, String signedData, String signature) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILicenseResultListener.DESCRIPTOR);
                    _data.writeInt(responseCode);
                    _data.writeString(signedData);
                    _data.writeString(signature);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}