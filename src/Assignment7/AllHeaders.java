package Assignment7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AllHeaders {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.oreilly.com");
            URLConnection uc = u.openConnection();
            uc.getHeaderFields().forEach((key, values) ->
                    System.out.println(key + ": " + values.toString().substring(1, values.toString().length() - 1)));
            // 다른 방법
//            int index = 0;
//
//            while(uc.getHeaderField(index)!=null){
//                System.out.println(uc.getHeaderFieldKey(index)+": "+uc.getHeaderField(index));
//                index++;
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
