package stud.concurrent;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConditionDemo{

    public static void main(String [] args){
        //testPutTack();
        testTackPut();
    }


    public static void testPutTack(){
        final BoundedBuffer bb = new BoundedBuffer();
        int count = 10;
        final CountDownLatch c = new CountDownLatch(count * 2);
        System.out.println(new Date() + " now try to call put for " + count );
        for(int i = 0; i < count ; ++i)
        {
            final int index = i;
            try {
                Thread t = new Thread(()->{
                    try {
                        bb.put(index);
                        System.out.println(new Date() + "  put finished:  " + index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    c.countDown();
                });
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println(new Date() + " main thread is going to sleep for 10 seconds");
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println(new Date() + " now try to take for count: " + count);
        for(int i =0; i < count; ++i)
        {
            Thread t= new Thread(()->{
                try {
                    Object o = bb.take();
                    System.out.println(new Date() + " take get: " + o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c.countDown();
            });
            t.start();
        }
        try {
            System.out.println(new Date() + ": main thread is to wait for all threads");
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + " all threads finished");
    }

    public static void testTackPut(){
        final BoundedBuffer bb = new BoundedBuffer();
        int count = 10;
        final CountDownLatch c = new CountDownLatch(count * 2);

        System.out.println(new Date() + " first try to call take for count: " + count);
        for(int i =0; i < count; ++i)
        {
            final int index = i;
            Thread t= new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().setName(" TAKE " + index);
                        Object o = bb.take();
                        System.out.println(new Date() + " " + " take get: " + o );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    c.countDown();
                }
            });
            t.start();
        }
        try {
            System.out.println(new Date() + " main thread is going to sleep for 10 seconds");
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println(new Date() + " now try to call put for " + count );
        for(int i = 0; i < count ; ++i)
        {
            final int index = i;
            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Thread.currentThread().setName(" PUT " + index);
                        try {

                            bb.put(index);

                            System.out.println(new Date() + " " + "  put finished:  " + index );

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        c.countDown();

                    }

                });
                t.start();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        try {
            System.out.println(new Date() + ": main thread is to wait for all threads");

            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + " all threads finished");
    }
}

class BoundedBuffer{
    final Lock lock = new ReentrantLock();

    final Condition notFull  = lock.newCondition();

    final Condition notEmpty = lock.newCondition();



    final Object[] items = new Object[5];

    int putptr, takeptr, count;



    public void put(Object x) throws InterruptedException {

        lock.lock();

        try {

            while (count == items.length)

                notFull.await();

            items[putptr] = x;

            if (++putptr == items.length) putptr = 0;

            ++count;

            notEmpty.signal();

        } finally {

            lock.unlock();

        }

    }


    public Object take() throws InterruptedException {

        lock.lock();

        try {

            while (count == 0)

                notEmpty.await();

            Object x = items[takeptr];

            if (++takeptr == items.length) takeptr = 0;

            --count;

            notFull.signal();

            return x;

        } finally {

            lock.unlock();

        }

    }
}
/**
 * 这段程序的目的是测试先put()后take()的操作，
 1. 我将BoundedBuffer的大小设置成5，同时在每次进入notFull和notEmpty的await()的时候打印一下表示当前线程正在等待；
 2. 先开启10个线程做put()操作，预计有5个线程可以完成，另外5个会进入等待
 3. 主线程sleep10秒中，然后启动10个线程做take()操作；

 这个时候，首先第一个take()必然成功完成，在这之前等待的5个put()线程都不会被唤醒， 接下来的事情就不好说了；
 剩下的5个put()线程和9个take()线程中的任何一个都可能会被jvm调度；
 比如可能出现
 a. 开始take()的时候，有5个连续的take()线程完成操作； 然后又进入put()和take()交替的情况
 b. 第一个take()之后，立刻会有一个put()线程被notFull().signal()唤醒； 然后继续有take()和put()交替的情况；

 其中take()线程也可能进入notEmpty.await()操作；
 但是任何时候，未完成的take()线程始终>=未完成的put()线程， 这个也是很自然的；
 */