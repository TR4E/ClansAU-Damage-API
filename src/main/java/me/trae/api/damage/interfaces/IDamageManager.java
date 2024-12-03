package me.trae.api.damage.interfaces;

import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IDamageManager {

    Map<UUID, List<CustomPostDamageEvent>> getDamageDataMap();

    void addDamageData(final CustomPostDamageEvent data);

    List<CustomPostDamageEvent> getListOfDamageDataByDamagee(final Entity damagee);

    CustomPostDamageEvent getLastDamageDataByDamagee(final Entity damagee);

    Map<UUID, Map<UUID, DamageReason>> getLastReasonMap();

    void addLastReason(final Entity damagee, final Entity damager, final DamageReason damageReason);

    void removeLastReason(final Entity damagee, final Entity damager);

    DamageReason getLastReasonByDamagee(final Entity damagee, final Entity damager);
}