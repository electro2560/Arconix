package com.songoda.arconix.nms.v1_7_R4;

import com.songoda.arconix.api.events.Custom.ActionBarSendEvent;
import com.songoda.arconix.api.packets.ActionBar;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar1_7R4 implements ActionBar {

    @Override
    public void sendActionBar(Player p, String message) {
        IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, true);
        ActionBarSendEvent actionBarSendEvent = new ActionBarSendEvent(p, message);
        Bukkit.getServer().getPluginManager().callEvent(actionBarSendEvent);
        if (actionBarSendEvent.isCancelled()) {
            return;
        }
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
    }

}
