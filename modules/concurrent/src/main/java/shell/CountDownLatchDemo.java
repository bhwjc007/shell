package shell;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchDemo
 * The startLatch that prevents any worker from proceeding
 * util the main thread is ready for them to proceed.
 * The main thread will be blocked util all doneLatch have completed.
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-07-08
 */
public class CountDownLatchDemo {

    private static CountDownLatch startLatch = new CountDownLatch(1);

    private static CountDownLatch doneLatch = new CountDownLatch(500);

    public static void main(String[] args) throws Exception{

        for (int i=0;i<500;i++){
            new Thread(new Worker(startLatch,doneLatch)).start();
        }

        //First step
        System.out.println("Initial something......");
        startLatch.countDown();

        doneLatch.await();
        //Third step
        System.out.println("All worker to be finished......");

    }
}


/**
 * work thread
 */
class Worker implements Runnable{

    private final CountDownLatch startLatch;

    private final CountDownLatch doneLatch;

    public Worker(CountDownLatch startLatch, CountDownLatch doneLatch){

        this.startLatch = startLatch;
        this.doneLatch = doneLatch;
    }


    @Override
    public void run(){

        try {

            startLatch.await();
            //Two step
            System.out.println("Worker to be done........");

            doneLatch.countDown();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
