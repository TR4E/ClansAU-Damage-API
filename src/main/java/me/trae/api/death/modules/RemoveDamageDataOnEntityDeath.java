package me.trae.api.death.modules;

import me.trae.api.damage.DamageManager;
import me.trae.api.death.DeathManager;
import me.trae.api.death.events.CustomDeathEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class RemoveDamageDataOnEntityDeath extends SpigotListener<Core, DeathManager> {

    public RemoveDamageDataOnEntityDeath(final DeathManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDeath(final CustomDeathEvent event) {
        this.getInstanceByClass().getManagerByClass(DamageManager.class).removeDamageData(event.getEntity());
    }
}