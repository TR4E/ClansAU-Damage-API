package me.trae.api.damage.interfaces;

import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.UUID;

public interface IDamageManager {

    Map<UUID, CustomPostDamageEvent> getLastDamageDataMap();

    void addLastDamageData(final CustomPostDamageEvent data);

    CustomPostDamageEvent getLastDamageDataByDamagee(final Entity damagee);

    Map<UUID, Map<UUID, DamageReason>> getLastReasonMap();

    void addLastReason(final Entity damagee, final Entity damager, final DamageReason damageReason);

    void removeLastReason(final Entity damagee, final Entity damager);

    DamageReason getLastReasonByDamagee(final Entity damagee, final Entity damager);
}