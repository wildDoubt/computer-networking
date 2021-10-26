package Assignment7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class EncodingAwareSourceViewer {
    public static void main(String[] args) {
        String encoding = "ISO-8859-1";
        String line;
        try {
            URL url = new URL("http://ecampus.konkuk.ac.kr");
            URLConnection connection = url.openConnection();
            String contentType = connection.getContentType();
            int encodingStart = contentType.indexOf("charset=");

            // contentType에 charset이 있으면 encoding에 저장
            if (encodingStart != -1) encoding = contentType.substring(encodingStart + 8);

            System.out.println(contentType);
            System.out.println(encoding);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    encoding));

            while ((line = bufferedReader.readLine()) != null) System.out.println(line);

            bufferedReader.close();
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Server sent an encoding Java does not support: " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
