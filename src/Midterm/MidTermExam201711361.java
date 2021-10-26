package Midterm;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.concurrent.*;

class FindMinMaxTask implements Callable<double[]> {
    private int startC, startD, endC, endD;
    private double maxValue = Double.MIN_VALUE;
    private double minValue = Double.MAX_VALUE;
    private int maxRow, maxCol, minRow, minCol, maxC, maxD, minC, minD;
    private int missingFiles;
    private long totalCount;
    private double totalSum;
    private boolean option;

    FindMinMaxTask(int startC, int startD, int endC, int endD, boolean option) {
        this.startC = startC;
        this.startD = startD;
        this.endC = endC;
        this.endD = endD;
        this.missingFiles = 0;
        this.totalCount = 0;
        this.totalSum = 0;
        this.option = option;
    }

    private void calc(int c, int d) throws IOException {
        int currRow = 0, currCol = 0;
        BufferedReader bufferedReader = null;
        String inputLine;
        String baseURL = "https://home.konkuk.ac.kr/~leehw/Site/nptest/2021/MidTerM/";
        URL url1, url2;
        url1 = new URL(baseURL + "file%20" + "(c=" + c + ")_(d=" + d + ").txt");
        url2 = new URL(baseURL + "file%20" + "{c=" + c + "}_{d=" + d + "}.txt");
        URLConnection conn1 = url1.openConnection();
        URLConnection conn2 = url2.openConnection();

//        conn1.setConnectTimeout(60000);
//        conn2.setConnectTimeout(60000);

        StringTokenizer stringTokenizer;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(conn1.getInputStream()));// 여기서 에러나면 url2 탐색
        } catch (FileNotFoundException e1) {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(conn2.getInputStream()));// 여기서 에러나면 url2 탐색

            } catch (FileNotFoundException e2) {
                missingFiles++;
                return;
            }
        }
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringTokenizer = new StringTokenizer(inputLine, "\t");
            while (stringTokenizer.hasMoreElements()) {
                double temp = Double.parseDouble(stringTokenizer.nextToken());
                totalSum += temp;
                totalCount++;
                if (temp > maxValue) {
                    maxRow = currRow;
                    maxCol = currCol;
                    maxValue = temp;
                    maxC = c;
                    maxD = d;
                }
                if (temp < minValue) {
                    minRow = currRow;
                    minCol = currCol;
                    minValue = temp;
                    minC = c;
                    minD = d;
                }
                currCol++;
            }
            currCol = 0;
            currRow++;
        }
        bufferedReader.close();
    }

    @Override
    public double[] call() throws Exception {
        int indexC = startC;
        int indexD = startD;
        while (indexC <= endC) {
            if (indexC == endC && indexD >= endD) break;
            calc(indexC, indexD);
//            System.out.println("pair: " + indexC + " " + indexD);
            indexD++;
            if (indexD > 50) {
                indexC++;
                indexD = 1;
            }
        }

        return new double[]{
                maxC, maxD, maxRow, maxCol, maxValue,
                minC, minD, minRow, minCol, minValue,
                missingFiles, totalCount, totalSum
        };
    }
}

public class MidTermExam201711361 {

    static int SIZE = 3000;

    public static double[][] find(int core, boolean option) throws ExecutionException, InterruptedException {
        int d = SIZE / core;
        int r = SIZE % core;
        double[][] result = new double[core][];
        FindMinMaxTask[] tasks = new FindMinMaxTask[core];
        Future<double[]>[] futures = new Future[core];
//        System.out.println("core count: " + core);
        int startC, startD;
        int endC, endD;

        int taskStart, taskEnd; // 작업의 시작과 끝
        long start = System.nanoTime();
        for (int i = 0; i < core; i++) {
            if (i < r) {
                taskStart = i * d + i % core;
                taskEnd = (i + 1) * d + i % core + 1;
            } else {
                taskStart = i * d + r;
                taskEnd = (i + 1) * d + r;
            }
            startC = taskStart / 50 + 1;
            startD = taskStart % 50 + 1;
            endC = taskEnd / 50 + 1;
            endD = taskEnd % 50 + 1;
//            System.out.println(startC + " " + startD + " " + endC + " " + endD + " " + (taskEnd - taskStart));
            tasks[i] = new FindMinMaxTask(startC, startD, endC, endD, option); // (startC, starD) ~ (endC, endD)
        }
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
        int n = 5;
        double averageSum = 0;
        for (int i = 0; i < n; i++) {
            double maxValue = Double.MIN_VALUE;
            int maxRow = 0, maxCol = 0, maxC = 0, maxD = 0;
            double minValue = Double.MAX_VALUE;
            int minRow = 0, minCol = 0, minC = 0, minD = 0;
            long miss = 0;
            double totalSum = 0;
            long totalCount = 0;

            // option true: read from file
            // option false: read from URL
            double[][] data = find(coreCount, false);
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
            averageSum += totalSum/totalCount;
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
        System.out.println("전체 평균: \t"+ String.format("%.4f", averageSum/n));
    }
}
