package me.trae.api.damage.events.interfaces;

import me.trae.api.damage.events.CustomDamageEvent;

public interface IDamageDelayEvent {

    CustomDamageEvent getDamageEvent();

    long getDelay();

    void setDelay(final long delay);

    boolean hasDelay();
}