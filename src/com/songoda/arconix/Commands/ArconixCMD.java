package com.songoda.arconix.Commands;

import com.songoda.arconix.Commands.Subcommands.SubtitleCMD;
import com.songoda.arconix.Commands.Subcommands.TitleSubCMD;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kiran Hart on 4/4/2017.
 */
public class ArconixCMD extends BaseCommand {

    private final Map<String, SubCommand> children = new HashMap<>();

    public ArconixCMD() {

        super("Arconix", "ArconixCMD.command");
        children.put("title", new TitleSubCMD());
        children.put("subtitle", new SubtitleCMD());
    }

    @Override
    public void execute(Player p, String[] args) {

        if (args.length == 0) {

            return;
        }

        SubCommand child = children.get(args[0].toLowerCase());

        if (child != null) {
            child.execute(p, args);
            return;
        }
    }

    public boolean registerChildren(SubCommand command) {

        if (children.get(command.getName()) != null) {
            return false;
        }

        children.put(command.getName(), command);
        return true;
    }

}
