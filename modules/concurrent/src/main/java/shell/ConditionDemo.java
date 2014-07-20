package shell;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConditionDemo
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-07-20
 */
public class ConditionDemo {

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();


    public Condition getCondition (){
        return this.condition;
    }


    public ReentrantLock getLock(){
        return this.lock;
    }


    public void run(){

        new Thread(new WorkerThread(this)).start();
        new Thread(new WorkerThread(this)).start();
        new Thread(new WorkerThread(this)).start();

        new Thread(new NotifierThread(this)).start();
    }


    public static void main(String[] args){

        ConditionDemo demo = new ConditionDemo();
        demo.run();
    }

}


/**
 * worker thread
 */
final class WorkerThread implements Runnable {

    private ConditionDemo conditionDemo = null;

    private ReentrantLock lock = null;

    private Condition condition = null;

    public WorkerThread(ConditionDemo demo) {
        this.conditionDemo = demo;
    }

    @Override
    public void run() {

        condition = conditionDemo.getCondition();
        lock = conditionDemo.getLock();

        if(condition!=null && lock!=null){

            lock.lock();

            System.out.println("==========> get lock [" + Thread.currentThread().toString() + "]");

            try {

                System.out.println("==========> release lock and be blocked [" + Thread.currentThread().toString() + "]");

                //if some condition is ok
                condition.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            System.out.println("==========> be notified and wake up [" + Thread.currentThread().toString() + "]");
        }


    }
}


/**
 * notify thread
 * a worker thread be waked up every time
 */
final class NotifierThread implements Runnable {

    private ConditionDemo conditionDemo = null;

    private Condition condition = null;

    private ReentrantLock lock = null;

    public NotifierThread(ConditionDemo demo) {
        this.conditionDemo = demo;
    }

    @Override
    public void run() {

        condition = conditionDemo.getCondition();
        lock = conditionDemo.getLock();

        if(condition != null && lock != null){


            try {

                lock.lock();

                Thread.sleep(3000);
                condition.signal();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
