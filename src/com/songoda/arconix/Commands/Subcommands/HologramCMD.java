package com.songoda.arconix.Commands.Subcommands;

import com.songoda.arconix.Arconix;
import com.songoda.arconix.Commands.SubCommand;
import com.songoda.arconix.Methods.Formatting;
import org.bukkit.entity.Player;

/**
 * Created by Kiran Hart on 4/4/2017.
 */
public class HologramCMD extends SubCommand {

    private Formatting formatting = new Formatting();

    public HologramCMD() {
        super("hologram", "hologram", "ArconixCMD.command.hologram", "Make a hologram", 4);
    }

    private Arconix arconix = Arconix.pl();

    @Override
    public void execute(Player p, String[] args) {

        if (args.length == 1) {
            p.sendMessage(formatting.formatText("&e/Arconix hologram hide/show/create [Text]"));
        }

        if (args.length == 2) {

            switch (args[1].toLowerCase()) {

                case "show":
                    arconix.packetLibrary.getHologramManager().showHolograms(p);
                    break;
                case "hide":
                    arconix.packetLibrary.getHologramManager().hideHolograms(p);
                    break;
                case "create":
                    p.sendMessage(formatting.formatText("&e/Arconix hologram create [Text]"));
                    break;
                default:
                    break;
            }
        }

        if (args.length >= 3) {

            String text = "";
            for (int i = 2; i < args.length; i++) {
                String arg = args[i] + " ";
                text = text + arg;
            }
            arconix.packetLibrary.getHologramManager().spawnHologram(p.getLocation().add(0, 1.5, 0), formatting.formatText(text));
        }
    }
}
