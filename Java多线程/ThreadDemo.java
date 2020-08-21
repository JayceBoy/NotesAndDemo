public class ThreadDemo extends Thread{
    private Thread thread;
    private String threadName;

    public ThreadDemo(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Running:" + threadName);
        try{
            for(int i = 0; i < 10; i++) {
                System.out.println("Thread:" + threadName + "->"+i);
            }
            Thread.sleep(500);
        }catch (InterruptedException e) {
            System.out.println("Thread："+"->中断");
        }
    }

    public void start() {
        System.out.println("线程：" + threadName + "->启动");
        if(null == thread) {
            thread = new Thread(this,threadName);
//            thread.setPriority(2);   //线程优先级
//            thread.interrupt();      //中断线程
//            thread.setName("线程名"); //设置线程名
//            thread.setDaemon(true);  //标记为守护线程或用户线程
//            thread.join(10);    //等待该线程终止的最长时间
//            thread.isAlive();     //线程是否处于活动状态
            thread.start();
        }
    }
}
