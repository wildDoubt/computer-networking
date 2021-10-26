package Midterm;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

class MyThread {
    private BufferedReader bufferedReader;
    private String inputLine;
    private int[] result;
    private double maxValue = Double.MIN_VALUE;
    private double minValue = Double.MAX_VALUE;
    private int maxRow, maxCol, minRow, minCol;
    private int currRow, currCol;
    private int c, d;

    MyThread(int c, int d) {
        this.c = c;
        this.d = d;
        currRow = 0;
        currCol = 0;
    }

    public int run() {
        try {
            String baseURL = "https://home.konkuk.ac.kr/~leehw/Site/nptest/2021/MidTerM/";

            URL url1, url2;
            url1 = new URL(baseURL + "file%20" + "(c=" + c + ")_(d=" + d + ").txt");
            url2 = new URL(baseURL + "file%20" + "{c=" + c + "}_{d=" + d + "}.txt");

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(url1.openStream()));// 여기서 에러나면 url2 탐색
            } catch (FileNotFoundException e1) {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(url2.openStream()));
                } catch (FileNotFoundException e2) {
                    return 0;
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream("result2/result_" + this.c + "_" + this.d + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            while ((inputLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(inputLine + '\n');
            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double[] getMaxValue() {
        if (maxValue != Double.MIN_VALUE) return new double[]{maxRow, maxCol, maxValue};
        else return null;
    }

    public double[] getMinValue() {
        if (minValue != Double.MAX_VALUE) return new double[]{minRow, minCol, minValue};
        else return null;
    }
}

public class Midterm {
    public static void main(String[] args) throws InterruptedException {
        MyThread[][] threads = new MyThread[61][51];
        double maxValue = Double.MIN_VALUE;
        int maxRow = 0, maxCol = 0, maxC = 0, maxD = 0;
        double minValue = Double.MAX_VALUE;
        int minRow = 0, minCol = 0, minC = 0, minD = 0;

        int counts = 0;

        long start = System.nanoTime();
        for (int i = 59; i <= 60; i++) {
            for (int j = 1; j <= 50; j++) {
                threads[i][j] = new MyThread(i, j);
                counts += threads[i][j].run();
                System.out.println(i + " " + j + " end");
            }
        }

        long end = System.nanoTime();
        System.out.println("Run-time(ms): " + (end - start) / 1e6);
        System.out.println("MAX\n");
        System.out.println("c: " + maxC + '\t' + "d: " + maxD + '\t' + "row: " + maxRow + '\t' + "column: " + maxCol + '\t' + "maxValue: " + maxValue + '\n');
        System.out.println("MIN\n");
        System.out.println("c: " + minC + '\t' + "d: " + minD + '\t' + "row: " + minRow + '\t' + "column: " + minCol + '\t' + "maxValue: " + minValue + '\n');
        System.out.println("Total: " + counts);
    }
}
