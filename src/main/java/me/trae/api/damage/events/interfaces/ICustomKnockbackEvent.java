package me.trae.api.damage.events.interfaces;

import me.trae.api.damage.events.CustomDamageEvent;

public interface ICustomKnockbackEvent {

    CustomDamageEvent getDamageEvent();

    double getKnockback();

    void setKnockback(final double knockback);
}