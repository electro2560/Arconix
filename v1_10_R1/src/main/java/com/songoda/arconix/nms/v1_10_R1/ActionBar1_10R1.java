package com.songoda.arconix.nms.v1_10_R1;

import com.songoda.arconix.api.events.Custom.ActionBarSendEvent;
import com.songoda.arconix.api.packets.ActionBar;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar1_10R1 implements ActionBar {

    @Override
    public void sendActionBar(Player p, String message) {
        IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ActionBarSendEvent actionBarSendEvent = new ActionBarSendEvent(p, message);
        Bukkit.getServer().getPluginManager().callEvent(actionBarSendEvent);
        if (actionBarSendEvent.isCancelled()) {
            return;
        }
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
    }

}
