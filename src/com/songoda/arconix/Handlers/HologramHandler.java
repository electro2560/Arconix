package com.songoda.arconix.Handlers;

import com.songoda.arconix.Arconix;
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
public class HologramHandler {

    Arconix plugin = Arconix.pl();

    private int maxDistance = plugin.hologramFile.getConfig().getInt("max-view-distance");

    public HologramHandler() {

    }

    public Map<Location, List<String>> getHologramList() {
        Map<Location, List<String>> map = new HashMap<>();
        if (plugin.hologramFile.getConfig().contains("Holograms")) {
            ConfigurationSection cs = plugin.hologramFile.getConfig().getConfigurationSection("Holograms");
            for (String key : cs.getKeys(false)) {
                List<String> list = plugin.hologramFile.getConfig().getStringList("Holograms." + key + ".lines");
                Location location = plugin.serialize().unserializeLocation(plugin.hologramFile.getConfig().getString("Holograms." + key + ".location"));
                map.put(location, list);
            }
        }
        return map;
    }

    public void handleMovement(Location from, Location to, Player p) {
        if (plugin.packetLibrary.getHologramManager() != null) {
            if (to == null)
                to = from.clone().add(9, 9, 9);
            //if (((int) from.getX()) != ((int) to.getX()) || ((int) from.getY()) != ((int) to.getY())) {
            if (from.getChunk() != to.getChunk()) {
                for (Location loc : plugin.packetLibrary.getHologramManager().getLocations()) {
                    if (p.getLocation().getWorld().equals(loc.getWorld())) {
                        //if (loc.distance(p.getLocation()) >= maxDistance) {
                            plugin.packetLibrary.getHologramManager().hideHologram(loc, p);
                        //} else {
                            plugin.packetLibrary.getHologramManager().showHologram(loc, p);
                        //}
                    }
                }
            }
        }
    }

    public void deleteHologram(Player p, String name) {
        if (plugin.hologramFile.getConfig().contains("Holograms")) {
            Location location = plugin.serialize().unserializeLocation(plugin.hologramFile.getConfig().getString("Holograms." + name + ".location"));
            plugin.hologramFile.getConfig().set("Holograms." + name, null);
            plugin.packetLibrary.getHologramManager().despawnHologram(location);
            handleMovement(p.getLocation(), null, p);
        }
    }

    public void createHologram(Player p, String title, List<String> lines) {
        saveHologram(p.getLocation(), title, lines);
        plugin.packetLibrary.getHologramManager().spawnHolograms(p.getLocation(), lines);
        handleMovement(p.getLocation(), null, p);
    }

    public void createHologram(Player p, String title, String line) {
        List<String> lines = new ArrayList<>();
        lines.add(line);
        createHologram(p, title, lines);
        handleMovement(p.getLocation(), null, p);
    }

    public void saveHologram(Location location, String title, List<String> list) {
        String serial = plugin.serialize().serializeLocation(location);
        plugin.hologramFile.getConfig().set("Holograms." + title + ".location", serial);
        plugin.hologramFile.getConfig().set("Holograms." + title + ".lines", list);
    }
}
