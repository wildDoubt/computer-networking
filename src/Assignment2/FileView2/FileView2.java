package Assignment2.FileView2;

import java.io.FileInputStream;
import java.io.IOException;

public class FileView2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

//        if(args.length != 1){
//            System.out.println("사용법: java FileView 파일이름");
//            System.exit(0);
//        }

        FileInputStream fileInputStream = null;

        try{
            fileInputStream = new FileInputStream("testArray.txt");
            int readCount;
            byte[] buffer = new byte[512];

            while((readCount=fileInputStream.read(buffer))!=-1){
                System.out.write(buffer, 0, readCount);
            }
        }catch (Exception exception){
            System.err.println(exception);
        }finally {
            try{
                fileInputStream.close();
            }catch (IOException ioException){

            }

        }
        long end = System.currentTimeMillis();
        System.out.println("Run-time: "+(end-start)/1000.0);
    }
}
