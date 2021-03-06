package com.songoda.arconix.api.handlers;

import com.songoda.arconix.api.ArconixAPI;
import com.songoda.arconix.api.methods.serialize.Serialize;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songoda on 4/2/2017.
 */
@SuppressWarnings("WeakerAccess")
public class HologramHandler {
    private ArconixAPI api = ArconixAPI.getApi();

    public HologramHandler() {
    }

    public Map<Location, List<String>> getHologramList() {
        Map<Location, List<String>> map = new HashMap<>();
        if (api.hologramFile.getConfig().contains("Holograms")) {
            ConfigurationSection cs = api.hologramFile.getConfig().getConfigurationSection("Holograms");
            for (String key : cs.getKeys(false)) {
                List<String> list = api.hologramFile.getConfig().getStringList("Holograms." + key + ".lines");
                Location location = Serialize.getInstance().unserializeLocation(api.hologramFile.getConfig().getString("Holograms." + key + ".location"));
                map.put(location, list);
            }
        }
        return map;
    }

    public void stream(Chunk chunk) {
        if (chunk == null)
            return;
        for (Location loc : api.packetLibrary.getHologramManager().getLocations()) {
            if (loc.getChunk().equals(chunk)) {
                api.packetLibrary.getHologramManager().addHologram(loc);
            }
        }
    }

    public void deleteHologram(Player p, String name) {
        if (name == null || name.equals(""))
            return;
        if (api.hologramFile.getConfig().contains("Holograms")) {
            Location location = Serialize.getInstance().unserializeLocation(api.hologramFile.getConfig().getString("Holograms." + name + ".location"));
            api.hologramFile.getConfig().set("Holograms." + name, null);
            api.packetLibrary.getHologramManager().despawnHologram(location);
            stream(location.getChunk());
        }
    }

    public void createHologram(Player p, String title, List<String> lines) {
        if (p == null || title == null || title.equals("") || lines == null)
            return;
        saveHologram(p.getLocation(), title, lines);
        api.packetLibrary.getHologramManager().spawnHolograms(p.getLocation(), lines);
        stream(p.getLocation().getChunk());
    }

    public void createHologram(Player p, String title, String line) {
        if (p == null || title == null || title.equals("") || line == null)
            return;
        List<String> lines = new ArrayList<>();
        lines.add(line);
        createHologram(p, title, lines);
        stream(p.getLocation().getChunk());
    }

    public void saveHologram(Location location, String title, List<String> list) {
        if (location == null || title == null || title.equals("") || list == null)
            return;
        String serial = Serialize.getInstance().serializeLocation(location);
        api.hologramFile.getConfig().set("Holograms." + title + ".location", serial);
        api.hologramFile.getConfig().set("Holograms." + title + ".lines", list);
    }
}