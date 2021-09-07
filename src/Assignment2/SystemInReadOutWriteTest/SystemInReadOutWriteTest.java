package Assignment2.SystemInReadOutWriteTest;

import java.io.*;

public class SystemInReadOutWriteTest {
    public static void main(String[] args){
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        int input;
        try{
            input = inputStream.read();
            System.out.println(input);
            outputStream.write(input);
            outputStream.flush();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
