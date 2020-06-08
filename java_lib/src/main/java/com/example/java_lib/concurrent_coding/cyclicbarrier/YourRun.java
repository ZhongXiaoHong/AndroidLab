package com.example.java_lib.concurrent_coding.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class YourRun {

    public static void main(String[] args) {
        
        //也就是5个线程都执行了cbRef对象的await()方法后程序才可以继续向下运行，
        // 否则这些线程彼此互相等待，一直呈阻塞状态
        CyclicBarrier cbRef = new CyclicBarrier(5, new Runnable() {
            public void run() {
                System.out.println("全都到了！");
            }
        });
        YourThread[] threadArray = new YourThread[5];
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new YourThread(cbRef);
        }
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i].start();
        }
    }

}
