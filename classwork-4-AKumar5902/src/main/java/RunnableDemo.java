import java.util.concurrent.atomic.AtomicInteger;

public class RunnableDemo implements Runnable {

    //int counter = 0;
    AtomicInteger counter = new AtomicInteger(0);

    final static int TIMES = 1000000;

    public static void main(String[] args) throws InterruptedException {
        RunnableDemo runnableDemo = new RunnableDemo();
        var threadA = new Thread(runnableDemo);
        var threadB = new Thread(runnableDemo);

        threadA.start();
        threadB.start();

        threadB.join();
        threadA.join();

        // System.out.println(runnableDemo.counter);
        System.out.println(runnableDemo.counter.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < TIMES; i++) {
            // pass in lock object 1 per atomic block
            // synchronized(RunnableDemo.class) {
            //    counter++;
            //  }
            // add();
            counter.incrementAndGet();

        }
        System.out.println("Thread Complete");
    }
    /*
    synchronized public void add() {
        counter++;
    }
    */

}
