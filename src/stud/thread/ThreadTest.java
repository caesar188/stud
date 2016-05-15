package stud.thread;


import java.util.ArrayList;

/**
 * Created by root on 16-5-15.
 */
public class ThreadTest{

public static void main(String [] args){



    new Thread(()->{

    }).start();
}


    static ArrayList  object1=new ArrayList(100);

    static ArrayList  object2=new ArrayList(100);

    public synchronized static  void method1(){
        object1.add("1");

        object2.add("2");
    }
    object1


}
