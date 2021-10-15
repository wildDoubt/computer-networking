package Assignment6;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyAddress2 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String dottedQuad = inetAddress.getHostAddress();
        System.out.println("My address is " + dottedQuad);

        byte[] address = {107, 23, (byte) 216, (byte) 196};
        InetAddress inetAddress1 = InetAddress.getByAddress(address);
        InetAddress inetAddress1Named = InetAddress.getByAddress("named.com", address);
        System.out.println(inetAddress1);
        System.out.println(inetAddress1Named);
    }
}
