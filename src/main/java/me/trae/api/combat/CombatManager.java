package me.trae.api.combat;

import me.trae.api.combat.events.CombatReceiveEvent;
import me.trae.api.combat.events.CombatRemoveEvent;
import me.trae.api.combat.interfaces.ICombatManager;
import me.trae.api.combat.modules.HandleCombatUpdater;
import me.trae.core.Core;
import me.trae.core.framework.SpigotManager;
import me.trae.core.utility.UtilServer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatManager extends SpigotManager<Core> implements ICombatManager {

    private final Map<UUID, Combat> COMBAT_MAP = new HashMap<>();

    public CombatManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleCombatUpdater(this));
    }

    @Override
    public Map<UUID, Combat> getCombatMap() {
        return this.COMBAT_MAP;
    }

    @Override
    public void addCombat(final Player player) {
        final Combat combat = new Combat(player);
        this.getCombatMap().put(player.getUniqueId(), combat);


        UtilServer.callEvent(new CombatReceiveEvent(combat, player));
    }

    @Override
    public void removeCombat(final Player player) {
        final Combat combat = this.getCombatMap().remove(player.getUniqueId());

        UtilServer.callEvent(new CombatRemoveEvent(combat, player));
    }

    @Override
    public Combat getCombatByPlayer(final Player player) {
        return this.getCombatMap().getOrDefault(player.getUniqueId(), null);
    }

    @Override
    public boolean isCombatByPlayer(final Player player) {
        return this.getCombatMap().containsKey(player.getUniqueId());
    }
}