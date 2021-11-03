package Assignment8;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class Last24 {
    static Date max(Date d1, Date d2) {
        return d1.getTime() < d2.getTime() ? d2 : d1;
    }

    static Date findLastModifiedTime(URL url) {
        Date result = new Date(0);
        long lo = 0;
        long hi = new Date().getTime();
        int counts = 0;

        try {
            while (lo < hi) {
                URLConnection uc = url.openConnection();
                long mid = (lo + hi) / 2;
                uc.setIfModifiedSince(mid);
                int status = Integer.parseInt(uc.getHeaderField(0).substring(9, 12));
                if (status == 200) {
                    result = max(result, new Date(mid));
                    lo = mid+1;
                } else {
                    hi = mid;
                }
                counts++;
            }
            URLConnection uc = url.openConnection();
            uc.setIfModifiedSince(result.getTime());
            try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = r.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println();
            }
        } catch (IOException ignored) {
        }
        System.out.println("Count: "+counts);
        return result;
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.oreilly.com");

            System.out.println("Last modified time: " + findLastModifiedTime(url));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
