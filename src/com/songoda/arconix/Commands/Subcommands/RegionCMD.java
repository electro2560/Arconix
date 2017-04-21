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
            p.sendMessage(formatting.formatText("&e/arconix region presetTypes &7-List all the valid presets."));
            p.sendMessage(formatting.formatText("&e/arconix region delpreset <name> &7-Delete an existing preset."));
            p.sendMessage(formatting.formatText("&e/arconix region editpreset <name> &7-Edit an existing preset."));
            p.sendMessage(formatting.formatText("&e/arconix region newpreset [Preset] <name> &7-Create a region preset"));
            p.sendMessage(formatting.formatText("&e/arconix region setpreset <PresetName> <region> &7-Give a region a created preset"));
            p.sendMessage(formatting.formatText("&e/arconix region setpresetactivation <Region> <PresetName> <ActivationType> &7-Set a preset activation type."));
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
                case "setpresetactivation":
                    p.sendMessage(formatting.formatText("&e/arconix setpresetactivation <Region> <PresetName> <ActivationType>"));
                    break;
                case "presettypes":
                    p.sendMessage(formatting.formatText("&b&lPreset Types:"));
                    p.sendMessage(formatting.formatText("&eTitle | Subtitle | Actionbar | Text | Ping |"));
                    p.sendMessage(formatting.formatText("&b&lPreset Activation Types:"));
                    p.sendMessage(formatting.formatText("&eEnter | Exit | Movein |"));
                    break;
                case "setpreset":
                    p.sendMessage(formatting.formatText("&e/arconix setpreset <PresetName> <region>"));
                    break;
                case "editpreset":
                    p.sendMessage(formatting.formatText("&e/arconix editpreset <PresetName>"));
                    break;
                case "newpreset":
                    p.sendMessage(formatting.formatText("&e/arconix newpreset [Preset] <Name>"));
                    break;
                case "delpreset":
                    p.sendMessage(formatting.formatText("&e/arconix delpreset <Name>"));
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
                case "setpresetactivation":
                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo region(s) by that name exist!"));
                        return;
                    }
                    p.sendMessage(formatting.formatText("&e/arconix setpresetactivation " + args[2] + " &c<PresetName> <ActivationType>"));
                    break;
                case "delete":
                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo region(s) by that name exist!"));
                        return;
                    }

                    regionUtils.removeRegion(p, args[2]);
                    break;
                case "delpreset":
                    if (!arconix.regionFile.getConfig().contains("presets." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cCould not find any presets by that name!"));
                        return;
                    }
                    arconix.regionFile.getConfig().set("presets." + args[2].toLowerCase(), null);
                    arconix.regionFile.saveConfig();
                    p.sendMessage(formatting.formatText("&eSuccessfully deleted the preset by the id of &B" + args[2]));
                    break;
                case "editpreset":
                    if (!arconix.regionFile.getConfig().contains("presets." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cCould not find any presets by that name!"));
                        return;
                    }
                    if (arconix.editingPreset.containsKey(p)) {
                        arconix.editingPreset.remove(p);
                        p.sendMessage(formatting.formatText("&cYou stopped editing that preset!"));
                        return;
                    }

                    String type = arconix.regionFile.getConfig().getString("presets." + args[2].toLowerCase() + ".type");
                    switch (type) {
                        case "title":
                            arconix.editingPreset.put(p, "title");
                            arconix.editingPresetName.put(p, args[2].toLowerCase());
                            p.sendMessage(formatting.formatText("&bLooks like your editing a title preset!"));
                            p.sendMessage(formatting.formatText("&ePlease follow the format for editing this preset below"));
                            p.sendMessage(formatting.formatText("&7In chat please follow this editing structure!"));
                            p.sendMessage(formatting.formatText("&7First 3 phrases should be numbers [FadeInTime, StayTime, FadeOutTime] followed by the text."));
                            p.sendMessage(formatting.formatText("&bEx. &d20 &940 &e20 &6The Text you want"));
                            break;
                        case "subtitle":
                            arconix.editingPreset.put(p, "subtitle");
                            arconix.editingPresetName.put(p, args[2].toLowerCase());
                            p.sendMessage(formatting.formatText("&bLooks like your editing a subtitle preset!"));
                            p.sendMessage(formatting.formatText("&ePlease follow the format for editing this preset below"));
                            p.sendMessage(formatting.formatText("&7In chat please follow this editing structure!"));
                            p.sendMessage(formatting.formatText("&7First 3 phrases should be numbers [FadeInTime, StayTime, FadeOutTime] followed by the text."));
                            p.sendMessage(formatting.formatText("&bEx. &d20 &940 &e20 &6The Text you want"));
                            break;
                        case "text":
                            arconix.editingPreset.put(p, "text");
                            arconix.editingPresetName.put(p, args[2].toLowerCase());
                            p.sendMessage(formatting.formatText("&bLooks like your editing a text preset!"));
                            p.sendMessage(formatting.formatText("&eSimply type what you wish to change the text to into chat."));
                            break;
                        case "ping":
                            arconix.editingPreset.put(p, "ping");
                            arconix.editingPresetName.put(p, args[2].toLowerCase());
                            p.sendMessage(formatting.formatText("&bLooks like your editing a ping preset!"));
                            p.sendMessage(formatting.formatText("&6Placeholders&f: &a{ping} &7-Used to get the ping, you should use it."));
                            p.sendMessage(formatting.formatText("&eSimply type what you wish to change the text to into chat."));
                            break;
                        case "actionbar":
                            arconix.editingPreset.put(p, "actionbar");
                            arconix.editingPresetName.put(p, args[2].toLowerCase());
                            p.sendMessage(formatting.formatText("&bLooks like your editing an actionbar preset!"));
                            p.sendMessage(formatting.formatText("&eSimply type what you wish to change the text to into chat."));
                            break;
                        default:
                            break;
                    }
                    break;
                case "newpreset":
                    switch (args[2].toLowerCase()) {
                        case "title":
                            p.sendMessage(formatting.formatText("&e/arconix newpreset title <Name>"));
                            break;
                        case "subtitle":
                            p.sendMessage(formatting.formatText("&e/arconix newpreset subtitle <Name>"));
                            break;
                        case "ping":
                            p.sendMessage(formatting.formatText("&e/arconix newpreset ping <Name>"));
                            break;
                        case "text":
                            p.sendMessage(formatting.formatText("&e/arconix newpreset text <Name>"));
                            break;
                        case "actionbar":
                            p.sendMessage(formatting.formatText("&e/arconix newpreset actionbar <Name>"));
                            break;
                        default:
                            p.sendMessage(formatting.formatText("&cInvalid preset type"));
                            break;
                    }
                    break;
                case "setpreset":
                    if (!arconix.regionFile.getConfig().contains("presets." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cCould not find any presets by that name!"));
                        return;
                    }
                    p.sendMessage(formatting.formatText("&e/arconix setpreset " + args[2] + " &c<region>"));
                    break;
            }

        }

        if (args.length == 4) {

            switch (args[1].toLowerCase()) {

                case "setpreset":

                    if (!arconix.regionFile.getConfig().contains("presets." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cCould not find any presets by that name!"));
                        return;
                    }
                    if (arconix.regionFile.getConfig().contains("regions." + args[3].toLowerCase() + ".presets." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cYou already added that preset to that region"));
                        return;
                    }
                    arconix.regionFile.getConfig().set("regions." + args[3].toLowerCase() + ".presets." + args[2].toLowerCase(), "ENTER");
                    arconix.regionFile.saveConfig();
                    p.sendMessage(formatting.formatText("&eYou've added the &b" + args[2] + " &epreset to the &b" + args[3] + " &eregion!"));
                    break;
                case "setpresetactivation":
                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo region(s) by that name exist!"));
                        return;
                    }

                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase() + ".presets." + args[3].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo preset by that name exist!"));
                        return;
                    }

                    p.sendMessage(formatting.formatText("&e/arconix setpresetactivation " + args[2] + " " + args[3] + " &c<ActivationType>"));
                    break;
                case "newpreset":
                    switch (args[2].toLowerCase()) {
                        case "title":
                            if (arconix.regionFile.getConfig().contains("presets." + args[3].toLowerCase())) {
                                p.sendMessage(formatting.formatText("&cA preset by that name already exist!"));
                                return;
                            }
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".type", "title");
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".fadeIn", 20);
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".stay", 30);
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".fadeOut", 30);
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".msg", "&a&lChange this in the region file/ingame.");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou created a title preset with the id of &b" + args[3]));
                            break;
                        case "subtitle":
                            if (arconix.regionFile.getConfig().contains("presets." + args[2].toLowerCase())) {
                                p.sendMessage(formatting.formatText("&cA preset by that name already exist!"));
                                return;
                            }
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".type", "subtitle");
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".fadeIn", 20);
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".stay", 30);
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".fadeOut", 30);
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".msg", "&b&lChange this in the region file/ingame.");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou created a subtitle preset with the id of &b" + args[3]));
                            break;
                        case "ping":
                            if (arconix.regionFile.getConfig().contains("presets." + args[3].toLowerCase())) {
                                p.sendMessage(formatting.formatText("&cA preset by that name already exist!"));
                                return;
                            }
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".type", "ping");
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".msg", "&eYour ping is &a{ping}");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou created a ping preset with the id of &b" + args[3]));
                            break;
                        case "text":
                            if (arconix.regionFile.getConfig().contains("presets." + args[3].toLowerCase())) {
                                p.sendMessage(formatting.formatText("&cA preset by that name already exist!"));
                                return;
                            }
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".type", "text");
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".msg", "&cChange in the region file/ingame");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou created a text preset with the id of &b" + args[3]));
                            break;
                        case "actionbar":
                            if (arconix.regionFile.getConfig().contains("presets." + args[3].toLowerCase())) {
                                p.sendMessage(formatting.formatText("&cA preset by that name already exist!"));
                                return;
                            }
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".type", "actionbar");
                            arconix.regionFile.getConfig().set("presets." + args[3].toLowerCase() + ".msg", "&cChange in the region file/ingame");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou created a actionbar preset with the id of &b" + args[3]));
                            break;
                    }
                    break;
            }
        }

        if (args.length == 5) {

            switch (args[1].toLowerCase()) {

                case "setpresetactivation":

                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo region(s) by that name exist!"));
                        return;
                    }

                    if (!arconix.regionFile.getConfig().contains("regions." + args[2].toLowerCase() + ".presets." + args[3].toLowerCase())) {
                        p.sendMessage(formatting.formatText("&cNo preset by that name exist!"));
                        return;
                    }

                    switch (args[4].toLowerCase()) {

                        case "enter":
                            arconix.regionFile.getConfig().set("regions." + args[2].toLowerCase() + ".presets." + args[3].toLowerCase(), "enter");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou changed that region's preset's activation type to &bEnter"));
                            break;
                        case "exit":
                            arconix.regionFile.getConfig().set("regions." + args[2].toLowerCase() + ".presets." + args[3].toLowerCase(), "exit");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou changed that region's preset's activation type to &bExit"));
                            break;
                        case "movein":
                            arconix.regionFile.getConfig().set("regions." + args[2].toLowerCase() + ".presets." + args[3].toLowerCase(), "movein");
                            arconix.regionFile.saveConfig();
                            p.sendMessage(formatting.formatText("&eYou changed that region's preset's activation type to &bMoveIn"));
                            break;
                        default:
                            p.sendMessage(formatting.formatText("&cInvalid activation type!"));
                            break;
                    }

                    break;
            }
        }
    }
}
