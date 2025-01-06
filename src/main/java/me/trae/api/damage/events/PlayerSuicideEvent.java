package me.trae.api.damage.events;

import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.event.types.IPlayerEvent;
import org.bukkit.entity.Player;

public class PlayerSuicideEvent extends CustomCancellableEvent implements IPlayerEvent {

    private final Player player;

    public PlayerSuicideEvent(final Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}