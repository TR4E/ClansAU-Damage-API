package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomDamageEvent;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.damage.events.damage.CustomPreDamageEvent;
import me.trae.api.damage.utility.UtilDamage;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilServer;
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

        if (!(this.isPreValid(event))) {
            return;
        }

        event.setCancelled(true);

        if (!(this.isPostValid(event))) {
            return;
        }

        final CustomPreDamageEvent customPreDamageEvent = this.getCustomDamageEvent(event);

        if (this.isInvulnerable(customPreDamageEvent)) {
            return;
        }

        UtilServer.callEvent(customPreDamageEvent);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPreDamage(final CustomPreDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        UtilServer.callEvent(new CustomDamageEvent(event));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        UtilServer.callEvent(new CustomPostDamageEvent(event));
    }

    private CustomPreDamageEvent getCustomDamageEvent(final EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent entityDamageByEntityEvent = UtilJava.cast(EntityDamageByEntityEvent.class, entityDamageEvent);

            if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
                final Projectile projectile = UtilJava.cast(Projectile.class, entityDamageByEntityEvent.getDamager());

                if (projectile instanceof Arrow) {
                    projectile.remove();
                }

                return new CustomPreDamageEvent(entityDamageByEntityEvent, projectile);
            }

            return new CustomPreDamageEvent(entityDamageByEntityEvent);
        }

        return new CustomPreDamageEvent(entityDamageEvent);
    }

    private boolean isPreValid(final EntityDamageEvent entityDamageEvent) {
        final Entity entity = entityDamageEvent.getEntity();

        if (entity instanceof FishHook) {
            return false;
        }

        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent entityDamageByEntityEvent = UtilJava.cast(EntityDamageByEntityEvent.class, entityDamageEvent);

            final Entity damager = entityDamageByEntityEvent.getDamager();

            if (damager instanceof FishHook) {
                return false;
            }
        }

        return true;
    }

    private boolean isPostValid(final EntityDamageEvent entityDamageEvent) {
        final Entity entity = entityDamageEvent.getEntity();

//        if (entity instanceof Fireball) {
//            return false;
//        }

        return true;
    }

    private boolean isInvulnerable(final CustomPreDamageEvent event) {
        final Entity damagee = event.getDamagee();
        final Entity damager = event.getDamager();

        if (damager != null && damager == damagee) {
            return true;
        }

        if (!(Arrays.asList(EntityDamageEvent.DamageCause.SUICIDE, EntityDamageEvent.DamageCause.VOID).contains(event.getCause()))) {
            if (UtilDamage.isInvulnerable(event.getDamagee())) {
                return true;
            }
        }

        return false;
    }
}