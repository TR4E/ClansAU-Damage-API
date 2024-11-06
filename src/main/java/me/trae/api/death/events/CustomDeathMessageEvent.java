package me.trae.api.death.events;

import me.trae.api.death.events.interfaces.ICustomDeathMessageEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.player.events.PlayerDisplayNameEvent;
import me.trae.core.utility.UtilServer;
import org.bukkit.entity.Player;

public class CustomDeathMessageEvent extends CustomCancellableEvent implements ICustomDeathMessageEvent {

    private final CustomDeathEvent deathEvent;

    private final Player target;

    private final String entityName, killerName;

    public CustomDeathMessageEvent(final CustomDeathEvent deathEvent, final Player target) {
        this.deathEvent = deathEvent;
        this.target = target;

        if (deathEvent.getEntity() instanceof Player) {
            this.entityName = UtilServer.getEvent(new PlayerDisplayNameEvent(deathEvent.getEntityByClass(Player.class), target, true)).getPlayerName();
        } else {
            this.entityName = deathEvent.getDamageEvent().getDamageeName();
        }

        if (deathEvent.getKiller() instanceof Player) {
            this.killerName = UtilServer.getEvent(new PlayerDisplayNameEvent(deathEvent.getKillerByClass(Player.class), target, true)).getPlayerName();
        } else {
            this.killerName = deathEvent.getDamageEvent().getDamagerName();
        }
    }

    @Override
    public CustomDeathEvent getDeathEvent() {
        return this.deathEvent;
    }

    @Override
    public Player getTarget() {
        return this.target;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    @Override
    public String getKillerName() {
        return this.killerName;
    }
}