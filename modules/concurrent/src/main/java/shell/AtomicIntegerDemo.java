package shell;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicIntegerDemo (ThreadSafe, Atomically)
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-07-07
 */
public class AtomicIntegerDemo {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static int getNext() {

        return atomicInteger.getAndIncrement();
    }


    public static void main(String[] args) {

        int result = AtomicIntegerDemo.getNext();

        System.out.println("result: " + result);
    }

}