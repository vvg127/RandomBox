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

public class LeggingsTask extends BukkitRunnable {

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isSneaking()) {
                PlayerInventory pi = player.getInventory();

                if (pi.getLeggings() != null) {
                    ItemStack item = pi.getLeggings();
                    ItemMeta meta = item.getItemMeta();

                    List<Component> lore = meta.lore();
                    if (lore == null) {return;}

                    if (lore.contains(BoxItem.lore[4])) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 0));
                    }

                }
            }
        }

    }

    public static void startTask() {
        LeggingsTask task = new LeggingsTask();
        task.runTaskTimer(JavaPlugin.getPlugin(RandomBox.class), 0L, 1L);
    }



}
