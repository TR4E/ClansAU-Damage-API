package me.trae.api.damage.events;

import me.trae.api.damage.events.interfaces.ICustomKnockbackEvent;
import me.trae.core.event.CustomCancellableEvent;

public class CustomKnockbackEvent extends CustomCancellableEvent implements ICustomKnockbackEvent {

    private final CustomDamageEvent damageEvent;
    private double knockback;

    public CustomKnockbackEvent(final CustomDamageEvent damageEvent) {
        this.damageEvent = damageEvent;
        this.knockback = damageEvent.getKnockback();
    }

    @Override
    public CustomDamageEvent getDamageEvent() {
        return this.damageEvent;
    }

    @Override
    public double getKnockback() {
        return this.knockback;
    }

    @Override
    public void setKnockback(final double knockback) {
        this.knockback = knockback;
    }
}