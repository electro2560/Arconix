package com.songoda.arconix.Events;

import com.songoda.arconix.Arconix;
import com.songoda.arconix.Events.Custom.RegionEnterEvent;
import com.songoda.arconix.Events.Custom.RegionExitEvent;
import com.songoda.arconix.Events.Custom.RegionMoveInEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;


public class RegionEvents implements Listener {

    private Arconix arconix = Arconix.pl();


    private int x1;
    private int y1;
    private int z1;
    private String world1;

    private int x2;
    private int y2;
    private int z2;
    private String world2;

    private int playerX;
    private int playerY;
    private int playerZ;

    private void runPresetAffects(Player p, String regionID, String presets) {

        ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions." + regionID.toLowerCase() + ".presets");
        if (section != null) {

            String type = arconix.regionFile.getConfig().getString("presets." + presets + ".type");

            switch (type) {

                case "title":
                    int fadeIn = arconix.regionFile.getConfig().getInt("presets." + presets + ".fadeIn");
                    int stay = arconix.regionFile.getConfig().getInt("presets." + presets + ".stay");
                    int fadeOut = arconix.regionFile.getConfig().getInt("presets." + presets + ".fadeOut");
                    String msg = arconix.regionFile.getConfig().getString("presets." + presets + ".msg");
                    arconix.packetLibrary.getTitleManager().sendTitle(p, msg, fadeIn, stay, fadeOut);
                    break;
                case "subtitle":
                    int subFadeIn = arconix.regionFile.getConfig().getInt("presets." + presets + ".fadeIn");
                    int subStay = arconix.regionFile.getConfig().getInt("presets." + presets + ".stay");
                    int subFadeOut = arconix.regionFile.getConfig().getInt("presets." + presets + ".fadeOut");
                    String subMsg = arconix.regionFile.getConfig().getString("presets." + presets + ".msg");
                    arconix.packetLibrary.getTitleManager().sendSubitle(p, subMsg, subFadeIn, subStay, subFadeOut);
                    break;
                case "actionbar":
                    String actionbarMsg = arconix.regionFile.getConfig().getString("presets." + presets + ".msg");
                    arconix.packetLibrary.getActionBarManager().sendActionBar(p, arconix.format().formatText(actionbarMsg));
                    break;
                case "ping":
                    String ping = arconix.regionFile.getConfig().getString("presets." + presets + ".msg").replace("{ping}", String.valueOf(arconix.packetLibrary.getPingManager().getPing(p)));
                    p.sendMessage(arconix.format().formatText(ping));
                    break;
                case "text":
                    String txt = arconix.regionFile.getConfig().getString("presets." + presets + ".msg");
                    p.sendMessage(arconix.format().formatText(txt));
                    break;
                default:
                    break;
            }


        }
    }

    private boolean isInCuboid(Location l, Location c1, Location c2) {
        double maxx = Math.max(c1.getX(), c2.getX());
        double maxy = Math.max(c1.getY(), c2.getY());
        double maxz = Math.max(c1.getZ(), c2.getZ());
        Vector max = new Vector(maxx, maxy, maxz);

        double minx = Math.min(c1.getX(), c2.getX());
        double miny = Math.min(c1.getY(), c2.getY());
        double minz = Math.min(c1.getZ(), c2.getZ());
        Vector min = new Vector(minx, miny, minz);

        return l.toVector().isInAABB(min, max);
    }

    //Preset Editing
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (!arconix.editingPreset.containsKey(p)) {
            return;
        }

        e.setCancelled(true);

