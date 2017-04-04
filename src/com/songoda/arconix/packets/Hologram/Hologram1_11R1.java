package com.songoda.arconix.packets.Hologram;

import com.songoda.arconix.Arconix;
import com.songoda.arconix.Handlers.HologramHandler;
import net.minecraft.server.v1_11_R1.EntityArmorStand;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hologram1_11R1 implements Hologram {

    Arconix plugin = Arconix.pl();

    private List<EntityArmorStand> entityList;

    public Hologram1_11R1() {
        entityList = new ArrayList<>();
        Map<Location, List<String>> map = plugin.holo.getHologramList();
        for (Map.Entry<Location, List<String>> entry : map.entrySet()) {
            spawnHolograms(entry.getKey(), entry.getValue());
        }
    }

    public void spawnHolograms(Location location, List<String> holograms) {
        for (String line : holograms) {
            spawnHologram(location, line);
            location = new Location(location.getWorld(), location.getX(), location.getY() - .25, location.getZ());
        }
    }

    public void spawnHologram(Location location, String line) {
        EntityArmorStand entity = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(),
                location.getX(), location.getY(), location.getZ());
        entity.setCustomName(plugin.format().formatText(line));
        entity.setCustomNameVisible(true);
        entity.setInvisible(true);
        entity.setNoGravity(false);
        entity.setSmall(true);
        entity.setMarker(true);
        entityList.add(entity);
        //location = location.subtract(0, getLineSpread(), 0);
    }

    public void showHolograms(Player p) {
        for (EntityArmorStand entity : entityList) {
            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entity);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
