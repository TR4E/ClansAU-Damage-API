package me.trae.api.combat.events;

import me.trae.api.combat.events.interfaces.IPlayerClickCombatNpcEvent;
import me.trae.core.event.CustomCancellableEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerClickCombatNpcEvent extends CustomCancellableEvent implements IPlayerClickCombatNpcEvent {

    private final Player player;
    private final OfflinePlayer target;

    public PlayerClickCombatNpcEvent(final Player player, final OfflinePlayer target) {
        this.player = player;
        this.target = target;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public OfflinePlayer getTarget() {
        return this.target;
    }
}