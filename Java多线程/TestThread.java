public class TestThread {
    public static void main(String[] args) {
        RunnableDemo R1 = new RunnableDemo("线程A");
        R1.start();

        RunnableDemo R2 = new RunnableDemo("线程B");
        R2.start();

        ThreadDemo T1 = new ThreadDemo("线程C");
        T1.start();

        ThreadDemo T2 = new ThreadDemo("线程D");
        T2.start();
    }
}
