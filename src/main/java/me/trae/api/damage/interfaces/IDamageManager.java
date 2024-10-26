package me.trae.api.damage.interfaces;

import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.UUID;

public interface IDamageManager {

    Map<UUID, CustomDamageEvent> getLastDamageDataMap();

    void addLastDamageData(final CustomDamageEvent data);

    CustomDamageEvent getLastDamageDataByDamagee(final Entity damagee);

    Map<UUID, Map<UUID, DamageReason>> getLastReasonMap();

    void addLastReason(final Entity damagee, final Entity damager, final DamageReason damageReason);

    void removeLastReason(final Entity damagee, final Entity damager);

    DamageReason getLastReasonByDamagee(final Entity damagee, final Entity damager);
}