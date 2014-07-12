package shell;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolExecutorDemo
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-07-12
 */
public class ThreadPoolExecutorDemo {

    //total consume time is 300ms
//    private final BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();

    //total consume time is 610ms
    private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);

    private AtomicInteger completedTaskCount = new AtomicInteger(0);

    private AtomicInteger rejectedTaskCount = new AtomicInteger(0);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor
            (10,600,30, TimeUnit.SECONDS,workQueue, Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());


    public void start(){

        long startTime = System.nanoTime();

        CountDownLatch latch = new CountDownLatch(1000);

        for (int i=0;i<1000;i++){
            try{

                executor.execute(new Task(latch));

            }catch (RejectedExecutionException e){

                latch.countDown();

                System.out.println("Be rejected task count : " + rejectedTaskCount.incrementAndGet());
            }
        }

        try {

            latch.await();
            executor.shutdownNow();

            System.out.println("Total consume : " + (System.nanoTime() - startTime)/10000000 + " ms");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    class Task implements Runnable{

        private CountDownLatch latch = null;

        public Task(CountDownLatch latch){

            this.latch = latch;
        }


        @Override
        public void run() {

            long startTime = System.nanoTime();

            try {

                Thread.currentThread().sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Complete task count : " + completedTaskCount.incrementAndGet());

            System.out.println("Consume : " + (System.nanoTime() - startTime)/10000000 + " ms");

            latch.countDown();
        }
    }


    public static void main(String[] args){

        ThreadPoolExecutorDemo threadPoolExecutorDemo = new ThreadPoolExecutorDemo();

        threadPoolExecutorDemo.start();
    }



}
