package me.trae.api.damage.events.interfaces;

import me.trae.core.utility.UtilJava;
import me.trae.core.utility.components.GetSystemTimeComponent;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;

public interface ICustomDamageEvent extends GetSystemTimeComponent {

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

    long getDelay();

    void setDelay(final long delay);

    default boolean hasDelay() {
        return this.getDelay() > 0L;
    }

    double getDamage();

    void setDamage(final double damage);

    double getFinalDamage();

    default boolean hasDamage() {
        return this.getDamage() > 0.0D;
    }

    double getKnockback();

    void setKnockback(final double knockback);

    default boolean isKnockback() {
        return this.getKnockback() > 0.0D;
    }

    SoundCreator getSoundCreator();

    void setSoundCreator(final SoundCreator soundCreator);

    String getDamageeName();

    void setDamageeName(final String damageeName);

    String getDamagerName();

    void setDamagerName(final String damagerName);

    String getProjectileName();
}