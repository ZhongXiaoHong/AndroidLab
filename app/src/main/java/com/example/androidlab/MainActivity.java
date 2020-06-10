package com.example.androidlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;

import com.example.androidlab.aidl.BookManagerActivity;
import com.example.androidlab.anr.AnrService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goAidlDemo(View view) {
        startActivity(new Intent(this, BookManagerActivity.class));
    }

    public void dolongTimeInServiceMainThread(View view) {
        startService(new Intent(this, AnrService.class));
    }

    public void dolongTime(View view) {
      Handler h =  new Handler(){
           @Override
           public void handleMessage(@NonNull Message msg) {
               super.handleMessage(msg);
               long current = SystemClock.uptimeMillis();

               while(((SystemClock.uptimeMillis()-current)/1000)<150){
                   System.out.println("第秒"+(SystemClock.uptimeMillis()-current)/1000);
               }
               System.out.println("结束*********");
           }
       };

      h.sendMessage(Message.obtain());
    }
}
