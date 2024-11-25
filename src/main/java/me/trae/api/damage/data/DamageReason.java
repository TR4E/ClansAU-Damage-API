package me.trae.api.damage.data;

import me.trae.api.damage.data.interfaces.IDamageReason;

public class DamageReason implements IDamageReason {

    private final String name;
    private final long systemTime, duration;

    public DamageReason(final String name, final long duration) {
        this.name = name;
        this.systemTime = System.currentTimeMillis();
        this.duration = duration;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getExtraName() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return this.getName() + this.getExtraName();
    }

    @Override
    public long getSystemTime() {
        return this.systemTime;
    }

    @Override
    public long getDuration() {
        return this.duration;
    }
}