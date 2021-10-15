package Midterm;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

public class FindMinMaxTask implements Callable<double[]> {
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
        int currRow=0, currCol=0;
        BufferedReader bufferedReader;
        String inputLine;
        String baseURL = "https://home.konkuk.ac.kr/~leehw/Site/nptest/";
        URL url;
        if (c >= 26) url = new URL(baseURL + "file%20" + "[c=" + c + "]_[d=" + d + "].txt");
        else url = new URL(baseURL + "file%20" + "<c=" + c + ">_<d=" + d + ">.txt");

        StringTokenizer stringTokenizer;

        try {
            if(!option) bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            else  bufferedReader = new BufferedReader(new FileReader("result/result_" + c + "_" + d + ".txt"));

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(inputLine, "\t");
                while (stringTokenizer.hasMoreElements()) {
                    double temp = Double.parseDouble(stringTokenizer.nextToken());
                    totalSum+=temp;
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
        } catch (FileNotFoundException ignored) {
            missingFiles++;
        }
    }

    @Override
    public double[] call() throws Exception {
//        for (int i = startC; i < endC; i++) {
//            for (int j = 1; j <= 60; j++) {
//                calc(i, j);
//
//            }
//        }
        int indexC = startC;
        int indexD = startD;
        while (indexC <= endC) {
            if(indexC==endC && indexD>=endD) break;
            calc(indexC, indexD);
//            System.out.println("pair: " + indexC + " " + indexD);
            indexD++;
            if (indexD > 60) {
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
