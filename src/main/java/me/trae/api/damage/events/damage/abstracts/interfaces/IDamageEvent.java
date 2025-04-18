package me.trae.api.damage.events.damage.abstracts.interfaces;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.core.Core;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilPlugin;
import me.trae.core.utility.components.time.GetSystemTimeComponent;
import me.trae.core.utility.components.time.SetSystemTimeComponent;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public interface IDamageEvent extends GetSystemTimeComponent, SetSystemTimeComponent {

    Entity getDamagee();

    default <E extends Entity> E getDamageeByClass(final Class<E> clazz) {
        return UtilJava.cast(clazz, this.getDamagee());
    }

    Entity getDamager();

    default <E extends Entity> E getDamagerByClass(final Class<E> clazz) {
        return UtilJava.cast(clazz, this.getDamager());
    }

    default boolean hasDamager() {
        return this.getDamager() != null;
    }

    Projectile getProjectile();

    default <E extends Projectile> E getProjectileByClass(final Class<E> clazz) {
        return UtilJava.cast(clazz, this.getProjectile());
    }

    default boolean hasProjectile() {
        return this.getProjectile() != null;
    }

    EntityDamageEvent.DamageCause getCause();

    String getCauseString();

    String getReasonString();

    ItemStack getItemStack();

    double getDamage();

    void setDamage(final double damage);

    default boolean hasDamage() {
        return this.getDamage() > 0.0D;
    }

    double getKnockback();

    void setKnockback(final double knockback);

    default boolean hasKnockback() {
        return this.getKnockback() > 0.0D;
    }

    long getDelay();

    void setDelay(final long delay);

    default boolean hasDelay() {
        return this.getDelay() > 0L;
    }

    SoundCreator getSoundCreator();

    void setSoundCreator(final SoundCreator soundCreator);

    default boolean hasSoundCreator() {
        return this.getSoundCreator() != null;
    }

    DamageReason getReason();

    void setReason(final DamageReason reason);

    void setReason(final String name, final long duration);

    default void setLightReason(final DamageReason reason) {
        final DamageReason damageReason = UtilPlugin.getInstanceByClass(Core.class).getManagerByClass(DamageManager.class).getLastReasonByDamagee(this.getDamagee(), this.getDamager());
        if (damageReason != null && !(damageReason.getName().contains(reason.getName())) && !(damageReason.hasExpired())) {
            return;
        }

        this.setReason(reason.getName(), reason.getDuration());
    }

    default void setLightReason(final String name, final long duration) {
        this.setLightReason(new DamageReason(name, duration));
    }

    default boolean hasReason() {
        return this.getReason() != null;
    }

    String getDamageeName();

    void setDamageeName(final String damageeName);

    String getDamagerName();

    void setDamagerName(final String damagerName);
}