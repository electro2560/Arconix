package com.songoda.arconix.packets.Hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface Hologram
{

    void spawnHolograms(Location location, List<String> holograms);

    void spawnHologram(Location location, String line);

    void showHolograms(Player p);

}
