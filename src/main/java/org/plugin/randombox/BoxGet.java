package org.plugin.randombox;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BoxGet implements Listener {

    @EventHandler
    public void ItemGet(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerInventory pi = p.getInventory();

            ItemStack item = e.getItem().getItemStack();

            if (getType(item) != 0) {

                ItemStack box = new BoxItem().boxItem;
                box.setAmount(item.getAmount() * getAmount(item));
                pi.addItem(box);
                p.playSound(p, Sound.ENTITY_CHICKEN_EGG,1,1);
                e.setCancelled(true);
                e.getItem().remove();

            }

        }
    }

    @EventHandler
    public void ItemGetOther(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            PlayerInventory pi = p.getInventory();
            Inventory in = e.getClickedInventory();
            if (in == null) {return;}

            ItemStack item = e.getCurrentItem();
            if (item == null) {return;}

            if (getType(item) != 0) {

                e.setCancelled(true);
                in.remove(item.getType());
                ItemStack box = new BoxItem().boxItem;
                box.setAmount(item.getAmount() * getAmount(item));
                pi.addItem(box);
                p.playSound(p, Sound.ENTITY_CHICKEN_EGG,1,1);

            }

        }

    }

    private int getType(ItemStack item) {
        Material material = item.getType();

        if (material == Material.IRON_INGOT) {
            return 1;
        } else if (material == Material.GOLD_INGOT) {
            return 2;
        } else if (material == Material.DIAMOND) {
            return 3;
        } else if (material == Material.EMERALD) {
            return 4;
        } else if (material == Material.NETHERITE_SCRAP) {
            return 5;
        } else if (material == Material.NETHERITE_INGOT) {
            return 6;
        } else {return 0;}

    }

    private int getAmount(ItemStack item) {
        Material material = item.getType();

        if (material == Material.IRON_INGOT) {
            return 1;
        } else if (material == Material.GOLD_INGOT) {
            return 2;
        } else if (material == Material.DIAMOND) {
            return 4;
        } else if (material == Material.EMERALD) {
            return 2;
        } else if (material == Material.NETHERITE_SCRAP) {
            return 8;
        } else if (material == Material.NETHERITE_INGOT) {
            return 32;
        } else {return 0;}

    }

}
