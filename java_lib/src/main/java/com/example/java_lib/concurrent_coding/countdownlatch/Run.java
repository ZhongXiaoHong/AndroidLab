package com.example.java_lib.concurrent_coding.countdownlatch;

public class Run{

    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        MyThread t = new MyThread(service);
        t.start();
        Thread.sleep(2000);
        service.downMethod();//让“门锁打开”
    }
}
