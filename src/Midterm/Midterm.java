package Midterm;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

class MyThread extends Thread {
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

    @Override
    public void run() {
        try {
            String baseURL = "https://home.konkuk.ac.kr/~leehw/Site/nptest/";
            URL url;
            if (this.c >= 26) {
                url = new URL(baseURL + "file%20" + "[c=" + this.c + "]_[d=" + this.d + "].txt");
            } else {
                url = new URL(baseURL + "file%20" + "<c=" + this.c + ">_<d=" + this.d + ">.txt");
            }
//            System.out.println(url);


            StringTokenizer stringTokenizer;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
//                FileOutputStream fileOutputStream = new FileOutputStream("result/result_" + this.c + "_" + this.d + ".txt");
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                while ((inputLine = bufferedReader.readLine()) != null) {
//                    bufferedWriter.write(inputLine+'\n');
                    System.out.println(c+" "+d);
                    stringTokenizer = new StringTokenizer(inputLine, "\t");
                    while (stringTokenizer.hasMoreElements()) {
                        double temp = Double.parseDouble(stringTokenizer.nextToken());
                        if (temp > maxValue) {
                            maxRow = currRow;
                            maxCol = currCol;
                            maxValue = temp;
                        }
                        if (temp < minValue) {
                            minRow = currRow;
                            minCol = currCol;
                            minValue = temp;
                        }
                        currCol++;
                    }
                    currCol = 0;
                    currRow++;
                }
            } catch (FileNotFoundException ignored) {
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
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
        MyThread[][] threads = new MyThread[50][60];
        double maxValue = Double.MIN_VALUE;
        int maxRow = 0, maxCol = 0, maxC = 0, maxD = 0;
        double minValue = Double.MAX_VALUE;
        int minRow = 0, minCol = 0, minC = 0, minD = 0;

        long start = System.nanoTime();
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < 50; j++) {
                threads[i][j] = new MyThread(i, j);
                threads[i][j].start();
            }
        }
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < 50; j++) {
                threads[i][j].join();
//                System.out.println(i+" "+j+" joined");
                try {
                    double[] maxResult = threads[i][j].getMaxValue();
                    double[] minResult = threads[i][j].getMinValue();
                    if (maxValue < maxResult[2]) {
                        maxValue = maxResult[2];
                        maxRow = (int) maxResult[0];
                        maxCol = (int) maxResult[1];
                        maxC = i;
                        maxD = j;
                    }
                    if (minValue > minResult[2]) {
                        minValue = minResult[2];
                        minRow = (int) minResult[0];
                        minCol = (int) minResult[1];
                        minC = i;
                        minD = j;
                    }
                } catch (NullPointerException ignored) {
                }
            }
        }

        long end = System.nanoTime();
        System.out.println("Run-time(ms): " + (end - start) / 1e6);
        System.out.println("MAX\n");
        System.out.println("c: " + maxC + '\t' + "d: " + maxD + '\t' + "row: " + maxRow + '\t' + "column: " + maxCol + '\t' + "maxValue: " + maxValue + '\n');
        System.out.println("MIN\n");
        System.out.println("c: " + minC + '\t' + "d: " + minD + '\t' + "row: " + minRow + '\t' + "column: " + minCol + '\t' + "maxValue: " + minValue + '\n');
    }
}
