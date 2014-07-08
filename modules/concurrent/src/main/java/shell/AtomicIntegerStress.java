package shell;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static shell.AtomicIntegerStress.getLatch;
import static shell.AtomicIntegerStress.getNext;
import static shell.AtomicIntegerStress.getNextWithAtomic;

/**
 * AtomicIntegerStress (Stress Test)
 * <p>50 thread and 1000 times invokes getNext for every thread.</p>
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-07-07
 */
public class AtomicIntegerStress {

    //general count
    private static int id = 0;

    //jdk atomically integer count
    private static AtomicInteger atomicInteger = new AtomicInteger();

    private static CountDownLatch latch = null;


    public synchronized static int getNext(){

        return ++id;
    }


    public static int getNextWithAtomic(){

        return atomicInteger.incrementAndGet();
    }


    public static CountDownLatch getLatch(){

        return AtomicIntegerStress.latch;
    }


    public static void main(String[] args) throws Exception{

        latch = new CountDownLatch(50);
        long beginTime = System.nanoTime();

        for (int i=0;i<50;i++){
            new Thread(new Task(false)).start();
        }

        //Current thread will be block util latch reduce to zero
        latch.await();
        System.out.println("Synchronized consume time: " + (System.nanoTime()-beginTime));

        latch = new CountDownLatch(50);
        beginTime = System.nanoTime();

        for (int j=0;j<50;j++){
            new Thread(new Task(true)).start();
        }

        latch.await();

        System.out.println("CAS consume time: " + (System.nanoTime()-beginTime));
    }

}



/**
 * Task
 */
class Task implements Runnable{

    //if atomically
    private boolean isAtomic;


    public Task(boolean isAtomic){

        this.isAtomic = isAtomic;
    }


    @Override
    public void run() {

        for (int i=0;i<1000;i++){

            if(isAtomic){
                getNextWithAtomic();
            }else {
                getNext();
            }
        }
        getLatch().countDown();

    }
}
