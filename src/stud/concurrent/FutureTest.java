package stud.concurrent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;


/**
 * Created by root on 16-5-14.
 */
public class FutureTest {

    public static void main(String [] args) throws  InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<String>> callables = new HashSet<>();
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return"Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return"Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return"Task 3";
            }
        });
        List<Future<String>> futures = executorService.invokeAll(callables);
        for(Future future : futures){
            try {
                System.out.println("future.get = " + future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
