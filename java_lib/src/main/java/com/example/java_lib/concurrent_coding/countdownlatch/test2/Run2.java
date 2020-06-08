package com.example.java_lib.concurrent_coding.countdownlatch.test2;

import java.util.concurrent.CountDownLatch;


public class Run2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch maxRuner = new CountDownLatch(10);
        MyThread2[] tArray = new MyThread2[Integer.parseInt(""
                + maxRuner.getCount())];//创建10条线程

        for (int i = 0; i < tArray.length; i++) {
            tArray[i] = new MyThread2(maxRuner);
            tArray[i].setName("线程" + (i + 1));
            tArray[i].start();
        }
        maxRuner.await();//阻塞住   等countdown至0  方行
        System.out.println("都回来了！");
    }
}
