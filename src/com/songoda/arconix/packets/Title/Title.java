package com.songoda.arconix.packets.Title;

import org.bukkit.entity.Player;

public interface Title {

    void sendTitle(Player p, String msg, int fadeIn, int stay, int fadeOut);

    void sendSubitle(Player p, String msg, int fadeIn, int stay, int fadeOut);
}
