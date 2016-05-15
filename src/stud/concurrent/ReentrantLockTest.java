package stud.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
public class ReentrantLockTest extends Thread{
    ReentrantLock lock;
    private int id;
    public ReentrantLockTest(int i,ReentrantLock test){
        this.id=i;
        this.lock=test;
    }
    public void run(){
        print(id);
    }

    public void print(int str){
        try{
            lock.lock();
            System.out.println(str+"获得");
            Thread.sleep((int)(Math.random()*1000));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println(str+"释放");
            lock.unlock();
        }
    }
    public static void main(String args[]){
        ExecutorService service=Executors.newCachedThreadPool();
        ReentrantLock lock=new ReentrantLock();
        for(int i=0;i<10;i++){
            service.submit(new ReentrantLockTest(i,lock));
        }
        service.shutdown();
    }
}

