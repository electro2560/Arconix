package com.songoda.arconix.Handlers;

import com.songoda.arconix.Arconix;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songoda on 4/2/2017.
 */
public class HologramHandler {

    Arconix plugin = Arconix.pl();

    public HologramHandler() {

    }

    public Map<Location, List<String>> getHologramList() {
        Map<Location, List<String>> map = new HashMap<>();
        if (plugin.hologramFile.getConfig().contains("Holograms")) {
            ConfigurationSection cs = plugin.hologramFile.getConfig().getConfigurationSection("Holograms");
            for (String key : cs.getKeys(false)) {
                List<String> list = plugin.hologramFile.getConfig().getStringList("Holograms." + key + ".lines");
                map.put(plugin.serialize().unserializeLocation(plugin.hologramFile.getConfig().getString("Holograms." + key + ".location")), list);
            }
        }
        return map;
    }

    public void createHologram(Location location, String title, List<String> lines) {
        saveHologram(location, title, lines);
        plugin.packetLibrary.getHologramManager().spawnHolograms(location, lines);
    }

    public void createHologram(Location location, String title, String line) {
        List<String> lines = new ArrayList<>();
        lines.add(line);
        createHologram(location, title, lines);
    }

    public void saveHologram(Location location, String title, List<String> list) {
        String serial = plugin.serialize().serializeLocation(location);
        plugin.hologramFile.getConfig().set("Holograms." + title + ".location", serial);
        plugin.hologramFile.getConfig().set("Holograms." + title + ".lines", list);
    }
}
