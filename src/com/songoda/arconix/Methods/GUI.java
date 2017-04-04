package com.songoda.arconix.Methods;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by songoda on 4/2/2017.
 */
public class GUI {

    Inventory inventory;

    public GUI(Inventory inv) {
        inventory = inv;
    }

    public ItemStack getGlass(Boolean rainbow, int type) {
        int randomNum = 1 + (int)(Math.random() * 6);
        ItemStack glass;
        if (rainbow) {
            glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) randomNum);
        } else {
            glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) type);
        }
        ItemMeta glassmeta = glass.getItemMeta();
        glassmeta.setDisplayName("§l");
        glass.setItemMeta(glassmeta);
        return glass;
    }
}
