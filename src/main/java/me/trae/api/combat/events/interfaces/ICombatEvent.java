package me.trae.api.combat.events.interfaces;

import me.trae.api.combat.Combat;
import me.trae.core.event.types.IPlayerEvent;

public interface ICombatEvent extends IPlayerEvent {

    Combat getCombat();
}