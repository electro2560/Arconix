package com.songoda.arconix.packets.Particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Particle
{

    void displayParticle(Player player, Location loc, float x, float y, float z, int speed, String effect, int amount);

    void broadcastParticle(Location loc, float x, float y, float z, int speed, String effect, int amount);

}
