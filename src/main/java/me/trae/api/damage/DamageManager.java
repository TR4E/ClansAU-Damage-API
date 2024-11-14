package me.trae.api.damage;

import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.interfaces.IDamageManager;
import me.trae.api.damage.modules.general.HandlePlaySoundOnArrowHitEntity;
import me.trae.api.damage.modules.generic.*;
import me.trae.api.damage.modules.generic.armour.HandleArmourDurability;
import me.trae.api.damage.modules.generic.armour.HandleArmourReduction;
import me.trae.api.damage.modules.generic.weapon.HandleWeaponDurability;
import me.trae.api.damage.modules.generic.weapon.HandleWeaponReduction;
import me.trae.api.damage.modules.system.HandleDamageReasonCheckForNewDamage;
import me.trae.core.Core;
import me.trae.core.framework.SpigotManager;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DamageManager extends SpigotManager<Core> implements IDamageManager {

    private final Map<UUID, CustomDamageEvent> LAST_DAMAGE_DATA_MAP = new HashMap<>();
    private final Map<UUID, Map<UUID, DamageReason>> LAST_REASON_MAP = new HashMap<>();

    public DamageManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        // General Modules
        addModule(new HandlePlaySoundOnArrowHitEntity(this));

        // Generic Modules
        addModule(new HandleCustomDamageDelay(this));
        addModule(new HandleCustomDamageKnockback(this));
        addModule(new HandleCustomDamageSound(this));
        addModule(new HandleDealCustomDamage(this));
        addModule(new HandlePreEntityDamage(this));
        // Armour Modules
        addModule(new HandleArmourDurability(this));
        addModule(new HandleArmourReduction(this));
        // Weapon Modules
        addModule(new HandleWeaponDurability(this));
        addModule(new HandleWeaponReduction(this));

        // System Modules
        addModule(new HandleDamageReasonCheckForNewDamage(this));
    }

    @Override
    public Map<UUID, CustomDamageEvent> getLastDamageDataMap() {
        return this.LAST_DAMAGE_DATA_MAP;
    }

    @Override
    public void addLastDamageData(final CustomDamageEvent data) {
        if (data.getDamager() == null) {
            return;
        }

        this.getLastDamageDataMap().put(data.getDamagee().getUniqueId(), data);
    }

    @Override
    public CustomDamageEvent getLastDamageDataByDamagee(final Entity damagee) {
        if (this.getLastDamageDataMap().containsKey(damagee.getUniqueId())) {
            return this.getLastDamageDataMap().remove(damagee.getUniqueId());
        }

        return null;
    }

    @Override
    public Map<UUID, Map<UUID, DamageReason>> getLastReasonMap() {
        return this.LAST_REASON_MAP;
    }

    @Override
    public void addLastReason(final Entity damagee, final Entity damager, final DamageReason damageReason) {
        if (!(this.getLastReasonMap().containsKey(damagee.getUniqueId()))) {
            this.getLastReasonMap().put(damagee.getUniqueId(), new HashMap<>());
        }

        this.getLastReasonMap().get(damagee.getUniqueId()).put(damager.getUniqueId(), damageReason);
    }

    @Override
    public void removeLastReason(final Entity damagee, final Entity damager) {
        this.getLastReasonMap().getOrDefault(damagee.getUniqueId(), new HashMap<>()).remove(damager.getUniqueId());
    }

    @Override
    public DamageReason getLastReasonByDamagee(final Entity damagee, final Entity damager) {
        return this.getLastReasonMap().getOrDefault(damagee.getUniqueId(), new HashMap<>()).getOrDefault(damager.getUniqueId(), null);
    }
}