package me.trae.api.death;

import me.trae.api.death.modules.HandleCustomDeathMessage;
import me.trae.core.framework.SpigotManager;
import me.trae.core.framework.SpigotPlugin;

public class DeathManager extends SpigotManager<SpigotPlugin> {

    public DeathManager(final SpigotPlugin instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleCustomDeathMessage(this));
    }
}