package me.trae.api.combat.events;

import me.trae.api.combat.Combat;
import me.trae.api.combat.events.interfaces.ICombatEvent;
import me.trae.core.event.CustomEvent;
import org.bukkit.entity.Player;

public class CombatUpdaterEvent extends CustomEvent implements ICombatEvent {

    private final Combat combat;
    private final Player player;

    public CombatUpdaterEvent(final Combat combat, final Player player) {
        this.combat = combat;
        this.player = player;
    }

    @Override
    public Combat getCombat() {
        return this.combat;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}