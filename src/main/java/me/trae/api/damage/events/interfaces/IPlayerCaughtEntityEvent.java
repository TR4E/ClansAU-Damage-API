package me.trae.api.damage.events.interfaces;

import me.trae.core.event.types.IPlayerEvent;
import me.trae.core.utility.UtilJava;
import org.bukkit.entity.LivingEntity;

public interface IPlayerCaughtEntityEvent extends IPlayerEvent {

    LivingEntity getCaught();

    default <E extends LivingEntity> E getCaughtByClass(final Class<E> clazz) {
        return UtilJava.cast(clazz, this.getCaught());
    }
}