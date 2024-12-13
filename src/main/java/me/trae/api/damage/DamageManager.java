package me.trae.api.damage;

import me.trae.api.damage.commands.KillCommand;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.damage.interfaces.IDamageManager;
import me.trae.api.damage.modules.general.HandlePlaySoundOnArrowHitEntity;
import me.trae.api.damage.modules.generic.*;
import me.trae.api.damage.modules.generic.armour.HandleArmourDurability;
import me.trae.api.damage.modules.generic.armour.HandleArmourReduction;
import me.trae.api.damage.modules.generic.weapon.HandleWeaponDurability;
import me.trae.api.damage.modules.generic.weapon.HandleWeaponReduction;
import me.trae.api.damage.modules.system.HandleDamageDataUpdater;
import me.trae.api.damage.modules.system.HandleDamageReasonCheckForNewDamage;
import me.trae.core.Core;
import me.trae.core.framework.SpigotManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class DamageManager extends SpigotManager<Core> implements IDamageManager {

    private final Map<UUID, List<CustomPostDamageEvent>> DAMAGE_DATA_MAP = new HashMap<>();
    private final Map<UUID, Map<UUID, DamageReason>> LAST_REASON_MAP = new HashMap<>();

    public DamageManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        // Commands
        addModule(new KillCommand(this));

        // General Modules
        addModule(new HandlePlaySoundOnArrowHitEntity(this));

        // Generic Modules
        addModule(new HandleCustomDamageArrowFireTicks(this));
        addModule(new HandleCustomDamageDelay(this));
        addModule(new HandleCustomDamageDisplayOnPlayerLevel(this));
        addModule(new HandleCustomDamageKnockback(this));
        addModule(new HandleCustomDamagePotionEffects(this));
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
        addModule(new HandleDamageDataUpdater(this));
        addModule(new HandleDamageReasonCheckForNewDamage(this));
    }

    @Override
    public Map<UUID, List<CustomPostDamageEvent>> getDamageDataMap() {
        return this.DAMAGE_DATA_MAP;
    }

    @Override
    public void addDamageData(final CustomPostDamageEvent data) {
        if (!(data.getDamager() instanceof Player)) {
            return;
        }

        if (!(this.getDamageDataMap().containsKey(data.getDamagee().getUniqueId()))) {
            this.getDamageDataMap().put(data.getDamagee().getUniqueId(), new ArrayList<>());
        }

        final List<CustomPostDamageEvent> list = this.getDamageDataMap().get(data.getDamagee().getUniqueId());

        list.removeIf(event -> event.getDamager().equals(data.getDamager()));

        list.add(data);
    }

    @Override
    public List<CustomPostDamageEvent> getListOfDamageDataByDamagee(final Entity damagee) {
        return this.getDamageDataMap().getOrDefault(damagee.getUniqueId(), new ArrayList<>());
    }

    @Override
    public CustomPostDamageEvent getLastDamageDataByDamagee(final Entity damagee) {
        final List<CustomPostDamageEvent> list = this.getListOfDamageDataByDamagee(damagee);
        if (!(list.isEmpty())) {
            return list.remove(list.size() - 1);
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