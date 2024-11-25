package me.trae.api.damage.events.interfaces;

import me.trae.api.damage.events.damage.CustomPostDamageEvent;

public interface ICustomKnockbackEvent {

    CustomPostDamageEvent getDamageEvent();

    double getKnockback();

    void setKnockback(final double knockback);
}