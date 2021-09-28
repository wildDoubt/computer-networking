package Assignment6;

public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                    System.out.println("My thread ended");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
        System.out.println("main() ended");

    }
}
