package Assignment4;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReturnDigest extends Thread {
    private String filename;
    private byte[] digest;

    public ReturnDigest(String filename){
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

            digest = messageDigest.digest();

//            StringBuffer result = new StringBuffer(filename);
//            result.append(": ");
//            result.append(DatatypeConverter.printHexBinary(digest));
//
//            System.out.println(result);
        }catch (IOException | NoSuchAlgorithmException exception){
            System.err.println(exception);
        }
    }

    public byte[] getDigest(){
        return digest;
    }
}
