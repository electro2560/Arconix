package com.songoda.arconix.nms.v1_7_R4;

import com.songoda.arconix.api.packets.SignEditor;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.TileEntitySign;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Kiran Hart on 4/7/2017.
 */
public class SignEditor1_7R4 implements SignEditor {

    @Override
    public void openSignEditor(Player p, Sign sign) {

        Location loc = sign.getLocation();
        EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) nmsPlayer.world.getTileEntity(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        PlayerConnection conn = nmsPlayer.playerConnection;

        tileEntitySign.isEditable = true;
        tileEntitySign.a(nmsPlayer);
        conn.sendPacket(new PacketPlayOutOpenSignEditor(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }

}

