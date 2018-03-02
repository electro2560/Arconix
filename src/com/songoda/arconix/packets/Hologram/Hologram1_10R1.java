package com.songoda.arconix.packets.Hologram;

import com.songoda.arconix.Arconix;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Hologram1_10R1 implements Hologram {

    Arconix plugin = Arconix.pl();

    private List<EntityArmorStand> entityList;
    private ArrayList<Location> locations;

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public Hologram1_10R1() {
        reload();
    }

    public void spawnHolograms(Location location, List<String> holograms) {
        for (String line : holograms) {
            spawnHologram(location, line);
            location = new Location(location.getWorld(), location.getX(), location.getY() - .25, location.getZ());
            locations.add(location);
        }
    }

    public void spawnHologram(Location location, String line) { //THis should be called createHologram but i cant change it due to compatibility. gg
        EntityArmorStand entity = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ());
        entity.setCustomName(plugin.format().formatText(line));
        entity.setCustomNameVisible(true);
        entity.setInvisible(true);
        entity.setNoGravity(true);
        entity.setSmall(true);
        entity.setMarker(true);
        entity.setBasePlate(true);
        entityList.add(entity);
        locations.add(location);
        addHologram(location);
    }

    public void despawnHologram(Location location) { //THis should be called RemoveHologram but i cant change it due to compatibility. gg
        EntityArmorStand ent = null;
        for (EntityArmorStand entity : entityList) {
            Location loc = new Location(entity.world.getWorld(), entity.locX, entity.locY, entity.locZ, 0, 0);
            if (loc.equals(location)) {
                ent = entity;
            }
        }
        entityList.remove(ent);
        locations.remove(ent);
        delete(location);
    }

    public void delete(Location location) {
        Collection<org.bukkit.entity.Entity> nearbyEntite = location.getWorld().getNearbyEntities(location, 2, 2, 2);
        for (org.bukkit.entity.Entity entity : nearbyEntite) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                if (location.getX() == entity.getLocation().getX() && location.getY() == entity.getLocation().getY() && location.getZ() == entity.getLocation().getZ()) {
                    entity.remove();
                }
            }
        }
    }

    public void addHologram(Location location) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        for (EntityArmorStand entity : entityList) {
            Location loc = new Location(entity.world.getWorld(), entity.locX, entity.locY, entity.locZ, 0, 0);
            if (loc.equals(location)) {
                setPosition(entity, location);
                addEntityToWorld(nmsWorld, entity, location);
            }
        }
    }

    public void setPosition(Entity entity, Location location) {
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(entity);
        for(EntityHuman eh : entity.world.players) {
            if (eh instanceof  EntityPlayer) {
                EntityPlayer p = (EntityPlayer) eh;
                double distanceSquared = Math.pow(p.locX - entity.locX, 2) + Math.pow(p.locZ - entity.locZ, 2);
                if (distanceSquared < 8192 && p.playerConnection != null) {
                    p.playerConnection.sendPacket(teleportPacket);
                }
            }
        }
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity, Location location) {
        try {
        Chunk nmsChunk = nmsWorld.getChunkAtWorldCoords(nmsEntity.getChunkCoordinates());
        if (nmsChunk != null) {
            org.bukkit.Chunk chunk = nmsChunk.bukkitChunk;
            if (!chunk.isLoaded()) {
                chunk.load();
            }
        }

        delete(location);
        if (nmsEntity.getWorld() != null) {
            return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        } else {
            return false;
        }
        } catch (Exception e) {
        }
        return false;
    }

    public void reload() {
        locations = new ArrayList<>();
        entityList = new ArrayList<>();
        Map<Location, List<String>> map = plugin.holo.getHologramList();
        for (Map.Entry<Location, List<String>> entry : map.entrySet()) {
            spawnHolograms(entry.getKey(), entry.getValue());
        }
    }
}
