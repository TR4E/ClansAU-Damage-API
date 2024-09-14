package me.trae.api.damage;

import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.interfaces.IDamageManager;
import me.trae.api.damage.modules.HandleDamageDisplayOnPlayerLevel;
import me.trae.api.damage.modules.HandleDamageReasonCheckForNewDamage;
import me.trae.api.damage.modules.HandlePlaySoundOnArrowHitEntity;
import me.trae.api.damage.modules.HandlePreEntityDamage;
import me.trae.core.framework.SpigotManager;
import me.trae.core.framework.SpigotPlugin;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DamageManager extends SpigotManager<SpigotPlugin> implements IDamageManager {

    private final Map<UUID, CustomDamageEvent> LAST_DAMAGE_DATA = new HashMap<>();
    private final Map<UUID, Map<UUID, DamageReason>> REASON_MAP = new HashMap<>();

    public DamageManager(final SpigotPlugin instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleDamageDisplayOnPlayerLevel(this));
        addModule(new HandleDamageReasonCheckForNewDamage(this));
        addModule(new HandlePlaySoundOnArrowHitEntity(this));
        addModule(new HandlePreEntityDamage(this));
    }

    @Override
    public Map<UUID, CustomDamageEvent> getLastDamageData() {
        return this.LAST_DAMAGE_DATA;
    }

    @Override
    public void addLastDamageData(final CustomDamageEvent data) {
        this.getLastDamageData().put(data.getDamagee().getUniqueId(), data);
    }

    @Override
    public CustomDamageEvent getLastDamageDataByDamagee(final Entity damagee) {
        if (this.getLastDamageData().containsKey(damagee.getUniqueId())) {
            return this.getLastDamageData().remove(damagee.getUniqueId());
        }

        return null;
    }

    @Override
    public Map<UUID, Map<UUID, DamageReason>> getReasonMap() {
        return this.REASON_MAP;
    }

    @Override
    public void addReason(final Entity damagee, final Entity damager, final DamageReason damageReason) {
        if (!(this.getReasonMap().containsKey(damagee.getUniqueId()))) {
            this.getReasonMap().put(damagee.getUniqueId(), new HashMap<>());
        }

        this.getReasonMap().get(damagee.getUniqueId()).put(damager.getUniqueId(), damageReason);
    }

    @Override
    public void removeReason(final Entity damagee, final Entity damager) {
        this.getReasonMap().getOrDefault(damagee.getUniqueId(), new HashMap<>()).remove(damager.getUniqueId());
    }

    @Override
    public DamageReason getReasonByEntity(final Entity damagee, final Entity damager) {
        return this.getReasonMap().getOrDefault(damagee.getUniqueId(), new HashMap<>()).getOrDefault(damager.getUniqueId(), null);
    }
}