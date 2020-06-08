package com.example.androidlab.binder_connect_pool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class BinderPoolExcutor {
    private static final String TAG = "BinderPoolExcutor";


    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPoolExcutor sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPoolExcutor(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPoolExcutor getInsance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPoolExcutor.class) {
                if (sInstance == null) {
                    sInstance = new BinderPoolExcutor(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection,
                Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();//绑定服务成功之后才释放掉，
            // 异步转成同步，确保BinderPoolExcutor构造方法调用完成之后，
            // mBinderPool已经不为空
            //即使得调用处可以直接这么用：
            //BinderPoolExcutor.getInstance(context).query(...);query中要用到mBinderpool但是不为空
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * query binder by binderCode from binder pool
     *
     * @param binderCode the unique token of binder
     * @return binder who's token is binderCode<br>
     * return null when not found or BinderPoolService died.
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection()

    {

        @Override
        public void onServiceDisconnected (ComponentName name){
        // ignored.
    }

        @Override
        public void onServiceConnected (ComponentName name, IBinder service){
        mBinderPool = IBinderPool.Stub.asInterface(service);
        try {
            mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);//z
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mConnectBinderPoolCountDownLatch.countDown();
    }
    }

    ;

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.
            DeathRecipient() {
        @Override
        public void binderDied() {
            Log.w(TAG, "binder died.");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,
                    0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

}



