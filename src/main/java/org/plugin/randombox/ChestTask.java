package org.plugin.randombox;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ChestTask extends BukkitRunnable {

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInventory pi = player.getInventory();

            if (player.getHealth() <= 5) {
                if (pi.getChestplate() != null) {
                    ItemStack item = pi.getChestplate();
                    ItemMeta meta = item.getItemMeta();

                    List<Component> lore = meta.lore();
                    if (lore == null) {return;}

                    if (lore.contains(BoxItem.lore[3])) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0));
                    }

                }
            }
        }
    }

    public static void startTask() {
        ChestTask task = new ChestTask();
        task.runTaskTimer(JavaPlugin.getPlugin(RandomBox.class), 0L, 1L);
    }



}
