package me.trae.api.damage.events.damage;

import me.trae.api.damage.events.damage.abstracts.DamageEvent;

public class CustomPostDamageEvent extends DamageEvent {

    public CustomPostDamageEvent(final CustomDamageEvent event) {
        super(event);
    }

    public CustomPostDamageEvent(final CustomPostDamageEvent event) {
        super(event);
    }
}