package me.trae.api.damage.events;

import me.trae.api.damage.events.interfaces.IDamageDelayEvent;
import me.trae.core.event.CustomCancellableEvent;

public class DamageDelayEvent extends CustomCancellableEvent implements IDamageDelayEvent {

    private final CustomDamageEvent damageEvent;

    private long delay;

    public DamageDelayEvent(final CustomDamageEvent damageEvent, final long delay) {
        this.damageEvent = damageEvent;
        this.delay = delay;
    }

    @Override
    public CustomDamageEvent getDamageEvent() {
        return this.damageEvent;
    }

    @Override
    public long getDelay() {
        return this.delay;
    }

    @Override
    public void setDelay(final long delay) {
        this.delay = delay;
    }

    @Override
    public boolean hasDelay() {
        return this.getDelay() > 0L;
    }
}