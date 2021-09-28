package Assignment6;

class Producer extends Thread {
    private Buffer blank;

    //constructor goes here

    public Producer(Buffer blank) {
        this.blank = blank;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                blank.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Producer: Produced" + i);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {

            }
        }
    }
}

class Consumer extends Thread {
    private Buffer blank;

    //constructor goes here
    public Consumer(Buffer blank) {
        this.blank = blank;
    }

    public void run() {
        int value = 0;

        for (int i = 0; i < 10; i++) {
            try {
                value = blank.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Consumer: Consumed" + value);
        }
    }
}

class Buffer {
    private int contents;
    private boolean available = false;

    public synchronized int get() throws InterruptedException {
        //if there is no available contents, keep waiting
        if(!available) this.wait();
        //if there is available contents, print the contents on screen (this is like consuming new contents), and notify
        System.out.println("Buffer: "+ contents);
        this.notify();
        //set available to false
        available = false;
        return contents;
    }

    public synchronized void put(int value) throws InterruptedException {
        //if there is available contents, keep waiting
        if(available) this.wait();
        //if there is no available contents, store value to contents (this is like producing new contents), and notify
        contents = value;
        this.notify();
        //set available to true
        available = true;
    }
}


public class Homework01ProducerConsumerProblem {

    public static void main(String[] args) {
        Buffer b = new Buffer();
        Producer p1 = new Producer(b);
        Consumer c1 = new Consumer(b);

        p1.start();
        c1.start();

    }

}//*/
