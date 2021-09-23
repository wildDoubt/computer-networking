package Assignment4;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CallbackDigest implements Runnable{
    private String filename;

    public CallbackDigest(String filename){
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
            // static method called
            CallbackDigestUserInterface.receiveDigest(digest, filename);
        }catch (IOException | NoSuchAlgorithmException exception){
            System.err.println(exception);
        }
    }
}
