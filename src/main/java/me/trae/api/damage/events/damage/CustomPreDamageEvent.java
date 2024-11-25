package me.trae.api.damage.events.damage;

import me.trae.api.damage.events.damage.abstracts.DamageEvent;
import me.trae.core.utility.UtilJava;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomPreDamageEvent extends DamageEvent {

    public CustomPreDamageEvent(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage) {
        super(damagee, damager, null, cause, damage);
    }

    public CustomPreDamageEvent(final Entity damagee, final EntityDamageEvent.DamageCause cause, final double damage) {
        super(damagee, null, null, cause, damage);
    }

    public CustomPreDamageEvent(final EntityDamageEvent event) {
        super(event.getEntity(), null, null, event.getCause(), event.getDamage());
    }

    public CustomPreDamageEvent(final EntityDamageByEntityEvent event) {
        super(event.getEntity(), event.getDamager(), null, event.getCause(), event.getDamage());
    }

    public CustomPreDamageEvent(final EntityDamageByEntityEvent event, final Projectile projectile) {
        super(event.getEntity(), UtilJava.cast(Entity.class, projectile.getShooter()), projectile, EntityDamageEvent.DamageCause.PROJECTILE, event.getDamage());
    }
}