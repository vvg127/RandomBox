package org.plugin.randombox;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class BoxItem {

    ItemStack boxItem = new ItemStack(Material.COMMAND_BLOCK);

    private static final ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
    private static final ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
    private static final ItemStack head = new ItemStack(Material.NETHERITE_HELMET);
    private static final ItemStack chest = new ItemStack(Material.NETHERITE_CHESTPLATE);
    private static final ItemStack leg = new ItemStack(Material.NETHERITE_LEGGINGS);
    private static final ItemStack shoes = new ItemStack(Material.NETHERITE_BOOTS);
    private static final ItemStack bow = new ItemStack(Material.BOW);
    private static final ItemStack bow2 = new ItemStack(Material.BOW);
    private static final ItemStack crossbow = new ItemStack(Material.CROSSBOW);
    private static final ItemStack fork = new ItemStack(Material.TRIDENT);
    private static final ItemStack lighting = new ItemStack(Material.SPLASH_POTION);
    private static final ItemStack rocket = new ItemStack(Material.CROSSBOW);
    private static final ItemStack tntEX = new ItemStack(Material.FIREWORK_ROCKET, 8);
    private static final ItemStack hook = new ItemStack(Material.FISHING_ROD);

    static final ItemStack[] legend = {sword, axe, head, chest, leg, shoes, bow, bow2, crossbow, fork, lighting, rocket, tntEX, hook};

    private static final TextColor color = TextColor.color(255, 228, 0);
    private static final Style style = Style.style(color, TextDecoration.ITALIC.withState(false));
    private static final Component lore1 = Component.text("흡혈", style);
    private static final Component lore2 = Component.text("전기 충격기", style);
    private static final Component lore3 = Component.text("야간 투시 제공", style);
    private static final Component lore4 = Component.text("버서커", style);
    private static final Component lore5 = Component.text("웅크릴 시 저항 1", style);
    private static final Component lore6 = Component.text("더블 점프", style);
    private static final Component lore7 = Component.text("폭발 화살", style);
    private static final Component lore8 = Component.text("화살 비", style);
    private static final Component lore9 = Component.text("무적 시간 제거", style);
    private static final Component lore10 = Component.text("끌어오기", style);
    private static final Component lore11 = Component.text("투척용 낙뢰의 물약", style);
    private static final Component lore12 = Component.text("로켓 런쳐", style);
    private static final Component lore13 = Component.text("로켓 폭죽", style);
    private static final Component lore14 = Component.text("그래플링 훅", style);

    static final Component[] lore = {lore1, lore2, lore3, lore4, lore5, lore6, lore7, lore8, lore9, lore10, lore11, lore12, lore13, lore14};

    private static final Component name = Component.text("레전더리 아이템", style);
    static final Style crossStyle = Style.style(TextColor.color(255, 255, 255), TextDecoration.ITALIC.withState(false));

    {
        ItemMeta meta = boxItem.getItemMeta();
        meta.displayName(Component.text("랜덤 박스", Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255,255,0))));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("던져서 사용", Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255, 255, 255))));
        meta.lore(lore);

        meta.addEnchant(Enchantment.VANISHING_CURSE,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        boxItem.setItemMeta(meta);

    }

    static {
        for (int i=0; i <= 13; i++) {
            List<Component> loreList = new ArrayList<>();
            loreList.add(lore[i]);
            legend[i].lore(loreList);
            ItemMeta meta = legend[i].getItemMeta();
            meta.displayName(name);
            legend[i].setItemMeta(meta);
        }

        PotionMeta potionMeta = (PotionMeta) legend[10].getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 1200, 10), true);
        potionMeta.setColor(Color.YELLOW);
        legend[10].setItemMeta(potionMeta);

        ItemMeta meta9 = legend[9].getItemMeta();
        meta9.addEnchant(Enchantment.LOYALTY, 10, true);
        legend[9].setItemMeta(meta9);

        FireworkMeta fireworkMeta = (FireworkMeta) legend[12].getItemMeta();
        fireworkMeta.addEffect(
                FireworkEffect.builder()
                .with(FireworkEffect.Type.CREEPER)
                .flicker(true)
                .trail(true)
                .withColor(Color.ORANGE)
                .withColor(Color.RED)
                .withColor(Color.YELLOW)
                .withColor(Color.WHITE)
                .withFade(Color.LIME)
                .build());
        legend[12].setItemMeta(fireworkMeta);

    }

}
