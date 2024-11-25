package me.trae.api.damage.events.damage;

import me.trae.api.damage.events.damage.abstracts.DamageEvent;

public class CustomDamageEvent extends DamageEvent {

    public CustomDamageEvent(final CustomPreDamageEvent event) {
        super(event);
    }
}