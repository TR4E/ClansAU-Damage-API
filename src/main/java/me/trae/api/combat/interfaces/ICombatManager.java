package me.trae.api.combat.interfaces;

import me.trae.api.combat.Combat;
import me.trae.api.combat.npc.CombatNPC;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface ICombatManager {

    Map<UUID, Combat> getCombatMap();

    void addCombat(final Player player);

    void removeCombat(final Player player);

    Combat getCombatByPlayer(final Player player);

    boolean isCombatByPlayer(final Player player);

    boolean isSafeByPlayer(final Player player);

    Map<UUID, CombatNPC> getCombatNpcMap();

    void addCombatNpc(final CombatNPC combatNPC);

    void removeCombatNpc(final CombatNPC combatNPC);

    CombatNPC getCombatNpcMap(final OfflinePlayer player);

    boolean isCombatNpc(final OfflinePlayer player);
}