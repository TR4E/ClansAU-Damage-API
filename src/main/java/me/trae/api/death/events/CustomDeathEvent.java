package me.trae.api.death.events;

import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.death.events.interfaces.ICustomDeathEvent;
import me.trae.core.event.CustomEvent;

public class CustomDeathEvent extends CustomEvent implements ICustomDeathEvent {

    private final CustomDamageEvent damageEvent;

    public CustomDeathEvent(final CustomDamageEvent damageEvent) {
        this.damageEvent = damageEvent;
    }

    @Override
    public CustomDamageEvent getDamageEvent() {
        return this.damageEvent;
    }
}