package Assignment3;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestThread extends Thread {
    private String filename;

    public DigestThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);

            while(digestInputStream.read() != -1);
            digestInputStream.close();

            byte[] digest = messageDigest.digest();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }catch (IOException | NoSuchAlgorithmException exception){
            System.err.println(exception);
        }
    }

    public static void main(String[] args) {
        for (String filename : args) {
            Thread thread = new DigestThread(filename);
            thread.start();
        }
    }
}
