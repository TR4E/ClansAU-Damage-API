package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilServer;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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

        if (!(this.isValid(event))) {
            return;
        }

        event.setCancelled(true);

        final CustomDamageEvent customDamageEvent = this.getCustomDamageEvent(event);

        if (this.isInvulnerable(customDamageEvent)) {
            return;
        }

        UtilServer.callEvent(customDamageEvent);
    }

    private CustomDamageEvent getCustomDamageEvent(final EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent entityDamageByEntityEvent = UtilJava.cast(EntityDamageByEntityEvent.class, entityDamageEvent);

            if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
                final Projectile projectile = UtilJava.cast(Projectile.class, entityDamageByEntityEvent.getDamager());

                if (projectile instanceof Arrow) {
                    projectile.remove();
                }

                return new CustomDamageEvent(entityDamageByEntityEvent, projectile);
            }

            return new CustomDamageEvent(entityDamageByEntityEvent);
        }

        return new CustomDamageEvent(entityDamageEvent);
    }

    private boolean isValid(final EntityDamageEvent entityDamageEvent) {
        final Entity entity = entityDamageEvent.getEntity();

        if (entity instanceof FishHook) {
            return false;
        }

        if (entity instanceof Fireball) {
            return false;
        }

        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent entityDamageByEntityEvent = UtilJava.cast(EntityDamageByEntityEvent.class, entityDamageEvent);

            final Entity damager = entityDamageByEntityEvent.getDamager();

            if (damager instanceof FishHook) {
                return false;
            }

            if (damager instanceof Fireball) {
                return false;
            }
        }

        return true;
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
}