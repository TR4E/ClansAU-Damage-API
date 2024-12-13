package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomKnockbackEvent;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
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

        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            return;
        }

        final CustomKnockbackEvent knockbackEvent = new CustomKnockbackEvent(event);
        UtilServer.callEvent(knockbackEvent);
        if (knockbackEvent.isCancelled()) {
            return;
        }

        if (!(knockbackEvent.hasKnockback())) {
            return;
        }

        this.apply(event, knockbackEvent);
    }

    private void apply(final CustomPostDamageEvent damageEvent, final CustomKnockbackEvent knockbackEvent) {
        double knockback = knockbackEvent.getKnockback();

        final Entity damagee = damageEvent.getDamagee();

        if (damagee instanceof Player) {
            if (UtilJava.cast(Player.class, damagee).isSprinting() && damageEvent.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                knockback += 3.0D;
            }
        }

        if (knockback < 2.0D) {
            knockback = 2.0D;
        }

        knockback = Math.log10(knockback);

        final Vector trajectory = UtilVelocity.getTrajectory2d(damageEvent.getDamager().getLocation().toVector(), damageEvent.getDamagee().getLocation().toVector());

        trajectory.multiply(0.8D * knockback);

        trajectory.setY(Math.abs(trajectory.getY()));

        UtilVelocity.velocity(damagee, trajectory, 0.3D + trajectory.length() * 0.8D, 0.0D, Math.abs(0.2D * knockback), 0.4D + 0.04D * knockback, true);
    }
}