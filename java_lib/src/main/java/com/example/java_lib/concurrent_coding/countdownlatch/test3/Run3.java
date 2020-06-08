package com.example.java_lib.concurrent_coding.countdownlatch.test3;

public class Run3 {
    public static void main(String[] args) throws InterruptedException {
        MyService3 service = new MyService3();
        MyThread3[] tArray = new MyThread3[10];
        for (int i = 0; i < tArray.length; i++) {
            tArray[i] = new MyThread3(service);
            tArray[i].setName("线程" + (i + 1));
            tArray[i].start();
        }
        Thread.sleep(4000);
        service.downMethod();
    }
}

