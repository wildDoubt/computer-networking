package Assignment7;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BinarySaver {
    public static void main(String[] args) {
        try{
            URL root = new URL("http://ecampus.konkuk.ac.kr/ilos/images/osms/konkuk/ko/tit_system.png");
            saveBinaryFile(root);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void saveBinaryFile(URL u) throws IOException {
        URLConnection connection = u.openConnection();
        String contentType = connection.getContentType();
        int contentLength = connection.getContentLength();
        System.out.println("contentLength: " + contentLength);
        if (contentType.startsWith("text/") || contentLength == -1) {
            throw new IOException("This is not a binary file.");
        }

        try (InputStream raw = connection.getInputStream()) {
            InputStream in = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int offset = 0;

            while (offset < contentLength) {
                int bytesRead = in.read(data, offset, data.length - offset);
                if (bytesRead == -1) break;
                offset += bytesRead;
            }

            if (offset != contentLength) {
                throw new IOException("Only read " + offset
                        + " bytes; Expected " + contentLength + " bytes");
            }

            String filename = u.getFile();
            filename = filename.substring(filename.lastIndexOf('/')+1);
            try(FileOutputStream fout = new FileOutputStream(filename)){
                fout.write(data);
                fout.flush();
            }
        }
    }
}
