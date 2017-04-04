package com.songoda.arconix;

import com.songoda.arconix.API.MCUpdate;
import com.songoda.arconix.Handlers.HologramHandler;
import com.songoda.arconix.Methods.*;
import com.songoda.arconix.Utils.ConfigWrapper;
import com.songoda.arconix.packets.PacketLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by songoda on 3/16/2017.
 */
public class Arconix extends JavaPlugin implements Listener {

    //Github test

    public boolean v1_7 = Bukkit.getServer().getClass().getPackage().getName().contains("1_7");
    public boolean v1_8 = Bukkit.getServer().getClass().getPackage().getName().contains("1_8");

    public String serverVersion = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    public PacketLibrary packetLibrary;

    public HologramHandler holo;

    private Formatting format = new Formatting();

    public void onDisable() {
        hologramFile.saveConfig();
    }

    public void onEnable() {
        loadHoloFile();
        holo = new HologramHandler();
        packetLibrary = new PacketLibrary();
        packetLibrary.setupPackets();

        try {
            MCUpdate update = new MCUpdate(this, true);
        } catch (IOException e) {
            Bukkit.getLogger().info("Arconix Failed initialize MCUpdate");
        }
    }

    public ConfigWrapper hologramFile = new ConfigWrapper(this, "", "holograms.yml");

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Arconix")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                sender.sendMessage(format.formatText("&9[&5Arconix&9]&7 " + this.getDescription().getVersion() + " Created by &5&l&oBrianna &7& &eTheCrytalStar&7."));
            } else if (args[0].equalsIgnoreCase("actionbar")) {

                String text = "";
                for (int i = 1; i < args.length; i++) {
                    text += " " + args[i];
                }
                packetLibrary.getActionBarManager().sendActionBar((Player) sender, text);
            } else if (args[0].equalsIgnoreCase("hologram")) {

                String text = "";
                for (int i = 2; i < args.length; i++) {
                    text += " " + args[i];
                }

                Location location = ((Player) sender).getLocation().add(0.5, 0, 0.5);

                holo.createHologram(location, args[1], text);
            } else if (args[0].equalsIgnoreCase("show")) {
                packetLibrary.getHologramManager().showHolograms((Player) sender);
            }
            //Added Title command to test sub titles.
            else if (args[0].equalsIgnoreCase("ptitle")) {
                packetLibrary.getTitleManager().sendTitle((Player) sender, "&cA Title", 20, 120, 20);
                packetLibrary.getTitleManager().sendSubitle((Player) sender, "&cA Title", 20, 120, 20);
            }

            //coneEffect(((Player)sender));
        }
        return true;
    }

    private void loadHoloFile() {
        hologramFile.getConfig().options().copyDefaults(true);
        hologramFile.saveConfig();
    }

    public void coneEffect(final Player player) {
        BukkitTask redstone = new BukkitRunnable() {
            double phi = 0;

            public void run() {
                phi = phi + Math.PI / 8;
                double x, y, z;

                Location location1 = new Location(player.getWorld(), -168.5, 98.0, 185.5);
                for (double t = 0; t <= 5 * Math.PI; t = t + Math.PI / 16) {
                    for (double i = 0; i <= 1; i = i + 1) {
                        x = 0.4 * (2 * Math.PI - t) * 0.5 * cos(t + phi + i * Math.PI);
                        y = 0.5 * t;
                        z = 0.4 * (2 * Math.PI - t) * 0.5 * sin(t + phi + i * Math.PI);
                        location1.add(x, y, z);
                        packetLibrary.getParticleManager().broadcastParticle(location1, 0, 0, 0, 0, "REDSTONE", 1);
                        location1.subtract(x, y, z);
                    }

                }

                if (phi > 10 * Math.PI) {
                    this.cancel();
                }
            }
        }.runTaskTimer(this, 0, 2);
    }

    public Formatting format() {
        return new Formatting();
    }

    public GUI getGUI() {
        return new GUI();
    }

    public APlayer getPlayer(Player player) {
        return new APlayer(player);
    }

    public Serializer serialize() {
        return new Serializer();
    }

    public Maths doMath() {
        return new Maths();
    }

    public static Arconix pl() {
        return (Arconix) Bukkit.getServer().getPluginManager().getPlugin("Arconix");
    }
}
