package com.example.java_lib.concurrent_coding.countdownlatch.test3;

public class MyThread3 extends Thread {
    private MyService3 myService;
    public MyThread3(MyService3 myService) {
        super();
        this.myService = myService;
    }
    @Override
    public void run() {
        myService.testMethod();
    }
}
