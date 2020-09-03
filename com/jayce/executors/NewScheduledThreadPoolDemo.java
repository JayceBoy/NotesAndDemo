package com.jayce.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewScheduledThreadPoolDemo {
    /**
     * 我们获取10次线程，观察10个线程地址
     * @param args
     */
    public static void main(String[] args) {
        //创建一个定长线程池，支持定时及周期性任务执行
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(4);
        System.out.println("======可缓存线程池======");
        for(int index = 1; index <= 10; index++) {
            newScheduledThreadPool.schedule(new ThreadDemo(index), 30,TimeUnit.SECONDS);
        }
    }
}
