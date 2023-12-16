package org.plugin.randombox;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BoxUse implements Listener {

    @EventHandler
    public void BoxDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        BoxItem box = new BoxItem();

        ItemStack item = e.getItemDrop().getItemStack();
        if (item.isSimilar(box.boxItem)) {

            Item drop = e.getItemDrop();
            ItemStack newItem = new ItemStack(Material.COMMAND_BLOCK);

            if (item.getAmount() != 1) {
                e.setCancelled(true);
                p.sendMessage("한 개씩만 사용해 주세요");
                return;
            }

            drop.setGlowing(true);

            if ((int) (Math.random() * 1000) == 1) {

                int random = (int) (Math.random() * BoxItem.legend.length);
                newItem = BoxItem.legend[random];

                Firework firework = (Firework) drop.getWorld().spawnEntity(drop.getLocation(), EntityType.FIREWORK);
                FireworkMeta meta = firework.getFireworkMeta();

                meta.addEffect(
                        FireworkEffect.builder()
                                .with(FireworkEffect.Type.BALL_LARGE)
                                .flicker(true)
                                .trail(true)
                                .withColor(Color.YELLOW)
                                .withColor(Color.RED)
                                .build()
                );
                firework.setFireworkMeta(meta);
                firework.detonate();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE,1,1);

                    if (player.equals(p)) {
                        p.showTitle(Title.title(
                                Component.text("경축", Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255,255,255))),
                                Component.text("레전더리 아이템을 뽑으셨습니다!", Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255, 250, 35)))
                        ));
                    } else {
                        player.showTitle(Title.title(
                                Component.text("경축", Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255,255,255))),
                                Component.text("누군가가 레전더리 아이템을 뽑았습니다!", Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255, 250, 35)))
                        ));
                    }

                }

            } else {

                while (true) {
                    Material[] type = Material.values();
                    int random = (int) (Math.random() * type.length);
                    if (type[random].isLegacy()) {continue;}
                    newItem.setType(type[random]);
                    break;
                }

                if ((int) (Math.random() * 20) == 1) {
                    ItemMeta meta = newItem.getItemMeta();

                    Enchantment[] type = Enchantment.values();
                    int random = (int) (Math.random() * type.length);
                    meta.addEnchant(type[random], (int) (Math.random() * type[random].getMaxLevel()) + 1, true);
                    newItem.setItemMeta(meta);

                    p.playSound(p, Sound.BLOCK_ENCHANTMENT_TABLE_USE,1,1);
                } else {p.playSound(p, Sound.ENTITY_TURTLE_EGG_CRACK,1,1);}

            }



            if (newItem.getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) newItem.getItemMeta();

                Enchantment[] type = Enchantment.values();
                int random = (int) (Math.random() * type.length);
                meta.addEnchant(type[random], (int) (Math.random() * type[random].getMaxLevel()) + 1, true);
                newItem.setItemMeta(meta);

            }

            if (newItem.getType() == Material.POTION || newItem.getType() == Material.LINGERING_POTION || newItem.getType() == Material.SPLASH_POTION) {
                PotionMeta meta = (PotionMeta) newItem.getItemMeta();

                PotionEffectType[] type = PotionEffectType.values();
                int random = (int) (Math.random() * type.length);
                PotionEffect effect = new PotionEffect(type[random], (int) ((Math.random() * 600) + 1) * 20, (int) (Math.random() * 5) + 1);
                meta.addCustomEffect(effect, true);
                newItem.setItemMeta(meta);

            }

            drop.setItemStack(newItem);

        }
    }

}
