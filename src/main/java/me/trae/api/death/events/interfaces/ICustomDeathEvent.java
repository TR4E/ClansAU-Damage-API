package me.trae.api.death.events.interfaces;

import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import org.bukkit.entity.LivingEntity;

public interface ICustomDeathEvent {

    CustomPostDamageEvent getDamageEvent();

    default LivingEntity getEntity() {
        return this.getDamageEvent().getDamageeByClass(LivingEntity.class);
    }

    default <E extends LivingEntity> E getEntityByClass(final Class<E> clazz) {
        return this.getDamageEvent().getDamageeByClass(clazz);
    }

    default LivingEntity getKiller() {
        return this.getDamageEvent().getDamagerByClass(LivingEntity.class);
    }

    default <E extends LivingEntity> E getKillerByClass(final Class<E> clazz) {
        return this.getDamageEvent().getDamagerByClass(clazz);
    }

    int getAssists();
}