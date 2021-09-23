package Assignment4;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InstanceCallbackDigest implements Runnable {
    private String filename;
    private InstanceCallbackDigestUserInterface callback;

    public InstanceCallbackDigest(String filename,
                                  InstanceCallbackDigestUserInterface callback) {
        this.callback = callback;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
//            System.out.println("run");
            FileInputStream inputStream = new FileInputStream(filename);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);

            while (digestInputStream.read() != -1) ;
            digestInputStream.close();

            byte[] digest = messageDigest.digest();
            // instance method called
            callback.receiveDigest(digest);
        } catch (IOException | NoSuchAlgorithmException exception) {
            System.err.println(exception);
        }
    }
}
