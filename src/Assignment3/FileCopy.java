package Assignment3;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) {
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try{
            fileReader = new FileReader("testArray.txt");
            fileWriter = new FileWriter("test.txt");

            char[] buffer = new char[512];
            int readCount = 0;

            while((readCount = fileReader.read(buffer))!=-1){
                fileWriter.write(buffer, 0, readCount);
            }
            System.out.println("파일을 복사하였습니다.");
            fileReader.close();
            fileWriter.close();
        }catch (IOException ioException){
            System.err.println(ioException);
        }
    }
}
