package com.example.androidlab.binder_connect_pool;

import android.os.RemoteException;

public class ComputeImpl  extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
