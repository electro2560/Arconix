package com.songoda.arconix.Methods;

import com.songoda.arconix.Arconix;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;

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

}
