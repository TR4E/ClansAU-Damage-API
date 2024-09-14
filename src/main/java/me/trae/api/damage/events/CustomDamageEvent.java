package me.trae.api.damage.events;

import me.trae.api.damage.events.interfaces.ICustomDamageEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.utility.UtilEntity;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class CustomDamageEvent extends CustomCancellableEvent implements ICustomDamageEvent {

    private final long systemTime;

    private final Entity damagee, damager;
    private final Projectile projectile;
    private final EntityDamageEvent.DamageCause cause;

    private long delay;
    private double damage, knockback;
    private SoundCreator soundCreator;
    private String damageeName, damagerName;

    private CustomDamageEvent(final Entity damagee, final Entity damager, final Projectile projectile, final EntityDamageEvent.DamageCause cause, final double damage) {
        this.systemTime = System.currentTimeMillis();

        this.damagee = damagee;
        this.damager = damager;
        this.projectile = projectile;
        this.cause = cause;

        this.damage = damage;
        this.knockback = 1.0D;
        this.soundCreator = new SoundCreator(Sound.HURT_FLESH);

        if (damagee != null) {
            this.damageeName = ChatColor.YELLOW + damagee.getName();
        }

        if (damager != null) {
            this.damagerName = ChatColor.YELLOW + damager.getName();
        }
    }

    public CustomDamageEvent(final EntityDamageEvent event) {
        this(event.getEntity(), null, null, event.getCause(), event.getFinalDamage());
    }

    public CustomDamageEvent(final EntityDamageByEntityEvent event) {
        this(event.getEntity(), event.getDamager(), null, event.getCause(), event.getFinalDamage());
    }

    public CustomDamageEvent(final EntityDamageByEntityEvent event, final Projectile projectile) {
        this(event.getEntity(), UtilJava.cast(Entity.class, projectile.getShooter()), projectile, event.getCause(), event.getFinalDamage());
    }

    @Override
    public long getSystemTime() {
        return this.systemTime;
    }

    @Override
    public Entity getDamagee() {
        return this.damagee;
    }

    @Override
    public Entity getDamager() {
        return this.damager;
    }

    @Override
    public Projectile getProjectile() {
        return this.projectile;
    }

    @Override
    public EntityDamageEvent.DamageCause getCause() {
        return this.cause;
    }

    @Override
    public long getDelay() {
        return this.delay;
    }

    @Override
    public void setDelay(final long delay) {
        this.delay = delay;
    }

    @Override
    public double getDamage() {
        return this.damage;
    }

    @Override
    public void setDamage(final double damage) {
        this.damage = damage;
    }

    @Override
    public double getFinalDamage() {
        double damage = this.getDamage();

        if (this.getDamager() instanceof LivingEntity) {
            final LivingEntity damager = this.getDamagerByClass(LivingEntity.class);

            if (UtilEntity.hasPotionEffect(damager, PotionEffectType.INCREASE_DAMAGE)) {
                final double strength = UtilEntity.getPotionEffectAmplifier(damager, PotionEffectType.INCREASE_DAMAGE) * 1.5D;

                damage += strength;
            }
        }

        if (this.getDamagee() instanceof LivingEntity) {
            final LivingEntity damagee = this.getDamageeByClass(LivingEntity.class);

            if (UtilEntity.hasPotionEffect(damagee, PotionEffectType.WEAKNESS)) {
                final double weakness = UtilEntity.getPotionEffectAmplifier(damagee, PotionEffectType.WEAKNESS) * 1.5D;

                damage += weakness;
            }
        }

        return damage;
    }

    @Override
    public double getKnockback() {
        return this.knockback;
    }

    @Override
    public void setKnockback(final double knockback) {
        this.knockback = knockback;
    }

    @Override
    public SoundCreator getSoundCreator() {
        return this.soundCreator;
    }

    @Override
    public void setSoundCreator(final SoundCreator soundCreator) {
        this.soundCreator = soundCreator;
    }

    @Override
    public String getDamageeName() {
        return this.damageeName;
    }

    @Override
    public void setDamageeName(final String damageeName) {
        this.damageeName = damageeName;
    }

    @Override
    public String getDamagerName() {
        return this.damagerName;
    }

    @Override
    public void setDamagerName(final String damagerName) {
        this.damagerName = damagerName;
    }

    @Override
    public String getProjectileName() {
        return this.hasProjectile() ? ChatColor.YELLOW + this.getProjectile().getName() : null;
    }
}