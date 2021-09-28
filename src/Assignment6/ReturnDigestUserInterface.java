package Assignment6;

import javax.xml.bind.DatatypeConverter;

public class ReturnDigestUserInterface {
    public static void main(String[] args) throws InterruptedException {
        ReturnDigest[] returnDigests = new ReturnDigest[args.length];
        for(int i = 0;i<args.length;i++){
            returnDigests[i] = new ReturnDigest(args[i]);
            returnDigests[i].start();
        }
        for(int i = 0;i<args.length;i++) {
            System.out.println("join");
            returnDigests[i].join();
            StringBuilder result = new StringBuilder(args[i]);
            result.append(": ");
            byte[] digest = returnDigests[i].getDigest();
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
        System.out.println("main ended");
    }
}
