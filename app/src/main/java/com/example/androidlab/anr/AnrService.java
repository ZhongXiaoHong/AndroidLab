package com.example.androidlab.anr;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import androidx.annotation.NonNull;

public class AnrService extends Service {
    public AnrService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("AnrService******onCreate***");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Handler h =  new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                long current = SystemClock.uptimeMillis();

                while(((SystemClock.uptimeMillis()-current)/1000)<50){
                    System.out.println("第秒"+(SystemClock.uptimeMillis()-current)/1000);
                }
                System.out.println("结束*********");
            }
        };
        h.sendMessage(Message.obtain());

//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return START_STICKY;
    }
}
