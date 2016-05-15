package stud.concurrent;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by root on 16-5-8.
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String args [])
    {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        final Lock rlock = lock.readLock();
        final Lock wlock = lock.writeLock();

        final CountDownLatch l  = new CountDownLatch(11);

        // start read thread

        for (int i=0;i<10;i++) {
            new Thread(() -> {


                rlock.lock();
                System.out.println(new Date() + "now to get read lock");

                try {
                    Thread.currentThread().sleep(5 * 1000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

                rlock.unlock();
                System.out.println(new Date() + "now to unlock rlock");

                l.countDown();
            }).start();
        }

        // start w thread
        new Thread(()->{


                wlock.lock();System.out.println(new Date() + "now to get wlock");


                wlock.unlock();System.out.println(new Date() + "now to unlock wlock");

                l.countDown();
        }).start();

        try {
            l.await();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        System.out.println(new Date() + "finished");
    }
}
