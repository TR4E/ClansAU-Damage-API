package me.trae.api.combat.interfaces;

import me.trae.api.combat.Combat;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface ICombatManager {

    Map<UUID, Combat> getCombatMap();

    void addCombat(final Player player);

    void removeCombat(final Player player);

    Combat getCombatByPlayer(final Player player);

    boolean isCombatByPlayer(final Player player);

    boolean isSafeByPlayerOnLog(final Player player);
}