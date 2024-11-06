package me.trae.api.death.events.interfaces;

import me.trae.api.death.events.CustomDeathEvent;
import me.trae.core.event.types.ITargetEvent;
import org.bukkit.entity.Player;

public interface ICustomDeathMessageEvent extends ITargetEvent<Player> {

    CustomDeathEvent getDeathEvent();

    String getEntityName();

    String getKillerName();
}