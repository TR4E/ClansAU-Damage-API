package me.trae.api.damage.events;

import me.trae.api.damage.events.interfaces.IPlayerCaughtEntityEvent;
import me.trae.core.event.CustomCancellableEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlayerCaughtEntityEvent extends CustomCancellableEvent implements IPlayerCaughtEntityEvent {

    private final Player player;
    private final LivingEntity caught;

    public PlayerCaughtEntityEvent(final Player player, final LivingEntity caught) {
        this.player = player;
        this.caught = caught;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public LivingEntity getCaught() {
        return this.caught;
    }
}