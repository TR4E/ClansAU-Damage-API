package me.trae.api.damage.modules;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.utility.UtilDamage;
import me.trae.api.damage.utility.constants.DamageConstants;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class HandlePreEntityDamage extends SpigotListener<Core, DamageManager> {

    public HandlePreEntityDamage(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getEntity() instanceof Damageable)) {
            return;
        }

        final CustomDamageEvent customDamageEvent = this.getCustomDamageEvent(event);

        if (this.isInvulnerable(customDamageEvent)) {
            return;
        }

        UtilServer.callEvent(customDamageEvent);
        if (customDamageEvent.isCancelled()) {
            return;
        }

        this.handleDamageSound(customDamageEvent);
        this.handleKnockback(customDamageEvent);

        UtilDamage.applyVanillaDamageMechanics(customDamageEvent);

        if (customDamageEvent.getDamager() != null) {
            this.getManager().addLastDamageData(customDamageEvent);
        }
    }

    private CustomDamageEvent getCustomDamageEvent(final EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent entityDamageByEntityEvent = UtilJava.cast(EntityDamageByEntityEvent.class, entityDamageEvent);

            if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
                return new CustomDamageEvent(entityDamageByEntityEvent, UtilJava.cast(Projectile.class, entityDamageByEntityEvent.getDamager()));
            }

            return new CustomDamageEvent(entityDamageByEntityEvent);
        }

        return new CustomDamageEvent(entityDamageEvent);
    }

    private boolean isInvulnerable(final CustomDamageEvent event) {
        final Entity damagee = event.getDamagee();
        final Entity damager = event.getDamager();

        if (damager != null && damager == damagee) {
            return true;
        }

        if (damagee instanceof Player) {
            final Player damageePlayer = event.getDamageeByClass(Player.class);

            if (!(Arrays.asList(EntityDamageEvent.DamageCause.SUICIDE, EntityDamageEvent.DamageCause.VOID).contains(event.getCause()))) {
                if (Arrays.asList(GameMode.CREATIVE, GameMode.SPECTATOR).contains(damageePlayer.getGameMode())) {
                    return true;
                }

                if (UtilJava.cast(CraftPlayer.class, damageePlayer).getHandle().isSpectator()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void handleDamageSound(final CustomDamageEvent event) {
        SoundCreator soundCreator = event.getSoundCreator();
        if (soundCreator == null) {
            return;
        }

        final Entity damagee = event.getDamagee();

        if (soundCreator.getSound() == Sound.HURT_FLESH) {
            final Sound sound = (event.getDamageeByClass(Damageable.class).getHealth() > 0.0D ? DamageConstants.getEntityHurtSound(damagee) : DamageConstants.getEntityDeathSound(damagee));
            if (sound != null) {
                soundCreator = new SoundCreator(sound);
            }
        }

        soundCreator.play(damagee.getLocation());
    }

    private void handleKnockback(final CustomDamageEvent event) {
        if (!(event.isKnockback())) {
            return;
        }

        final Entity damager = event.getDamager();
        if (damager == null) {
            return;
        }

        final Entity damagee = event.getDamagee();

        if (damager == damagee) {
            return;
        }

        final Vector velocity = damagee.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();

        velocity.multiply(event.getKnockback());
        velocity.setY(0.5D);

        damagee.setVelocity(velocity);
    }
}