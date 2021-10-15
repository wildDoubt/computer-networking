package Midterm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class MultithreadedMinMaxFinder {

    static int SIZE = 600;

    public static double[][] find(int core) throws ExecutionException, InterruptedException {
        int d = SIZE / core;
        double[][] result = new double[core][];
        FindMinMaxTask[] tasks = new FindMinMaxTask[core];
        Future<double[]>[] futures = new Future[core];
        System.out.println("core count: " + core);
        int startC, startD;
        int endC, endD;
        for (int i = 0; i < SIZE; i += d) {
            System.out.println(i + " " + (i + d));
            startC = i / 60;
            startD = i % 60;
            endC = (i + d) / 60;
            endD = (i + d) % 60;
            tasks[i / d] = new FindMinMaxTask(startC, startD, endC, endD);
            System.out.println(startC + "\t" + startD + "\t" + endC + "\t" + endD);
        }
        long start = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(core);

        for (int i = 0; i < core; i++) {
            futures[i] = service.submit(tasks[i]);
        }
        service.shutdown();
        for (int i = 0; i < core; i++) {
            result[i] = futures[i].get();
        }
        long end = System.nanoTime();
        System.out.println("Run-time(s): " + (end - start) / 1e9);

        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int coreCount = Runtime.getRuntime().availableProcessors();

        double maxValue = Double.MIN_VALUE;
        int maxRow = 0, maxCol = 0, maxC = 0, maxD = 0;
        double minValue = Double.MAX_VALUE;
        int minRow = 0, minCol = 0, minC = 0, minD = 0;

        double[][] data = find(coreCount);
        for (double[] result : data) {
            if (maxValue < result[4]) {
                maxValue = result[4];
                maxRow = (int) result[2];
                maxCol = (int) result[3];
                maxC = (int) result[0];
                maxD = (int) result[1];
            }
            if (minValue > result[9]) {
                minValue = result[9];
                minRow = (int) result[7];
                minCol = (int) result[8];
                minC = (int) result[5];
                minD = (int) result[6];
            }
        }
        System.out.println("MAX\n");
        System.out.println("c: " + maxC + '\t' + "d: " + maxD + '\t' + "row: " + maxRow + '\t' + "column: " + maxCol + '\t' + "maxValue: " + maxValue + '\n');
        System.out.println("MIN\n");
        System.out.println("c: " + minC + '\t' + "d: " + minD + '\t' + "row: " + minRow + '\t' + "column: " + minCol + '\t' + "maxValue: " + minValue + '\n');
    }
}
