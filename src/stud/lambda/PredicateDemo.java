package stud.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by root on 16-5-8.
 */
public class PredicateDemo {

    public static void main(String args[]){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", null);

        Predicate<String> startsWithJ = (n) -> n.startsWith("J");

        Predicate<String> fourLetterLong = (n) -> n.length() == 4;

        Predicate<String> isNotNull = (n) -> n != null;

        languages.stream().filter(isNotNull.and(startsWithJ).and(fourLetterLong)).forEach(
                (n) ->
                        System.out.print("\nLanguages, which starts with 'J' and four letter long is : " + n));
    }

    public static void filter(List<String> names, Predicate condition) {
        for(String name: names) {
            if(condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    //更佳的方式
    public static void filter2(List names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }


}
