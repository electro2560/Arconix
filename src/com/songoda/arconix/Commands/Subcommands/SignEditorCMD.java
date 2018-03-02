package com.songoda.arconix.Commands.Subcommands;

import com.songoda.arconix.Arconix;
import com.songoda.arconix.Commands.SubCommand;
import com.songoda.arconix.Methods.Formatting;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kiran Hart on 4/7/2017.
 */
public class SignEditorCMD extends SubCommand {

    private Formatting formatting = new Formatting();

    public SignEditorCMD() {
        super("signeditor", "signeditor", "ArconixCMD.command.signeditor", "Re-Open the sign editor", 1);
    }

    private Arconix arconix = Arconix.pl();

    @Override
    public void execute(Player p, String[] args) {

        if (args.length == 1) {

            if (p.getTargetBlock((Set<Material>) null, 200).getType() != Material.SIGN_POST) {
                p.sendMessage(formatting.formatText("&cPlease look at a sign before using this command!"));
            } else {
                Sign sign = (Sign) p.getTargetBlock((Set<Material>) null, 25).getState();
                arconix.packetLibrary.getSignEditorManager().openSignEditor(p, sign);
            }
        }
    }
}
