package com.songoda.arconix.Utils;

import com.songoda.arconix.Arconix;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Kiran Hart on 4/13/2017.
 */
public class RegionUtils {
    private Arconix arconix = Arconix.pl();

    public RegionUtils() {

    }

    public void createNewRegion(Player p, String regionName, Location location1, Location location2) {

        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".regionname", regionName.toLowerCase());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.world", location1.getWorld().getName());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.x", location1.getBlockX());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.y", location1.getBlockY());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.z", location1.getBlockZ());

        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.world", location2.getWorld().getName());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.x", location2.getBlockX());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.y", location2.getBlockY());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.z", location2.getBlockZ());
        arconix.regionFile.saveConfig();
        p.sendMessage(arconix.format().formatText("&eYou created a new region by the name of &6" + regionName.toLowerCase()));
        arconix.selectedLocationOne.remove(p);
        arconix.selectedLocationTwo.remove(p);
        if (arconix.inSelectionMode.contains(p)) {
            arconix.inSelectionMode.remove(p);
        }
    }

    public void createNewRegion(String regionName, Location location1, Location location2) {

        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".regionname", regionName.toLowerCase());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.world", location1.getWorld().getName());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.x", location1.getBlockX());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.y", location1.getBlockY());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location1.z", location1.getBlockZ());

        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.world", location2.getWorld().getName());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.x", location2.getBlockX());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.y", location2.getBlockY());
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase() + ".location2.z", location2.getBlockZ());
        arconix.regionFile.saveConfig();
    }

    public void removeRegion(Player p, String regionName) {
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase(), null);
        arconix.regionFile.saveConfig();
        p.sendMessage(arconix.format().formatText("&eYou deleted the region by the name of &6" + regionName.toLowerCase()));
    }

    public void removeRegion(String regionName) {
        arconix.regionFile.getConfig().set("regions." + regionName.toLowerCase(), null);
        arconix.regionFile.saveConfig();
    }

    public boolean regionExist(String regionName) {
        return arconix.regionFile.getConfig().contains("regions." + regionName.toLowerCase());
    }

    public ArrayList<String> getAllRegions() {
        ArrayList<String> regions = new ArrayList<>();
        ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions");
        if (section != null) {
            for (String ids : section.getKeys(false)) {
                regions.add(ids);
            }
        }
        return regions;
    }
}
