package stud.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 16-5-8.
 */
public class Lambda1 {

    public static void main(String args[]){

        demo1();
        demo2();

    }

    //用Lambda表达式实现Runnable接口
    public static void demo1(){
        //Before Java 8:
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();
        //Java 8 way:
        new Thread(() -> {
            System.out.println("first line");
            System.out.println("second line");
        }).start();
    }

    //用Lambda表达式进行List迭代
    public static void demo2(){
        //Prior Java 8 :
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.println(feature);
        }
        //In Java 8:
        List features2 = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features2.forEach(n -> System.out.println(n));

        features2.forEach(System.out::println);
    }
}
