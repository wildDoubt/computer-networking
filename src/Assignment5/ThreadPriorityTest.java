package Assignment5;

public class ThreadPriorityTest extends Thread {

    public void run() {
        for (int i = 0; i < 10; i++) System.out.println(getName() + ": " + i);
    }

    public static void main(String[] args) {
        ThreadPriorityTest t0 = new ThreadPriorityTest();
        ThreadPriorityTest t1 = new ThreadPriorityTest();

        t0.setPriority(Thread.MIN_PRIORITY);
        t1.setPriority(Thread.MAX_PRIORITY);

        t0.start();
        t1.start();
    }
}
