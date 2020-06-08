package com.example.java_lib.concurrent_coding.countdownlatch.test2;

import java.util.concurrent.CountDownLatch;
public class MyThread2 extends Thread {
    private CountDownLatch maxRuner;
    public MyThread2(CountDownLatch maxRuner) {
        super();
        this.maxRuner = maxRuner;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            maxRuner.countDown();//  count_down  减一
        } catch (InterruptedException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

