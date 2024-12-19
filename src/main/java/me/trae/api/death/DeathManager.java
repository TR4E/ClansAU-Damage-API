package me.trae.api.death;

import me.trae.api.death.modules.HandleCustomDeathMessage;
import me.trae.api.death.modules.HandleDeathSummaryMessage;
import me.trae.api.death.modules.RemoveDamageDataOnEntityDeath;
import me.trae.core.Core;
import me.trae.core.config.annotations.ConfigInject;
import me.trae.core.framework.SpigotManager;

public class DeathManager extends SpigotManager<Core> {

    @ConfigInject(type = String.class, path = "Custom-Reason-ChatColor", defaultValue = "GREEN")
    public String customReasonChatColor;

    public DeathManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleCustomDeathMessage(this));
        addModule(new HandleDeathSummaryMessage(this));
        addModule(new RemoveDamageDataOnEntityDeath(this));
    }
}