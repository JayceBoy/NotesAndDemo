package com.jayce.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPoolDemo {
    /**
     * 我们获取10次线程，观察10个线程地址
     * @param args
     */
    public static void main(String[] args) {
        //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        System.out.println("======可缓存线程池======");
        for(int index = 1; index <= 10; index++) {
            newFixedThreadPool.execute(new ThreadDemo(index));
        }
    }
}
