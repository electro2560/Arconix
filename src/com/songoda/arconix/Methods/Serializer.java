package com.songoda.arconix.Methods;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

/**
 * Created by songoda on 4/2/2017.
 */
public class Serializer {


    public String serializeLocation(Block b) {
        return serializeLocation(b.getLocation());
    }

    public String serializeLocation(Location location) {
        String w = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String str = "w:" + w + "x:" + x + "y:" + y + "z:" + z;
        return str.replace(".0", "").replace(".", "~");
    }

    public Location unserializeLocation(String str) {
        str = str.replace("y:", ":").replace("z:", ":").replace("w:", "").replace("x:", ":").replace("~", ".");
        List<String> args = Arrays.asList(str.split("\\s*:\\s*"));

        World world = Bukkit.getWorld(args.get(0));
        double x = Double.parseDouble(args.get(1)), y = Double.parseDouble(args.get(2)), z = Double.parseDouble(args.get(3));
        return new Location(world, x, y, z, 0, 0);
    }
}
