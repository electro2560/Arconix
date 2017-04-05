package com.songoda.arconix.API;

import com.songoda.arconix.Arconix;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by songoda on 4/4/2017.
 */
public class Update {


    Arconix plugin = Arconix.pl();

    public Update() {
        try {
            download();
        } catch (IOException e) {
            failed();
        }
    }

    public void download() throws IOException {
        System.out.println("===== Downloading Arconix =====");
        URL url = new URL("http://mcupdate.org/download.php?plugin=57");
        File to = new File("./plugins/Arconix.jar");
        File dwn = new File("./plugins/Arconix/Arconix.jar");

        if(to.exists()) {
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.100 Safari/534.30");

            ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
            FileOutputStream fOutStream = new FileOutputStream(dwn);

            fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fOutStream.close();
            rbc.close();
            System.out.println("===== Arconix Finished Downloading =====");
        } else {
            failed();
        }
    }

    private void failed() {
        System.out.println("========================================");
        System.out.println("Arconix failed to update!!!!");
        System.out.println("Please manually update it from here http://mcupdate.org/plugin?pl=57");
        System.out.println("========================================");
    }
}
