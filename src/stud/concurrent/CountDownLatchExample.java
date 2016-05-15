package stud.concurrent;


import java.util.concurrent.*;
/**
 * Created by root on 16-5-8.
 */
public class CountDownLatchExample {
    public static void main(String [] args){
        runDemo1();
    }


    /**
     * 等待线程完成后在执行一下一部操作.
     */
    public static void runDemo1(){
        int count = 10;

        final CountDownLatch l = new CountDownLatch(count);
        for(int i = 0; i < count; ++i)
        {
            final int index = i;
            new Thread(()-> {
                try {
                    Thread.currentThread().sleep(3 * 1000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

                System.out.println("thread " + index + " has finished...");

                l.countDown();

            }).start();
        }

        try {
            l.await();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        System.out.println("now all threads have finished");
    }
}
