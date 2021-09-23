package Assignment4;

import javax.xml.bind.DatatypeConverter;

public class CallbackDigestUserInterface {

    public static void receiveDigest(byte[] digest, String name){
        StringBuffer result = new StringBuffer(name);
        result.append(": ");
        result.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(result);
    }

    public static void main(String[] args) {
        for(String filename: args){
            CallbackDigest callbackDigest = new CallbackDigest(filename);
            Thread thread = new Thread(callbackDigest);
            thread.start();
        }
    }
}
