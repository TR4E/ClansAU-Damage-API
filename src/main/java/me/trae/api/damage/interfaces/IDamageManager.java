package me.trae.api.damage.interfaces;

import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.UUID;

public interface IDamageManager {

    Map<UUID, CustomDamageEvent> getLastDamageData();

    void addLastDamageData(final CustomDamageEvent data);

    CustomDamageEvent getLastDamageDataByDamagee(final Entity damagee);

    Map<UUID, Map<UUID, DamageReason>> getReasonMap();

    void addReason(final Entity damagee, final Entity damager, final DamageReason damageReason);

    void removeReason(final Entity damagee, final Entity damager);

    DamageReason getReasonByEntity(final Entity damagee, final Entity damager);
}