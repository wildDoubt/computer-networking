package Assignment4;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedMaxFinder {
    public static int max(int[] data) throws InterruptedException, ExecutionException{
        if(data.length==1){
            return data[0];
        }else if(data.length==0){
            throw new IllegalArgumentException();
        }

        FindMaxTask task1 = new FindMaxTask(data, 0, data.length/2);
        FindMaxTask task2 = new FindMaxTask(data, data.length/2, data.length);

        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);
        service.shutdown();
        return Math.max(future1.get(), future2.get());
    }

    public static void main(String[] args) {
        int[] arr = {1, 8, 2, 9, 5, 6};
        System.out.print("Array: ");
        for(int i: arr){
            System.out.print(i);
            System.out.print(' ');
        }
        try{
            System.out.println();
            System.out.println("Max value: "+max(arr));
        }catch (Exception exception){
            System.out.println(exception);
        }
    }
}
