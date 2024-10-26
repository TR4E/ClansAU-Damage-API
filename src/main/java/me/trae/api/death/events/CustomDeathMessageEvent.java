package me.trae.api.death.events;

import me.trae.api.death.events.interfaces.ICustomDeathMessageEvent;
import me.trae.core.event.CustomCancellableEvent;
import org.bukkit.entity.Player;

public class CustomDeathMessageEvent extends CustomCancellableEvent implements ICustomDeathMessageEvent {

    private final CustomDeathEvent deathEvent;
    private final Player target;

    private String entityName, killerName;

    public CustomDeathMessageEvent(final CustomDeathEvent deathEvent, final Player target) {
        this.deathEvent = deathEvent;
        this.target = target;

        this.entityName = String.format("<yellow>%s", deathEvent.getEntity().getName());
        this.killerName = String.format("<yellow>%s", (deathEvent.getKiller() != null ? deathEvent.getKiller().getName() : deathEvent.getDamageEvent().getCauseString()));
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
    public void setEntityName(final String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getKillerName() {
        return this.killerName;
    }

    @Override
    public void setKillerName(final String killerName) {
        this.killerName = killerName;
    }
}