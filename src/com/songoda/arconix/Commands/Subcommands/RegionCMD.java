package com.songoda.arconix.Commands.Subcommands;

import com.songoda.arconix.Arconix;
import com.songoda.arconix.Commands.SubCommand;
import com.songoda.arconix.Events.Custom.RegionCreateEvent;
import com.songoda.arconix.Methods.Formatting;
import com.songoda.arconix.Utils.RegionUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Created by Kiran Hart on 4/12/2017.
 */
public class RegionCMD extends SubCommand {

    private Formatting formatting = new Formatting();

    private Arconix arconix = Arconix.pl();
    private RegionUtils regionUtils = new RegionUtils();

    public RegionCMD() {
        super("region", "region", "ArconixCMD.command.region", "Stuff for regions", 4);
    }

    @Override
    public void execute(Player p, String[] args) {

        if (args.length == 1) {
            p.sendMessage(formatting.formatText("&e/arconix region select &7-Enter region selection mode."));
            p.sendMessage(formatting.formatText("&e/arconix region list &7-List all the created regions."));
            p.sendMessage(formatting.formatText("&e/arconix region create <name> &7-Create a new defined region."));
            p.sendMessage(formatting.formatText("&e/arconix region delete <name> &7-Delete an existing region."));
        }

        if (args.length == 2) {

            switch (args[1].toLowerCase()) {

                case "select":
                    if (!arconix.inSelectionMode.contains(p)) {
                        arconix.inSelectionMode.add(p);
                        p.sendMessage(formatting.formatText("&eYou are now within region selection mode, select a region by left/right clicking blocks."));
                    } else {
                        arconix.inSelectionMode.remove(p);
                        p.sendMessage(formatting.formatText("&cYou are no longer within region selection mode."));
                    }
                    break;
                case "list":
                    ConfigurationSection section = arconix.regionFile.getConfig().getConfigurationSection("regions");
                    if (section != null) {
                        p.sendMessage(formatting.formatText("&e------------- Created Regions -------------"));
                        int index = 0;
                        int x1, y1, z1, x2, y2, z2;
                        String world;
                        for (String regions : regionUtils.getAllRegions()) {
                            index++;
                            world = arconix.regionFile.getConfig().getString("regions." + regions + ".location1.world");
                            x1 = arconix.regionFile.getConfig().getInt("regions." + regions + ".location1.x");
                            y1 = arconix.regionFile.getConfig().getInt("regions." + regions + ".location1.y");
                            z1 = arconix.regionFile.getConfig().getInt("regions." + regions + ".location1.z");
                            x2 = arconix.regionFile.getConfig().getInt("regions." + regions + ".location2.x");
                            y2 = arconix.regionFile.getConfig().getInt("regions." + regions + ".location2.y");
                            z2 = arconix.regionFile.getConfig().getInt("regions." + regions + ".location2.z");
                            p.sendMessage(formatting.formatText("&6" + index + "&f. &e" + regions + "&f&l| &b" + world + "&d " + x1 + ", " + y1 + ", " + z1 + " &9&l|&e " + x2 + ", " + y2 + ", " + z2));
                        }
                    }
                    break;
                case "create":
                    p.sendMessage(formatting.formatText("&e/arconix region create &c<name>"));
                    break;
                case "delete":
                    p.sendMessage(formatting.formatText("&e/arconix region delete &c<name>"));
                    break;
            }

        }

        if (args.length == 3) {

            switch (args[1].toLowerCase()) {

                case "create":
                    if (arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cA region by that name already exist!"));
                        return;
                    }

                    if (!arconix.selectedLocationOne.containsKey(p)) {
                        p.sendMessage(formatting.formatText("&cCannot create the region until the first location is defined!"));
                        return;
                    }

                    if (!arconix.selectedLocationTwo.containsKey(p)) {
                        p.sendMessage(formatting.formatText("&cCannot create the region until the second location is defined!"));
                        return;
                    }

                    Location locationOne = arconix.selectedLocationOne.get(p);
                    Location locationTwo = arconix.selectedLocationTwo.get(p);

                    RegionCreateEvent regionCreateEvent = new RegionCreateEvent(p, args[2].toLowerCase(), locationOne, locationTwo);
                    arconix.getServer().getPluginManager().callEvent(regionCreateEvent);
                    if (regionCreateEvent.isCancelled()) {
                        return;
                    }
                    regionUtils.createNewRegion(p, args[2], locationOne, locationTwo);
                    break;
                case "delete":
                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo region(s) by that name exist!"));
                        return;
                    }

                    regionUtils.removeRegion(p, args[2]);
                    break;
            }

        }

    }
}
