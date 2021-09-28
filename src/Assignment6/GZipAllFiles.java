package Assignment6;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GZipAllFiles {
    public final static int THREAD_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

        for(String filename:args){
            File f = new File(filename);
            if(f.exists()){
                if(f.isDirectory()){
                    File[] files = f.listFiles();
                    if(files==null) continue;
                    for (File file : files) {
                        if (!file.isDirectory()) {
                            Runnable task = new GZipRunnable(file);
                            pool.submit(task);
                        }
                    }
                }else{
                    Runnable task = new GZipRunnable(f);
                    pool.submit(task);
                }
            }
        }
        pool.shutdown();
    }
}
