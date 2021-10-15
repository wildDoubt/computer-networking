package Assignment6;

import javax.xml.bind.DatatypeConverter;

public class Polling {
    public static void main(String[] args) throws InterruptedException {
        // 쓰레드를 전부 먼저 start하고 나중에 값을 가져오면 되지 않을까?
        ReturnDigest[] digests = new ReturnDigest[args.length];

        for (int i = 0; i < args.length; i++) {
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            byte[] digest = digests[i].getDigest();
            // polling
            if (digest == null) {
                i--;
                Thread.yield();
                continue;
            }
            StringBuffer result = new StringBuffer(args[i]);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
//        for (int i = 0; i < args.length; i++) {
////            System.out.println("main " + i);
//            byte[] digest = digests[i].getDigest();
//            if (digest != null) {
//                StringBuffer result = new StringBuffer(args[i]);
//                result.append(": ");
//                result.append(DatatypeConverter.printHexBinary(digest));
//                System.out.println(result);
//                break;
//            }
//        }
    }
}
