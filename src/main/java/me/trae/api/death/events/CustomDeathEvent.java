package me.trae.api.death.events;

import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.death.events.interfaces.ICustomDeathEvent;
import me.trae.core.event.CustomEvent;

public class CustomDeathEvent extends CustomEvent implements ICustomDeathEvent {

    private final CustomPostDamageEvent damageEvent;

    public CustomDeathEvent(final CustomPostDamageEvent damageEvent) {
        this.damageEvent = damageEvent;
    }

    @Override
    public CustomPostDamageEvent getDamageEvent() {
        return this.damageEvent;
    }
}