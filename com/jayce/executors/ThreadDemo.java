package com.jayce.executors;

public class ThreadDemo implements Runnable{
    private Integer index;
    public ThreadDemo(Integer index) {
        this.index = index;
    }
    @Override
    public void run() {
        try {
            System.out.println("线程开始处理");
            Thread.sleep(100*index);
            System.out.println("我的线程编号是：" + index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
