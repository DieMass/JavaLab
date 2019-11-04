package die.mass;

import main.java.die.mass.ImageLoaderDB;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import main.java.die.mass.models.Image;

import javax.imageio.ImageIO;

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
    private static ImageLoaderDB imageLoaderDB = ImageLoaderDB.create();

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    public static boolean download(String url1, String path) {
        try {
            URL url = new URL(url1);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf)))
            {
                out.write(buf,0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            File image = new File(path);
            FileOutputStream fos = new FileOutputStream(image);
            fos.write(response);
            fos.close();
            imageLoaderDB.save(new Image(null,image.getName(),image.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void run() {
        if (!mode.equals("multi-thread") && !mode.equals("single-thread")) {
            System.err.println("Ancorrect parameter in mode");
            System.exit(-1);
        }
        System.out.println(path);
        path = path.charAt(path.length() - 1) == '/' ? path : path + '/';
        String[] url = urls.split(";");
        if (mode.equals("single-thread")) count = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < url.length; i++) {
            int a = i;
            executorService.submit(() -> {
                download(url[a], path + '/' + url[a].split("(/)")[url[a].split("(/)").length - 1]);
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

