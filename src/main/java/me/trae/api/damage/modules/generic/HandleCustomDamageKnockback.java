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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.util.Vector;

public class HandleCustomDamageKnockback extends SpigotListener<Core, DamageManager> {

    @ConfigInject(type = Double.class, path = "Pre-Y", defaultValue = "0.0")
    private double preY;

    @ConfigInject(type = Double.class, path = "Post-Y", defaultValue = "0.4")
    private double postY;

    @ConfigInject(type = Double.class, path = "Base-Strength", defaultValue = "0.4")
    private double baseStrength;

    @ConfigInject(type = Double.class, path = "Sprint-Strength-Addition", defaultValue = "0.6")
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

        final Vector vector = this.getVector(damagee, damager);

        damagee.setVelocity(vector);
    }

    private Vector getVector(final Entity damagee, final Entity damager) {
        final Vector vector = damagee.getLocation().toVector().subtract(damager.getLocation().toVector());

        vector.setY(this.preY);
        vector.normalize();

        vector.multiply(this.getStrength(damager));

        vector.setY(this.postY);

        return vector;
    }

    private double getStrength(final Entity damager) {
        double strength = this.baseStrength;

        if (damager instanceof Player && UtilJava.cast(Player.class, damager).isSprinting()) {
            strength += this.sprintStrengthAddition;
        }

        return strength;
    }
}