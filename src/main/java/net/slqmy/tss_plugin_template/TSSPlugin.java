package net.slqmy.tss_plugin_template;

import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import net.kyori.adventure.text.Component;
import net.slqmy.tss_core.TSSCorePlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SpongeAbsorbEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public final class TSSPlugin extends JavaPlugin implements Listener {

    private final TSSCorePlugin core = (TSSCorePlugin) Bukkit.getPluginManager().getPlugin("TSS-Core");
    private final Util util = new Util(this, core);
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.GLOW_BERRIES) {
            Player player = event.getPlayer();
            PotionEffect glowing = new PotionEffect(PotionEffectType.GLOWING, 5 * 20, 0, true, true, false);

            player.addPotionEffect(glowing);
        }
    }

    @EventHandler
    public void onSnowmanHurt(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Snowman golem && event.getDamager() instanceof LivingEntity) {
            golem.setTarget((LivingEntity) event.getDamager());
        }
    }

    @EventHandler
    public void onPiglinCrossBowCharge(EntityLoadCrossbowEvent event) {
        if (!(event.getEntity() instanceof Piglin piglin)) {
            return;
        }

        if (!(util.randomChance(10.0))) return;

        ItemStack crossbow = piglin.getActiveItem();
        CrossbowMeta meta = (CrossbowMeta) crossbow.getItemMeta();

        ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET);
        FireworkMeta fireworkMeta = (FireworkMeta) firework.getItemMeta();

        FireworkEffect orangeExplosion = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.ORANGE)
                .withFade(Color.WHITE)
                .withTrail()
                .build();

        FireworkEffect redExplosion = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.RED)
                .withFade(Color.WHITE)
                .withTrail()
                .build();

        fireworkMeta.addEffect(orangeExplosion);
        fireworkMeta.addEffect(redExplosion);
        fireworkMeta.setPower(1);

        firework.setItemMeta(fireworkMeta);

        meta.setChargedProjectiles(List.of(firework));

        piglin.getActiveItem().setItemMeta(meta);
        event.setCancelled(true);
    }

    @EventHandler
    public void onSpongeAbsorbLava(SpongeAbsorbEvent event) {

        Block sponge = event.getBlock();

        boolean hasDrainedLava = sponge.getWorld().getBlockAt(event.getBlocks().get(0).getLocation()).getType().equals(Material.LAVA);

        if (hasDrainedLava) {
            sponge.getWorld().playSound(sponge.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1.0F, 1.0F);
            sponge.getWorld().spawnParticle(Particle.LARGE_SMOKE, sponge.getLocation().add(0.5, 0.5, 0.5), 10, 0.5, 0.2, 0.5, 0);
            sponge.setType(Material.DEAD_HORN_CORAL_BLOCK);
        }
    }

    @EventHandler
    public void onSquidHurt(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Squid squid)) return;
        if (event.getEntity() instanceof GlowSquid) return;

        Vector facingVector = squid.getLocation().getDirection().clone().multiply(-1);
        Location inkLocation = squid.getLocation().add(facingVector);

        AreaEffectCloud[] clouds = new AreaEffectCloud[] {
            (AreaEffectCloud) squid.getWorld().spawnEntity(inkLocation.clone().subtract(0, 0.3, 0), EntityType.AREA_EFFECT_CLOUD),
            (AreaEffectCloud) squid.getWorld().spawnEntity(inkLocation, EntityType.AREA_EFFECT_CLOUD),
            (AreaEffectCloud) squid.getWorld().spawnEntity(inkLocation.clone().add(0, 0.3, 0), EntityType.AREA_EFFECT_CLOUD)
        };

        for (AreaEffectCloud cloud : clouds) {
            cloud.setColor(Color.BLACK);
            cloud.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8 * 20, 0), true);
            cloud.setDuration(15);
            cloud.setParticle(Particle.SQUID_INK);
            cloud.setRadius(0.8F);
        }

        new BukkitRunnable() {
            private int ticksCount = 0;
            @Override
            public void run() {

                if (ticksCount >= 15) cancel();

                Vector facingVector = squid.getLocation().getDirection().clone().multiply(util.randomDouble(-1.0, -0.5));
                Location inkLocation = squid.getLocation().add(facingVector);

                for (AreaEffectCloud cloud : clouds) {
                    cloud.teleport(inkLocation.clone().add(0, 0.3, 0).subtract(0, Arrays.stream(clouds).toList().indexOf(cloud) * 0.3, 0));
                }
                ticksCount++;
            }
        }.runTaskTimer(this, 0L, 1L);
        
    }

    @EventHandler
    public void onGlowSquidHurt(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof final GlowSquid glowSquid)) return;

        Vector facingVector = glowSquid.getLocation().getDirection().clone().multiply(-1);
        Location inkLocation = glowSquid.getLocation().add(facingVector);

        AreaEffectCloud[] clouds = new AreaEffectCloud[]{
                (AreaEffectCloud) glowSquid.getWorld().spawnEntity(inkLocation.clone().subtract(0, 0.3, 0), EntityType.AREA_EFFECT_CLOUD),
                (AreaEffectCloud) glowSquid.getWorld().spawnEntity(inkLocation, EntityType.AREA_EFFECT_CLOUD),
                (AreaEffectCloud) glowSquid.getWorld().spawnEntity(inkLocation.clone().add(0, 0.3, 0), EntityType.AREA_EFFECT_CLOUD)
        };

        for (AreaEffectCloud cloud : clouds) {
            cloud.setColor(Color.AQUA);
            cloud.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 8 * 20, 0), true);
            cloud.setDuration(15);
            cloud.setParticle(Particle.GLOW_SQUID_INK);
            cloud.setRadius(0.8F);
        }

        new BukkitRunnable() {
            private int ticksCount = 0;

            @Override
            public void run() {

                if (ticksCount >= 15) cancel();

                Vector facingVector = glowSquid.getLocation().getDirection().clone().multiply(util.randomDouble(-1.0, -0.5));
                Location inkLocation = glowSquid.getLocation().add(facingVector);

                for (AreaEffectCloud cloud : clouds) {
                    cloud.teleport(inkLocation.clone().add(0, 0.3, 0).subtract(0, Arrays.stream(clouds).toList().indexOf(cloud) * 0.3, 0));
                    util.logToServer(cloud.getColor().toString());
                }
                ticksCount++;
            }
        }.runTaskTimer(this, 0L, 1L);
    }

    @EventHandler
    public void onPlayerReelIn(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)) {
            Entity caughtEntity = event.getCaught();

            assert caughtEntity != null;
            util.logToServer("We have caught " + caughtEntity.getClass());

            FishHook hook = event.getHook();
            Player player = event.getPlayer();
            Damageable rod = (Damageable) event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

            Vector direction = player.getLocation().toVector().subtract(hook.getLocation().toVector());

            direction.setX(Math.sqrt(Math.abs(direction.getX())) * Math.signum(direction.getX()));
            direction.setY(Math.cbrt(Math.abs(direction.getY())) * Math.signum(direction.getY()));
            direction.setZ(Math.sqrt(Math.abs(direction.getZ())) * Math.signum(direction.getZ()));

            caughtEntity.setVelocity(direction);
            rod.setDamage(1000);

        }
    }

    @EventHandler
    public void onSlimeFall(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && event.getEntity() instanceof Slime) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPhantomSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Phantom phantom) {

            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta bowMeta = bow.getItemMeta();

            bowMeta.addEnchant(Enchantment.POWER, 3, false);
            bowMeta.addEnchant(Enchantment.PUNCH, 1, false);

            bow.setItemMeta(bowMeta);

            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();

            chestplateMeta.setColor(Color.BLUE);
            chestplateMeta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, false);

            Skeleton skeleton = (Skeleton) phantom.getWorld().spawnEntity(phantom.getLocation(), EntityType.SKELETON);
            skeleton.customName(Component.text("Phantom Archer"));
            skeleton.getEquipment().setItemInMainHand(bow);
            skeleton.getEquipment().setChestplate(chestplate);
            skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
            skeleton.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(32);

            phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
            phantom.addPassenger(skeleton);
        }
    }
}
