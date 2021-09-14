package Assignment3;

import Utils.Utils;

import java.io.*;

public class FileStreamCopyBuffered {
    static int size;
    public static void FileStreamCopySingleByte(String[] args){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            fileInputStream = new FileInputStream("testArray.txt");
            fileOutputStream = new FileOutputStream("test.txt");
            int readCount;
            while((readCount=fileInputStream.read())!=-1){
                fileOutputStream.write(readCount);
            }
        }catch (Exception exception){
            System.err.println(exception);
        }finally {
            try{
                fileInputStream.close();
            }catch (IOException ioException){

            }
            try{
                fileOutputStream.close();
            }catch (IOException ioException){

            }
        }
    }

    public static void FileStreamCopyByteArray(String[] args){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            fileInputStream = new FileInputStream("testArray.txt");
            fileOutputStream = new FileOutputStream("test.txt");

            fileInputStream = new FileInputStream("testArray.txt");
            fileOutputStream = new FileOutputStream("test.txt");

            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            int readCount;
            byte[] buffer = new byte[512];

            // bufferInput -> bufferOutput
            while((readCount=bufferedInputStream.read(buffer))!=-1){
                bufferedOutputStream.write(buffer, 0, readCount);
                size+=readCount;
            }
        }catch (Exception exception){
            System.err.println(exception);
        }finally {
            try{
                fileInputStream.close();
            }catch (IOException ioException){

            }
            try{
                fileOutputStream.close();
            }catch (IOException ioException){

            }
        }
    }

    public static void main(String[] args) {
//        if(args.length != 2){
//            System.out.println("사용법: java FileStreamCopy 파일이름1 파일이름2");
//            System.exit(0);
//        }
        Utils utils = new Utils();

        long time1 = utils.getElapsedTime(()->FileStreamCopySingleByte(args));
        long time2 = utils.getElapsedTime(()->FileStreamCopyByteArray(args));
        System.out.println("File size: "+size/1e6+"mb");
        System.out.println(time1+"ms");
        System.out.println(time2+"ms");
    }
}