        switch (arconix.editingPreset.get(p).toLowerCase()) {

            case "title":
                String chatSplit[] = e.getMessage().split(" ");
                int fadeIn = Integer.parseInt(chatSplit[0]);
                int stay = Integer.parseInt(chatSplit[1]);
                int fadeOut = Integer.parseInt(chatSplit[2]);
                String msg = "";
                for (int i = 3; i < chatSplit.length; i++) {
                    msg = msg + " " + chatSplit[i];
                }
                String presetName = arconix.editingPresetName.get(p);
                arconix.regionFile.getConfig().set("presets." + presetName + ".fadeIn", fadeIn);
                arconix.regionFile.getConfig().set("presets." + presetName + ".stay", stay);
                arconix.regionFile.getConfig().set("presets." + presetName + ".fadeOut", fadeOut);
                arconix.regionFile.getConfig().set("presets." + presetName + ".msg", msg);
                arconix.regionFile.saveConfig();
                p.sendMessage(arconix.format().formatText("&eYou successfully edited the &b" + presetName + " &epreset!"));
                arconix.editingPreset.remove(p);
                arconix.editingPresetName.remove(p);
                break;
            case "subtitle":
                String schatSplit[] = e.getMessage().split(" ");
                int sfadeIn = Integer.parseInt(schatSplit[0]);
                int sstay = Integer.parseInt(schatSplit[1]);
                int sfadeOut = Integer.parseInt(schatSplit[2]);
                String smsg = "";
                for (int i = 3; i < schatSplit.length; i++) {
                    smsg = smsg + " " + schatSplit[i];
                }
                String spresetName = arconix.editingPresetName.get(p);
                arconix.regionFile.getConfig().set("presets." + spresetName + ".fadeIn", sfadeIn);
                arconix.regionFile.getConfig().set("presets." + spresetName + ".stay", sstay);
                arconix.regionFile.getConfig().set("presets." + spresetName + ".fadeOut", sfadeOut);
                arconix.regionFile.getConfig().set("presets." + spresetName + ".msg", smsg);
                arconix.regionFile.saveConfig();
                p.sendMessage(arconix.format().formatText("&eYou successfully edited the &b" + spresetName + " &epreset!"));
                arconix.editingPreset.remove(p);
                arconix.editingPresetName.remove(p);
                break;
            case "text":
                String tpresetName = arconix.editingPresetName.get(p);
                arconix.regionFile.getConfig().set("presets." + tpresetName + ".msg", e.getMessage());
                arconix.regionFile.saveConfig();
                p.sendMessage(arconix.format().formatText("&eYou successfully edited the &b" + tpresetName + " &epreset!"));
                arconix.editingPreset.remove(p);
                arconix.editingPresetName.remove(p);
                break;
            case "ping":
                String ppresetName = arconix.editingPresetName.get(p);
                arconix.regionFile.getConfig().set("presets." + ppresetName + ".msg", e.getMessage());
                arconix.regionFile.saveConfig();
                p.sendMessage(arconix.format().formatText("&eYou successfully edited the &b" + ppresetName + " &epreset!"));
                arconix.editingPreset.remove(p);
                arconix.editingPresetName.remove(p);
                break;
            case "actionbar":
                String apresetName = arconix.editingPresetName.get(p);
                arconix.regionFile.getConfig().set("presets." + apresetName + ".msg", e.getMessage());
                arconix.regionFile.saveConfig();
                p.sendMessage(arconix.format().formatText("&eYou successfully edited the &b" + apresetName + " &epreset!"));
                arconix.editingPreset.remove(p);
                arconix.editingPresetName.remove(p);
                break;
        }
    }

    //Custom Event usage
    @EventHandler
    public void onRegionEnter(RegionEnterEvent e) {

        ArrayList<String> enterPresets = new ArrayList<>();

        Player p = e.getPlayer();
        if (arconix.regionFile.getConfig().getConfigurationSection("regions." + e.getRegionName() + ".presets") != null) {

            ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions." + e.getRegionName() + ".presets");

            for (String presets : section.getKeys(false)) {

                if (arconix.regionFile.getConfig().getString("regions." + e.getRegionName() + ".presets." + presets).equalsIgnoreCase("enter")) {
                    enterPresets.add(presets);
                }
            }

            for (String vpresets : enterPresets) {
                runPresetAffects(p, e.getRegionName(), vpresets);
            }
        }
    }

    @EventHandler
    public void onRegionExit(RegionExitEvent e) {

        ArrayList<String> enterPresets = new ArrayList<>();

        Player p = e.getPlayer();
        if (arconix.regionFile.getConfig().getConfigurationSection("regions." + e.getRegionName() + ".presets") != null) {

            ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions." + e.getRegionName() + ".presets");

            for (String presets : section.getKeys(false)) {

                if (arconix.regionFile.getConfig().getString("regions." + e.getRegionName() + ".presets." + presets).equalsIgnoreCase("exit")) {
                    enterPresets.add(presets);
                }
            }

            for (String vpresets : enterPresets) {
                runPresetAffects(p, e.getRegionName(), vpresets);
            }
        }
    }

    @EventHandler
    public void onRegionMoveIn(RegionMoveInEvent e) {

        ArrayList<String> enterPresets = new ArrayList<>();

        Player p = e.getPlayer();
        if (arconix.regionFile.getConfig().getConfigurationSection("regions." + e.getRegionName() + ".presets") != null) {

            ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions." + e.getRegionName() + ".presets");

            for (String presets : section.getKeys(false)) {

                if (arconix.regionFile.getConfig().getString("regions." + e.getRegionName() + ".presets." + presets).equalsIgnoreCase("movein")) {
                    enterPresets.add(presets);
                }
            }

            for (String vpresets : enterPresets) {
                runPresetAffects(p, e.getRegionName(), vpresets);
            }
        }
    }

    //Selection
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!arconix.inSelectionMode.contains(p)) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (!arconix.inSelectionMode.contains(p)) return;

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

            if (arconix.selectedLocationOne.containsKey(p)) {
                arconix.selectedLocationOne.remove(p);
                arconix.selectedLocationOne.put(p, e.getClickedBlock().getLocation());
                p.sendMessage(arconix.format().formatText("&eFirst location updated!"));
                return;
            }
            arconix.selectedLocationOne.put(p, e.getClickedBlock().getLocation());
            p.sendMessage(arconix.format().formatText("&eYou selected the first location."));
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (arconix.selectedLocationTwo.containsKey(p)) {
                arconix.selectedLocationTwo.remove(p);
                arconix.selectedLocationTwo.put(p, e.getClickedBlock().getLocation());
                p.sendMessage(arconix.format().formatText("&eSecond location updated!"));
                return;
            }
            arconix.selectedLocationTwo.put(p, e.getClickedBlock().getLocation());
            p.sendMessage(arconix.format().formatText("&eYou selected the second location."));
            return;
        }
    }


    @EventHandler
    public void onPlayerEnterRegion(PlayerMoveEvent e) {

        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {

            this.playerX = e.getTo().getBlockX();
            this.playerY = e.getTo().getBlockY();
            this.playerZ = e.getTo().getBlockZ();

            ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions");

            if (section == null) return;

            for (String ids : section.getKeys(false)) {

                x1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.x");
                y1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.y");
                z1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.z");
                world1 = arconix.regionFile.getConfig().getString("regions." + ids + ".location1.world");
                x2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.x");
                y2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.y");
                z2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.z");
                world2 = arconix.regionFile.getConfig().getString("regions." + ids + ".location2.world");

                Location location1 = new Location(Bukkit.getWorld(world1), x1, y1, z1);
                Location location2 = new Location(Bukkit.getWorld(world2), x2, y2, z2);

                if (isInCuboid(e.getFrom(), location1, location2)) return;

                if (isInCuboid(e.getTo(), location1, location2)) {

                    RegionEnterEvent regionEnterEvent = new RegionEnterEvent(e.getPlayer(), ids, location1, location2);
                    Bukkit.getServer().getPluginManager().callEvent(regionEnterEvent);

                }
            }
        }

    }

    @EventHandler
    public void onPlayerLeaveRegion(PlayerMoveEvent e) {

        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {

            this.playerX = e.getTo().getBlockX();
            this.playerY = e.getTo().getBlockY();
            this.playerZ = e.getTo().getBlockZ();

            ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions");

            if (section == null) return;

            for (String ids : section.getKeys(false)) {

                x1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.x");
                y1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.y");
                z1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.z");
                world1 = arconix.regionFile.getConfig().getString("regions." + ids + ".location1.world");
                x2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.x");
                y2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.y");
                z2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.z");
                world2 = arconix.regionFile.getConfig().getString("regions." + ids + ".location2.world");

                Location location1 = new Location(Bukkit.getWorld(world1), x1, y1, z1);
                Location location2 = new Location(Bukkit.getWorld(world2), x2, y2, z2);

                if (isInCuboid(e.getTo(), location1, location2)) return;

                if (isInCuboid(e.getFrom(), location1, location2)) {
                    RegionExitEvent regionEnterEvent = new RegionExitEvent(e.getPlayer(), ids, location1, location2);
                    Bukkit.getServer().getPluginManager().callEvent(regionEnterEvent);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMoveInRegion(PlayerMoveEvent e) {

        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {

            this.playerX = e.getTo().getBlockX();
            this.playerY = e.getTo().getBlockY();
            this.playerZ = e.getTo().getBlockZ();

            ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions");

            if (section == null) return;

            for (String ids : section.getKeys(false)) {

                x1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.x");
                y1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.y");
                z1 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location1.z");
                world1 = arconix.regionFile.getConfig().getString("regions." + ids + ".location1.world");
                x2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.x");
                y2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.y");
                z2 = arconix.regionFile.getConfig().getInt("regions." + ids + ".location2.z");
                world2 = arconix.regionFile.getConfig().getString("regions." + ids + ".location2.world");

                Location location1 = new Location(Bukkit.getWorld(world1), x1, y1, z1);
                Location location2 = new Location(Bukkit.getWorld(world2), x2, y2, z2);

                if (isInCuboid(e.getTo(), location1, location2)) {
                    RegionMoveInEvent regionMoveInEvent = new RegionMoveInEvent(e.getPlayer(), ids, location1, location2);
                    Bukkit.getServer().getPluginManager().callEvent(regionMoveInEvent);
                }
            }

        }
    }
}
