package com.songoda.arconix.API;

import com.songoda.arconix.Arconix;

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
    boolean hasUpdated = false;

    public Update() {
        try {
            if (hasUpdated) {
                download();
                hasUpdated = true;
            }
        } catch (IOException e) {
            failed();
        }
    }

    public void download() throws IOException {
        System.out.println("===== Downloading Arconix =====");
        URL url = new URL("http://mcupdate.org/download.php?plugin=57");
        File dwn = new File("./plugins/update/Arconix.jar");

            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.100 Safari/534.30");

            ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
            FileOutputStream fOutStream = new FileOutputStream(dwn);

            fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fOutStream.close();
            rbc.close();
            System.out.println("===== Arconix Finished Downloading =====");
    }

    private void failed() {
        System.out.println("========================================");
        System.out.println("Arconix failed to update!!!!");
        System.out.println("Please manually update it from here http://mcupdate.org/plugin?pl=57");
        System.out.println("========================================");
    }
}
