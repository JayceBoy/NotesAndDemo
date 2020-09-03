package com.jayce.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewCachedThreadPoolDemo {
    /**
     * 我们获取四次次线程，观察4个线程地址
     * @param args
     */
    public static void main(String[] args) {
        //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        System.out.println("======可缓存线程池======");
        for(int index = 1; index <= 4; index++) {
            newCachedThreadPool.execute(new ThreadDemo(index));
        }
    }
}
