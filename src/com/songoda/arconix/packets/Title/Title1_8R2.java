package com.songoda.arconix.packets.Title;

import com.songoda.arconix.Methods.Formatting;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Kiran Hart on 4/4/2017.
 */
public class Title1_8R2 implements Title {

    Formatting formatting = new Formatting();

    @Override
    public void sendTitle(Player p, String msg, int fadeIn, int stay, int fadeOut) {

        CraftPlayer craftPlayer = (CraftPlayer) p;
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
        IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{'text': '" + formatting.formatText(msg) + "'}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title, fadeIn, stay, fadeOut);
        playerConnection.sendPacket(packet);
    }

    @Override
    public void sendSubitle(Player p, String msg, int fadeIn, int stay, int fadeOut) {

        CraftPlayer craftPlayer = (CraftPlayer) p;
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
        IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{'text': '" + formatting.formatText(msg) + "'}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, title, fadeIn, stay, fadeOut);
        playerConnection.sendPacket(packet);
    }
}
