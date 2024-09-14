package me.trae.api.damage.modules;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.framework.SpigotPlugin;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleDamageReasonCheckForNewDamage extends SpigotListener<SpigotPlugin, DamageManager> {

    public HandleDamageReasonCheckForNewDamage(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final Entity damager = event.getDamager();
        if (damager == null) {
            return;
        }

        final Entity damagee = event.getDamagee();

        final DamageReason damageReason = this.getManager().getReasonByEntity(damager, damagee);
        if (damageReason == null) {
            return;
        }

        final DamageReason reason = event.getReason();
        if (reason != null && damageReason.getName().equals(reason.getName())) {
            return;
        }

        if (damageReason.getDuration() == -1L) {
            return;
        }

        this.getManager().removeReason(damager, damagee);
    }
}