package stud.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockDemo {

    public static void main(String args []){
        new ReentrantLockDemo().demoLock();
    }

    public void demoLock()
    {
        final int loopcount = 10000;
        int threadcount = 10;

        final SafeSeqWithLock seq = new SafeSeqWithLock();

        final CountDownLatch l = new CountDownLatch(threadcount);

        for(int i = 0; i < threadcount; ++i)
        {
            final int index = i;
            new Thread(()->{
                    for(int j = 0; j < loopcount; ++j)
                    {
                        seq.inc();
                    }
                    System.out.println("finished : " + index);
                    l.countDown();
            }).start();
        }

        try {
            l.await();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        System.out.println("both have finished....");

        System.out.println("SafeSeqWithLock:" + seq.get());

    }
}

class SafeSeqWithLock{
    private long count = 0;

    private ReentrantLock lock = new ReentrantLock();

    public void inc()
    {
        lock.lock();

        try{
            count++;
        }
        finally{
            lock.unlock();
        }
    }

    public long get()
    {
        return count;
    }
}
