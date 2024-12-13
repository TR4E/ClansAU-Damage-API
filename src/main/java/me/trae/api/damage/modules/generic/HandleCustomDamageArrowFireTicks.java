package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class HandleCustomDamageArrowFireTicks extends SpigotListener<Core, DamageManager> {

    public HandleCustomDamageArrowFireTicks(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }

        if (!(event.getProjectile() instanceof Arrow)) {
            return;
        }

        final Arrow arrow = event.getProjectileByClass(Arrow.class);

        if (arrow.getFireTicks() <= 0) {
            return;
        }

        event.getDamagee().setFireTicks(arrow.getFireTicks());
    }
}