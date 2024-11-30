package me.trae.api.damage.events;

import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.damage.events.interfaces.ICustomKnockbackEvent;
import me.trae.core.event.CustomCancellableEvent;

public class CustomKnockbackEvent extends CustomCancellableEvent implements ICustomKnockbackEvent {

    private final CustomPostDamageEvent damageEvent;
    private double knockback;

    public CustomKnockbackEvent(final CustomPostDamageEvent damageEvent) {
        this.damageEvent = damageEvent;
        this.knockback = damageEvent.getKnockback();
    }

    @Override
    public CustomPostDamageEvent getDamageEvent() {
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

    @Override
    public boolean hasKnockback() {
        return this.getKnockback() > 0.0D;
    }
}