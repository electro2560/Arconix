package com.songoda.arconix.packets.TabList;

import com.songoda.arconix.Methods.Formatting;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by Kiran Hart on 4/15/2017.
 */
public class TabList1_8R2 implements TabList {

    Formatting formatting = new Formatting();

    @Override
    public void sendTablist(Player p, String header, String footer) {

        PacketPlayOutPlayerListHeaderFooter headerFooterPacket = new PacketPlayOutPlayerListHeaderFooter();

        IChatBaseComponent headerComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + formatting.formatText(header) + "\"}");
        IChatBaseComponent footerComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + formatting.formatText(footer) + "\"}");

        try {
            Field headerField = headerFooterPacket.getClass().getDeclaredField("a");
            Field footerField = headerFooterPacket.getClass().getDeclaredField("b");
            headerField.setAccessible(true);
            footerField.setAccessible(true);
            headerField.set(headerFooterPacket, headerComp);
            footerField.set(headerFooterPacket, footerComp);
            headerField.setAccessible(false);
            footerField.setAccessible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(headerFooterPacket);
    }
}
