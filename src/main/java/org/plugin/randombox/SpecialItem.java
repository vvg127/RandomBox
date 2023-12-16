package org.plugin.randombox;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SpecialItem implements Listener {

    private final RandomBox plugin;

    public SpecialItem(RandomBox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();

        if (p.getGameMode() == GameMode.CREATIVE) {
            p.setAllowFlight(true);
            return;
        }

        if (pi.getBoots() == null) {
            p.setAllowFlight(false);
            return;
        }

        ItemStack item = pi.getBoots();
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = meta.lore();
        if (lore == null) {return;}

        if (p.getGameMode() == GameMode.SURVIVAL && p.getLocation().getBlock().getRelative(0, -1, 0).getType().isSolid()) {
            p.setAllowFlight(lore.contains(BoxItem.lore[5]));
        }
    }

    @EventHandler
    public void DoubleJump(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();

        if (p.getGameMode() != GameMode.SURVIVAL) {return;}

        if (pi.getBoots() == null) {return;}

        ItemStack item = pi.getBoots();
        ItemMeta meta = item.getItemMeta();

        List<Component> lore = meta.lore();
        if (lore == null) {return;}

        if (lore.contains(BoxItem.lore[5])) {
            if (e.isFlying()) {
                e.setCancelled(true);
                p.setAllowFlight(false);

                Location playerLocation = p.getLocation();
                playerLocation.setPitch(0.0F);
                Vector direction = playerLocation.getDirection().multiply(1.5).add(new Vector(0, 0.75, 0));

                p.setVelocity(direction);
            }
        }
    }

    @EventHandler
    public void LightingBolt(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[1])) {
                Entity entity = e.getEntity();
                Location location = entity.getLocation();
                World world = entity.getWorld();

                world.spawn(location, LightningStrike.class);
            }
        }
    }

    @EventHandler
    public void Vampire(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[0])) {

                if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) {return;}

                if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() > p.getHealth()) {
                    p.setHealth(p.getHealth() + e.getFinalDamage() / 2);
                } else {p.setAbsorptionAmount(p.getAbsorptionAmount() + 2);}

            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {

        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {return;}

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[6])) {
                if (e.getEntityType() == EntityType.ARROW) {
                    Arrow arrow = (Arrow) e.getEntity();
                    Location arrowLocation = arrow.getLocation();
                    World world = arrowLocation.getWorld();

                    world.createExplosion(arrowLocation, 5.0F, false);
                    arrow.remove();
                }
            }
        }
    }

    @EventHandler
    public void onEntityHit(ProjectileHitEvent e) {

        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[6])) {

                Entity hitEntity = e.getHitEntity();
                if (hitEntity != null && hitEntity.getType() != EntityType.PLAYER) {
                    Location entityLocation = hitEntity.getLocation();
                    World world = entityLocation.getWorld();
                    world.createExplosion(entityLocation, 5.0F, true);
                } else if (hitEntity instanceof Player) {
                    Location playerLocation = hitEntity.getLocation();
                    World world = playerLocation.getWorld();

                    world.createExplosion(playerLocation, 3.0F, true);
                }
            }
        }
    }

    @EventHandler
    public void RainArrow(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {return;}

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[7])) {

                if (e.getEntityType() == EntityType.ARROW) {
                    Arrow arrow = (Arrow) e.getEntity();

                    int interval = 1;
                    int totalIterations = 50;

                    BukkitRunnable task = new BukkitRunnable() {
                        int iteration = 0;

                        @Override
                        public void run() {
                            if (iteration >= totalIterations) {
                                cancel();
                                return;
                            }

                            for (int i = 0; i <= 5; i++) {
                                Location randomLocation = randomLocation(arrow, 15, 10);
                                World world = arrow.getWorld();

                                world.spawnEntity(randomLocation, EntityType.ARROW);}

                            iteration++;

                        }
                    };

                    task.runTaskTimer(plugin, interval, interval);

                }
            }

        }
    }

    private Location randomLocation(Entity entity, double Distance, double offsetY) {
        Location baseLocation = entity.getLocation();
        double angle = Math.random() * Math.PI * 2;
        double distance = Math.random() * Distance;

        double offsetX = Math.cos(angle) * distance;
        double offsetZ = Math.sin(angle) * distance;

        return baseLocation.clone().add(offsetX, offsetY, offsetZ);
    }

    @EventHandler
    public void PotionAdd(PlayerArmorChangeEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getNewItem();

        if (item == null) {
            if (e.getSlotType() == PlayerArmorChangeEvent.SlotType.HEAD) {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            if (e.getSlotType() == PlayerArmorChangeEvent.SlotType.HEAD) {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            return;
        }

        List<Component> lore = meta.lore();
        if (lore == null) {
            if (e.getSlotType() == PlayerArmorChangeEvent.SlotType.HEAD) {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            return;
        }

        if (e.getSlotType() == PlayerArmorChangeEvent.SlotType.HEAD) {
            if (lore.contains(BoxItem.lore[2])) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0));
            } else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        }
    }

    @EventHandler
    public void CrossCharge(EntityLoadCrossbowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[8])) {

                Component c = Component.text("남은 탄환 10개", BoxItem.crossStyle);

                if (lore.size() < 2) {
                    lore.add(c);
                } else {
                    lore.set(1, c);
                }

                meta.lore(lore);
                item.setItemMeta(meta);

            }
        }
    }

    @EventHandler
    public void CrossShot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {

            if (e.getBow().getType() != Material.CROSSBOW) {return;}

            Player p = (Player) e.getEntity();
            PlayerInventory pi = p.getInventory();

            ItemStack item = pi.getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            CrossbowMeta crossMeta = (CrossbowMeta) item.getItemMeta();

            List<Component> lore = meta.lore();
            if (lore == null) {return;}

            if (lore.contains(BoxItem.lore[8])) {
                if (getLeftShot(lore) != 0) {
                    lore.set(1, Component.text("남은 탄환 " + (getLeftShot(lore) - 1) + "개", BoxItem.crossStyle));

                    List<ItemStack> list = new ArrayList<>();
                    list.add(new ItemStack(Material.ARROW));

                    crossMeta.lore(lore);
                    crossMeta.setChargedProjectiles(list);

                    ItemStack item2 = new ItemStack(item);
                    item2.setItemMeta(crossMeta);

                    pi.setItemInMainHand(item2);

                }
            }
        }
    }

    private int getLeftShot(List<Component> list) {
        Component component = list.get(1);

        for (int i = 0; i != 11; i++) {
            Component component1 = Component.text("남은 탄환 " + i + "개", BoxItem.crossStyle);
            if (component.equals(component1)) {return i;}
        }
        return 0;
    }

    @EventHandler
    public void CoolTime(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();

            if (arrow.getShooter() instanceof Player) {
                Player p = (Player) arrow.getShooter();
                PlayerInventory pi = p.getInventory();

                ItemStack item = pi.getItemInMainHand();
                ItemMeta meta = item.getItemMeta();

                List<Component> lore = meta.lore();
                if (lore == null) {return;}

                if (lore.contains(BoxItem.lore[8])) {
                    double damage = e.getFinalDamage();

                    if (e.getEntity() instanceof LivingEntity) {
                        LivingEntity entity = (LivingEntity) e.getEntity();
                        if (entity.isDead()) {return;}
                        e.setCancelled(true);
                        entity.damage(damage);
                        entity.setNoDamageTicks(0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void Trident(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Trident) {
            Trident trident = (Trident) e.getEntity();

            if (e.getHitEntity() == null) {return;}

            if (e.getHitEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) e.getHitEntity();

                if (trident.getLoyaltyLevel() == 10) {

                    BukkitRunnable task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (trident.isDead() || entity.isDead()) {
                                cancel();
                                return;
                            }

                            entity.teleport(trident.getLocation());

                        }
                    };

                    task.runTaskTimer(plugin,0,1);
                }
            }
        }
    }

    @EventHandler
    public void RocketCharge(EntityLoadCrossbowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerInventory pi = p.getInventory();

            ItemStack main = pi.getItemInMainHand();
            ItemMeta metaMain = main.getItemMeta();

            List<Component> loreMain = metaMain.lore();
            if (loreMain == null) {return;}

            if (loreMain.contains(BoxItem.lore[11])) {
                e.setCancelled(true);

                ItemStack off = pi.getItemInOffHand();
                ItemMeta metaOff = off.getItemMeta();
                if (metaOff == null) {return;}

                List<Component> loreOff = metaOff.lore();
                if (loreOff == null) {return;}

                if (loreOff.contains(BoxItem.lore[12])) {
                    CrossbowMeta meta = (CrossbowMeta) main.getItemMeta();

                    ItemStack item = BoxItem.legend[12];
                    item.setAmount(1);
                    meta.addChargedProjectile(item);

                    main.setItemMeta(meta);

                    off.setAmount(off.getAmount() - 1);

                }
            }
        }
    }

    @EventHandler
    public void RocketShoot(EntityShootBowEvent e) {
        if (e.getBow() == null) {return;}
        if (e.getBow().getType() == Material.CROSSBOW) {
            if (e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                PlayerInventory pi = p.getInventory();

                ItemStack item = pi.getItemInMainHand();
                ItemMeta meta = item.getItemMeta();

                List<Component> lore = meta.lore();
                if (lore == null) {return;}

                if (lore.contains(BoxItem.lore[11])) {
                    if (e.getProjectile() instanceof Firework) {
                        Firework firework = (Firework) e.getProjectile();
                        Location location = firework.getLocation();

                        firework.setTicksToDetonate(100000000);
                        firework.addPassenger(p);

                        BukkitRunnable task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (firework.isDetonated() || firework.getPassengers().isEmpty()) {
                                    cancel();
                                    return;
                                }

                                location.setDirection(p.getLocation().getDirection());

                                firework.setVelocity(location.getDirection().multiply(2));


                            }
                        };

                        task.runTaskTimer(plugin, 0, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void RocketBOOM(FireworkExplodeEvent e) {
        Firework firework = e.getEntity();

        if (firework.getTicksToDetonate() == 100000000) {
            World world = firework.getWorld();

            world.createExplosion(firework.getLocation(), 100, true, true);
            world.createExplosion(firework.getLocation().add(0, -10, 0), 100, true, true);
            world.createExplosion(firework.getLocation().add(0, -20, 0), 100, true, true);
            world.createExplosion(firework.getLocation().add(0, -30, 0), 100, true, true);

            int totalIterations = 20;

            BukkitRunnable task = new BukkitRunnable() {

                int iteration = 0;

                @Override
                public void run() {

                    if (iteration >= totalIterations) {
                        cancel();
                        return;
                    }

                    for (int i = 0; i <= 5; i++) {
                        int randomY = (int) (Math.random() * 30);
                        if ((int) (Math.random() * 2) == 0) {randomY *= -1;}

                        world.createExplosion(randomLocation(firework, 50, randomY), 20, true, true);
                        TNTPrimed tnt = (TNTPrimed) world.spawnEntity(randomLocation(firework, 100, 10), EntityType.PRIMED_TNT);
                        tnt.setFuseTicks(60);
                        tnt.setYield(10);

                    }

                    iteration++;

                }
            };

            task.runTaskTimer(plugin, 0, 10);

        }
    }

    @EventHandler
    public void Hook(PlayerFishEvent e) {
        if (e.getHook().getState() == FishHook.HookState.UNHOOKED) {
            Player p = e.getPlayer();
            PlayerInventory pi = p.getInventory();


            ItemStack main = pi.getItemInMainHand();
            ItemMeta mainM = main.getItemMeta();
            List<Component> mainL = mainM.lore();
            if (mainL == null || main.getType() != Material.FISHING_ROD) {

                ItemStack off = pi.getItemInOffHand();
                ItemMeta offM = off.getItemMeta();
                List<Component> offL = offM.lore();
                if (offL == null || off.getType() != Material.FISHING_ROD) {return;}

            }

            FishHook hook = e.getHook();

            Location pLocation = p.getLocation();
            pLocation.setPitch(0.0F);
            Vector direction = pLocation.getDirection().multiply(1 + pLocation.distance(hook.getLocation())).add(new Vector(0, 0.75, 0));


            p.setVelocity(direction);




        }

    }



}
