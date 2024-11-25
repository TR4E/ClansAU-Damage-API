package me.trae.api.combat;

import me.trae.api.combat.events.CombatReceiveEvent;
import me.trae.api.combat.events.CombatRemoveEvent;
import me.trae.api.combat.interfaces.ICombatManager;
import me.trae.api.combat.modules.*;
import me.trae.api.combat.npc.CombatNPC;
import me.trae.api.damage.utility.UtilDamage;
import me.trae.core.Core;
import me.trae.core.client.ClientManager;
import me.trae.core.framework.SpigotManager;
import me.trae.core.utility.UtilServer;
import me.trae.core.weapon.WeaponManager;
import me.trae.core.weapon.models.ValuableWeapon;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class CombatManager extends SpigotManager<Core> implements ICombatManager {

    private final Map<UUID, Combat> COMBAT_MAP = new HashMap<>();
    private final Map<UUID, CombatNPC> COMBAT_NPC_MAP = new HashMap<>();

    public CombatManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleCombatLogOnPlayerJoinAndQuit(this));
        addModule(new HandleCombatLogUpdater(this));
        addModule(new HandleCombatOnPlayerDamage(this));
        addModule(new HandleCombatOnPlayerDeath(this));
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

    @Override
    public boolean isSafeByPlayer(final Player player) {
        if (this.getInstance().isServerStopping()) {
            return true;
        }

        if (UtilDamage.isInvulnerable(player)) {
            return true;
        }

        if (this.getInstance().getManagerByClass(ClientManager.class).getClientByPlayer(player).isAdministrating()) {
            return true;
        }

        if (this.isCombatByPlayer(player)) {
            return false;
        }

        final WeaponManager weaponManager = this.getInstance().getManagerByClass(WeaponManager.class);

        final Predicate<ItemStack> predicate = (itemStack -> {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return false;
            }

            return weaponManager.getWeaponByItemStack(itemStack) instanceof ValuableWeapon;
        });

        for (final ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (predicate.test(itemStack)) {
                return false;
            }
        }

        for (final ItemStack itemStack : player.getInventory().getContents()) {
            if (predicate.test(itemStack)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Map<UUID, CombatNPC> getCombatNpcMap() {
        return this.COMBAT_NPC_MAP;
    }

    @Override
    public void addCombatNpc(final CombatNPC combatNPC) {
        this.getCombatNpcMap().put(combatNPC.getUUID(), combatNPC);
    }

    @Override
    public void removeCombatNpc(final CombatNPC combatNPC) {
        this.getCombatNpcMap().remove(combatNPC.getUUID());
    }

    @Override
    public CombatNPC getCombatNpcMap(final OfflinePlayer player) {
        return this.getCombatNpcMap().getOrDefault(player.getUniqueId(), null);
    }

    @Override
    public boolean isCombatNpc(final OfflinePlayer player) {
        return this.getCombatNpcMap().containsKey(player.getUniqueId());
    }
}