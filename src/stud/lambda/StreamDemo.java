package stud.lambda;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 16-5-8.
 */
public class StreamDemo {
    public static void main(String args[]){
        filter();
        callObjMethod();
        callObjMethod();
        distinct();

        summaryStatics();
    }

    //過濾
    public static void filter(){
        List<String>  strList=Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        List<String> filtered = strList.stream().filter(x -> x.length()>
                2).collect(Collectors.toList());

        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
    }

    //調用toUpperCase函數
    public static void callObjMethod(){
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy","U.K.","Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    //distinct
    public static void distinct(){
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map( i ->i*i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s, Square Without duplicates : %s %n", numbers, distinct);
    }

    //Summary Static
    public static void summaryStatics(){
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        IntSummaryStatistics stats=numbers.stream().mapToInt(x->x).summaryStatistics();

        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());

    }
}
