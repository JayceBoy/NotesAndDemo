public class RunnableDemo implements Runnable{
    private Thread t;
    private String threadName;
    RunnableDemo(String threadName) {
        this.threadName = threadName;
        System.out.println("创建线程->" + threadName);
    }
    @Override
    public void run() {
        System.out.println("Running->" + threadName);
        try{
           for (int i = 1; i <= 5;i++) {
               System.out.println("Thread:" + threadName + i);
               //线程休眠
               Thread.sleep(500);
           }
        }catch (Exception e){
            System.out.println("线程：" + threadName + "->中断");
        }
        System.out.println("线程：" + threadName + "->结束");
    }

    public void start() {
        if(null == t) {
            t = new Thread(this,threadName);
            t.start();
        }
    }
}
