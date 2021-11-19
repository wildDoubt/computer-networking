package Assignment8;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FormPoster {
    public URL url;

    private String query;

    public FormPoster(URL url){
        if(!url.getProtocol().toLowerCase().startsWith("http")){
            throw new IllegalArgumentException(
                    "Posting only works for http URLs");
        }
        this.url = url;
        query = "";
    }

    public void add(String name, String value){
        query+=name+"="+value+"&";
    }

    public URL getURL(){
        return this.url;
    }

    public InputStream post() throws IOException{
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Connection", "close");

        try(OutputStreamWriter out
                 = new OutputStreamWriter(uc.getOutputStream(),"UTF-8")){
            out.write(query);
            out.write("\r\n");
            out.flush();
        }

        return uc.getInputStream();
    }

    public static void main(String[] args) {
        URL url;
        try {
            url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        FormPoster poster = new FormPoster(url);
        poster.add("name", "Elliotte Rusty Harold");
        poster.add("email", "elharo@ibiblio.org");
        poster.add("want to", "go home");

        try(InputStream in = poster.post()){
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = r.readLine())!=null){
                System.out.println(line);
            }
            System.out.println();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
