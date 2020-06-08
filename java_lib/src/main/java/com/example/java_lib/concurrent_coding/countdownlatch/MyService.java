package com.example.java_lib.concurrent_coding.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class MyService {

    private CountDownLatch down = new CountDownLatch(1);

    public void testMethod() {
        try {
            System.out.println("A");
            down.await();//阻塞住，count =0 才放开执行下面的语句
            System.out.println("B");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void downMethod() {
        System.out.println("X");
        down.countDown();
    }
}
