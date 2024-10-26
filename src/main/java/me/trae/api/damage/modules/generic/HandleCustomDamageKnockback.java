package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.events.CustomKnockbackEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilServer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.util.Vector;

public class HandleCustomDamageKnockback extends SpigotListener<Core, DamageManager> {

    public HandleCustomDamageKnockback(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.hasKnockback())) {
            return;
        }

        if (!(event.hasDamager())) {
            return;
        }

        final Entity damager = event.getDamager();
        final Entity damagee = event.getDamagee();

        if (damager == damagee) {
            return;
        }

        final CustomKnockbackEvent knockbackEvent = new CustomKnockbackEvent(event);
        UtilServer.callEvent(knockbackEvent);
        if (knockbackEvent.isCancelled()) {
            return;
        }

        final Vector vector = damagee.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();

        vector.multiply(knockbackEvent.getKnockback());
        vector.setY(0.5D);

        damagee.setVelocity(vector);
    }
}