package me.trae.api.combat;

import me.trae.api.combat.interfaces.ICombat;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Combat implements ICombat {

    private final UUID uuid;
    private final long systemTime, duration;

    public Combat(final Player player) {
        this.uuid = player.getUniqueId();
        this.systemTime = System.currentTimeMillis();
        this.duration = CombatManager.COMBAT_DURATION;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
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