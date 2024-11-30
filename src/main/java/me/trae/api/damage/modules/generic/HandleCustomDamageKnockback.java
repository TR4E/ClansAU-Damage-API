package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomKnockbackEvent;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.config.annotations.ConfigInject;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilServer;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleCustomDamageKnockback extends SpigotListener<Core, DamageManager> {

    @ConfigInject(type = Double.class, path = "Pre-Y", defaultValue = "0.0")
    private double preY;

    @ConfigInject(type = Double.class, path = "Post-Y", defaultValue = "0.4")
    private double postY;

    @ConfigInject(type = Double.class, path = "Sprint-Strength-Addition", defaultValue = "3.0")
    private double sprintStrengthAddition;

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

        final Entity damagee = event.getDamagee();
        final Entity damager = event.getDamager();

        if (damager == damagee) {
            return;
        }

        final CustomKnockbackEvent knockbackEvent = new CustomKnockbackEvent(event);
        UtilServer.callEvent(knockbackEvent);
        if (knockbackEvent.isCancelled()) {
            return;
        }

        UtilJava.call(damagee.getLocation().toVector().subtract(damager.getLocation().toVector()), vector -> {
            vector.normalize();

            vector.multiply(knockbackEvent.getKnockback());

            vector.setY(0.3D * knockbackEvent.getKnockback());

            damagee.setVelocity(vector);
        });
    }
}