package stud.thread;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 16-5-15.
 */
public class MyConditionTest {
    public static void main(String args[]){

        Lock lock =new ReentrantLock();

        Condition notfull=lock.newCondition();

        Condition notEmpty= lock.newCondition();

        int size=10;

        PriorityQueue<String> queue= new PriorityQueue<>(size);

        new Thread(()->{
            while (true){
                lock.lock();
                try {
                while(queue.size()==size){
                    try{
                        notfull.await();

                        System.out.println("Producor wait");
                    }catch (InterruptedException ie){
                        notEmpty.signal();
                    }
                }

                queue.offer("apple");

                System.out.println("Producor put an apple");
                notEmpty.signal();


                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            } finally{
                lock.unlock();
            }

            }
        }).start();


        new Thread(()->{

            while (true){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                lock.lock();
                try {
                    while(queue.size()==0){
                        try{
                            System.out.println("Consumer wait");
                            notEmpty.await();
                        }catch (InterruptedException ie){
                            notfull.signal();
                        }
                    }

                    queue.poll();
                    System.out.println("Consumer got an apple");
                    notfull.signal();
                } finally{
                    lock.unlock();
                }

            }
        }).start();
    }



}
