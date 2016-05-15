package stud.lambda;

import java.util.List;
import java.util.Arrays;

/**
 * Created by root on 16-5-8.
 */
public class MapReduce {

    public static void main(String args []){
        map();

        reduce();
    }

    private static void map(){

        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);

        // Without lambda expressions:
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }
        // With Lambda expression:
        costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
    }

    private static void reduce(){

        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        // Old way:
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        // New way:
        double bill = costBeforeTax.stream().map((cost) ->cost + .12*cost).reduce((sum, cost) -> sum
                + cost).get();

        System.out.println("Total : " + bill);
    }
}
