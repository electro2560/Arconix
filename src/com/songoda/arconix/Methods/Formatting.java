package com.songoda.arconix.Methods;

import com.songoda.arconix.Arconix;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by songoda on 4/2/2017.
 */
public class Formatting {

    Arconix plugin = Arconix.pl();

    public String formatEconomy(double amt) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(amt);
    }

    public String formatTitle(String text) {
        if (plugin.v1_7 || plugin.v1_8) {
            if(text.length() > 32)
                text = text.substring(0,29) + "...";
        }
        text = formatText(text);
        return text;
    }

    public String formatText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String readableTime(Long time) {
        return String.format("%d hour(s), %d min(s), %d sec(s)", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }

}
