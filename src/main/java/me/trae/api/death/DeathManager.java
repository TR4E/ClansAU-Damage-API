package me.trae.api.death;

import me.trae.api.death.modules.HandleCustomDeathMessage;
import me.trae.core.Core;
import me.trae.core.framework.SpigotManager;

public class DeathManager extends SpigotManager<Core> {

    public DeathManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleCustomDeathMessage(this));
    }
}