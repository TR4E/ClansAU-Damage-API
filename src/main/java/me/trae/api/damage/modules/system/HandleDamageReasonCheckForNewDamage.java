package me.trae.api.damage.modules.system;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleDamageReasonCheckForNewDamage extends SpigotListener<Core, DamageManager> {

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

        final DamageReason lastDamageReason = this.getManager().getLastReasonByDamagee(damagee, damager);
        if (lastDamageReason == null) {
            return;
        }

        final DamageReason damageReason = event.getReason();
        if (damageReason == null || damageReason.getName().equals(lastDamageReason.getName())) {
            return;
        }

        if (lastDamageReason.getDuration() == -1L) {
            return;
        }

        System.out.println("Removed reason");

        this.getManager().removeLastReason(damagee, damager);
    }
}