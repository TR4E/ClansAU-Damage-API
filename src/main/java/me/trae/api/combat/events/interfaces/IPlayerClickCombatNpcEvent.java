package me.trae.api.combat.events.interfaces;

import me.trae.core.event.types.IPlayerEvent;
import me.trae.core.event.types.ITargetEvent;
import org.bukkit.OfflinePlayer;

public interface IPlayerClickCombatNpcEvent extends IPlayerEvent, ITargetEvent<OfflinePlayer> {
}