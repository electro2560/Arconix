package com.songoda.arconix.plugin.Commands.Subcommands;

import com.songoda.arconix.api.methods.Formatting;
import com.songoda.arconix.plugin.Arconix;
import com.songoda.arconix.plugin.Commands.SubCommand;
import org.bukkit.entity.Player;

/**
 * Created by Kiran Hart on 4/4/2017.
 */
public class ReloadCMD extends SubCommand {

    private Formatting formatting = new Formatting();

    public ReloadCMD() {
        super("reload", "reload", "ArconixCMD.command.reload", "reload", 1);
    }

    private Arconix arconix = Arconix.pl();

    @Override
    public void execute(Player p, String[] args) {

        if (args.length == 1) {
            Arconix.pl().reload();
            p.sendMessage("Reload Successful.");
        }
    }
}
