package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomKnockbackEvent;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.UtilVelocity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class HandleCustomDamageKnockback extends SpigotListener<Core, DamageManager> {

    public HandleCustomDamageKnockback(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.hasKnockback())) {
            return;
        }

        if (!(event.hasDamager())) {
            return;
        }

        if (event.getDamagee().equals(event.getDamager())) {
            return;
        }

        final CustomKnockbackEvent knockbackEvent = new CustomKnockbackEvent(event);
        UtilServer.callEvent(knockbackEvent);
        if (knockbackEvent.isCancelled()) {
            return;
        }

        final Vector vector = this.getVector(event, knockbackEvent);

        event.getDamagee().setVelocity(vector);
    }

    private Vector getVector(final CustomPostDamageEvent damageEvent, final CustomKnockbackEvent knockbackEvent) {
        final Entity damagee = damageEvent.getDamagee();
        final Entity damager = damageEvent.getDamager();

        double baseKnockback = damageEvent.getDamage();

        if (damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && damager instanceof Player && damageEvent.getDamagerByClass(Player.class).isSprinting()) {
            baseKnockback += 2.0D;
        }

        baseKnockback = 0.5D + (baseKnockback * 0.2D);

        final Vector vector = UtilVelocity.getTrajectory(damager.getLocation().toVector(), damagee.getLocation().toVector());

        final double finalKnockback = baseKnockback * knockbackEvent.getKnockback();

        vector.multiply(finalKnockback);

        vector.setY(0.4D);

        return vector;
    }
}