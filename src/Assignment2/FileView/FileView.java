package Assignment2.FileView;

import java.io.FileInputStream;
import java.io.IOException;

public class FileView {
    public static void main(String[] args) {
        long start = System.nanoTime();

        if(args.length != 1){
            System.out.println("사용법: java FileView 파일이름");
            System.exit(0);
        }

        FileInputStream fileInputStream = null;

        try{
            fileInputStream = new FileInputStream(args[0]);
            int i;
            while((i=fileInputStream.read())!=-1){
                System.out.write(i);
            }
        }catch (Exception exception){
            System.err.println(exception);
        }finally {
            try{
                fileInputStream.close();
            }catch (IOException ioException){

            }

        }
        long end = System.nanoTime();
        System.out.println("Run-time(ms): "+(end-start)/1e6);
    }
}
