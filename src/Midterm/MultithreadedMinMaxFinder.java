package Midterm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedMinMaxFinder {

    static int SIZE = 3000;

    public static double[][] find(int core, boolean option) throws ExecutionException, InterruptedException {
        int d = SIZE / core;
        double[][] result = new double[core][];
        FindMinMaxTask[] tasks = new FindMinMaxTask[core];
        Future<double[]>[] futures = new Future[core];
        System.out.println("core count: " + core);
        int startC, startD;
        int endC, endD;

        int taskStart, taskEnd; // 작업의 시작과 끝

        for (int i = 0; i < core; i++) {
            taskStart = i * d;
            taskEnd = i == core - 1 ? SIZE : (i + 1) * d;
            startC = taskStart / 60 + 1;
            startD = taskStart % 60 + 1;
            endC = taskEnd / 60 + 1;
            endD = taskEnd % 60 + 1;
            tasks[i] = new FindMinMaxTask(startC, startD, endC, endD, option); // (startC, starD) ~ (endC, endD)
        }
        ExecutorService service = Executors.newFixedThreadPool(core);

        long start = System.nanoTime();

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
        long miss = 0;
        double totalSum = 0;
        long totalCount = 0;

        // option true: read from file
        // option false: read from URL
        double[][] data = find(coreCount, true);
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
            miss += result[10];
            totalCount += result[11];
            totalSum += result[12];
        }
        System.out.println("MAX");
        System.out.println("c: " + maxC + '\t' + "d: " + maxD + '\t' + "row: " + maxRow + '\t' + "column: " + maxCol + '\t' + "maxValue: " + maxValue);
        System.out.println();
        System.out.println("MIN");
        System.out.println("c: " + minC + '\t' + "d: " + minD + '\t' + "row: " + minRow + '\t' + "column: " + minCol + '\t' + "maxValue: " + minValue);
        System.out.println();
        System.out.println("Total Sum:\t" + totalSum);
        System.out.println("Total Count:\t" + totalCount);
        System.out.println("Average:\t" + String.format("%.4f", totalSum / totalCount));
        System.out.println();
        System.out.println("number of missing files: " + miss);
    }
}
