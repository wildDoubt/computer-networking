package Assignment3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StreamReaderWriterTest {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        try{
//            fileInputStream = new FileInputStream("test.txt");
            fileInputStream = new FileInputStream("testHangul.txt");
            inputStreamReader = new InputStreamReader(fileInputStream);
            outputStreamWriter = new OutputStreamWriter(System.out);

            char[] buffer = new char[512];
            int readCount = 0;

            while((readCount = inputStreamReader.read(buffer))!=-1){
                outputStreamWriter.write(buffer, 0, readCount);
            }
            fileInputStream.close();
            inputStreamReader.close();
            outputStreamWriter.close();
        }catch (IOException ioException){
            System.err.println(ioException);
        }

    }
}
