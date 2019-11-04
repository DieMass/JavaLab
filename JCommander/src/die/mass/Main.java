package die.mass;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Parameters(separators = "=")
public class Main {
    @Parameter(names = {"--mode"})
    private String mode = "single-thread";
    @Parameter(names = {"--files"})
    private String urls;
    @Parameter(names = {"--folder"})
    private String path;
    @Parameter(names = {"--count"})
    private int count = 1;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    public static boolean download(String url, String path) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(path));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    public void run() {
        if (!mode.equals("multi-thread") && !mode.equals("single-thread")) {
            System.err.println("Ancorrect parameter in mode");
            System.exit(-1);
        }
        System.out.println(path);
        path = path.charAt(path.length() - 1) == '\\' ? path : path + '\\';
        String[] url = urls.split(";");
        if (mode.equals("single-thread")) count = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < url.length; i++) {
            int a = i;
            executorService.submit(() -> {
                download(url[a], path + a + ".jpg");
                System.out.println(path + url[a]);
            });
        }
//        System.out.println("mode " + mode);
//        System.out.println("folder " + path);
//        System.out.println("URLs " + urls);
//        System.out.println("count " + count);
        executorService.shutdown();
    }
}
