// IBinderPool.aidl
package com.example.androidlab.binder_connect_pool;

// Declare any non-default types here with import statements

interface IBinderPool {

    IBinder  queryBinder(int binderCode);
}
