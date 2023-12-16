package org.plugin.randombox;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Thunder extends BukkitRunnable {
    @Override
    public void run() {

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {

                if (entity instanceof  LivingEntity) {
                    LivingEntity e = (LivingEntity) entity;

                    if (e.getPotionEffect(PotionEffectType.GLOWING) != null) {
                        if (e.getPotionEffect(PotionEffectType.GLOWING).getAmplifier() == 10) {
                            e.getWorld().spawnEntity(e.getLocation(), EntityType.LIGHTNING);
                        }
                    }
                }
            }
        }

    }

    public static void startTask() {
        Thunder task = new Thunder();
        task.runTaskTimer(JavaPlugin.getPlugin(RandomBox.class), 0L, 1L);
    }
}
