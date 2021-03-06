package com.songoda.arconix.plugin.Commands.Subcommands;

import com.songoda.arconix.api.ArconixAPI;
import com.songoda.arconix.api.methods.Formatting;
import com.songoda.arconix.plugin.Commands.SubCommand;
import org.bukkit.entity.Player;

/**
 * Created by Kiran Hart on 4/4/2017.
 */
public class HologramCMD extends SubCommand {

    private Formatting formatting = new Formatting();

    public HologramCMD() {
        super("hologram", "hologram", "ArconixCMD.command.hologram", "Make a hologram", 4);
    }

    @Override
    public void execute(Player p, String[] args) {

        if (args.length == 1) {
            p.sendMessage(formatting.formatText("&e/Arconix hologram hide/show/create [Text]"));
        }

        if (args.length == 2) {

            switch (args[1].toLowerCase()) {

                case "show":
                    //plugin.holo.handleMovement(p.getLocation(), null, p);
                    break;
                case "hide":
                    //arconix.packetLibrary.getHologramManager().hideHolograms(p);
                    break;
                case "delete":
                    p.sendMessage(formatting.formatText("&e/Arconix hologram delete [hologram]"));
                    break;
                case "create":
                    p.sendMessage(formatting.formatText("&e/Arconix hologram create [hologram] [title]"));
                    break;
                default:
                    break;
            }
        }

        if (args.length >= 3) {

            switch (args[1].toLowerCase()) {
                case "delete":
                    ArconixAPI.getApi().getHolo().deleteHologram(p, args[2].trim());
                    break;
                case "create":
                    StringBuilder text = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        String arg = args[i] + " ";
                        text.append(arg);
                    }
                    ArconixAPI.getApi().getHolo().createHologram(p, args[2].trim(), text.toString());
                    break;
                default:
                    break;
            }
        }
    }
}
