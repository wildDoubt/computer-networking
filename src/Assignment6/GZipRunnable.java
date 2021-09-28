package Assignment6;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZipRunnable implements Runnable {

    private final File input;

    public GZipRunnable(File input) {
        this.input = input;
    }

    @Override
    public void run() {
        // 이미 압축된 파일이 아닌 경우
        if (!input.getName().endsWith(".gz")) {
            // 파일을 생성
            File output = new File(input.getParent(), input.getName() + ".gz");
            // 이미 압축된 파일이 존재하는 경우는 제외
            if (!output.exists()) {
                try (
                        InputStream in = new BufferedInputStream(new FileInputStream(input));
                        OutputStream out = new BufferedOutputStream(
                                new GZIPOutputStream(
                                        new FileOutputStream(output)))
                ) {
                    int b;
                    while((b=in.read())!=-1) out.write(b);
                    out.flush();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
}
