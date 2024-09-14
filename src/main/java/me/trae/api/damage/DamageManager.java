package me.trae.api.damage;

import me.trae.api.damage.modules.HandleDamageDisplayOnPlayerLevel;
import me.trae.api.damage.modules.HandlePlaySoundOnArrowHitEntity;
import me.trae.api.damage.modules.HandlePreEntityDamage;
import me.trae.core.framework.SpigotManager;
import me.trae.core.framework.SpigotPlugin;

public class DamageManager extends SpigotManager<SpigotPlugin> {

    public DamageManager(final SpigotPlugin instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleDamageDisplayOnPlayerLevel(this));
        addModule(new HandlePlaySoundOnArrowHitEntity(this));
        addModule(new HandlePreEntityDamage(this));
    }
}