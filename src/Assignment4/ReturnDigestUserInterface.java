package Assignment4;

import javax.xml.bind.DatatypeConverter;

public class ReturnDigestUserInterface {
    public static void main(String[] args) {
        for(String filename:args){
            ReturnDigest returnDigest = new ReturnDigest(filename);
            returnDigest.start();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            byte[] digest = returnDigest.getDigest();
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
    }
}
