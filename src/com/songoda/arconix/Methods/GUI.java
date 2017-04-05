package com.songoda.arconix.Methods;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by songoda on 4/2/2017.
 */
public class GUI {

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

    public static void fillGlass(Inventory i, int type) {
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta glassmeta = glass.getItemMeta();
        glassmeta.setDisplayName("§5");
        glass.setItemMeta(glassmeta);

        int nu = 0;
        while (nu != 27) {
            i.setItem(nu, glass);
            nu++;
        }
    }

    public boolean inventoryContains(Inventory inventory, ItemStack item) {
        int count = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()) {
                count += items[i].getAmount();
            }
            if (count >= item.getAmount()) {
                return true;
            }
        }
        return false;
    }

    public void removeFromInventory(Inventory inventory, ItemStack item) {
        int amt = item.getAmount();
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()) {
                if (items[i].getAmount() > amt) {
                    items[i].setAmount(items[i].getAmount() - amt);
                    break;
                } else if (items[i].getAmount() == amt) {
                    items[i] = null;
                    break;
                } else {
                    amt -= items[i].getAmount();
                    items[i] = null;
                }
            }
        }
        inventory.setContents(items);
    }
}
