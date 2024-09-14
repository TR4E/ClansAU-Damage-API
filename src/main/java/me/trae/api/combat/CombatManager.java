package me.trae.api.combat;

import me.trae.api.combat.events.CombatTagEvent;
import me.trae.api.combat.interfaces.ICombatManager;
import me.trae.api.combat.modules.HandleCombatOnDamage;
import me.trae.api.combat.modules.HandleCombatUpdater;
import me.trae.core.Core;
import me.trae.core.client.ClientManager;
import me.trae.core.framework.SpigotManager;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.enums.TimeUnit;
import me.trae.core.weapon.Weapon;
import me.trae.core.weapon.WeaponManager;
import me.trae.core.weapon.types.Legendary;
import me.trae.core.weapon.weapons.TNT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class CombatManager extends SpigotManager<Core> implements ICombatManager {

    public static long COMBAT_DURATION = TimeUnit.SECONDS.getDuration() * 30;

    private final Map<UUID, Combat> COMBAT_MAP = new HashMap<>();

    public CombatManager(final Core instance) {
        super(instance);
    }

    @Override
    public void registerModules() {
        addModule(new HandleCombatOnDamage(this));
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

        UtilServer.callEvent(new CombatTagEvent(combat, player));
    }

    @Override
    public void removeCombat(final Player player) {
        this.getCombatMap().remove(player.getUniqueId());
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
    public boolean isSafeByPlayerOnLog(final Player player) {
        if (this.getInstance().getManagerByClass(ClientManager.class).getClientByPlayer(player).isAdministrating()) {
            return true;
        }

        if (this.isCombatByPlayer(player)) {
            return true;
        }

        final WeaponManager weaponManager = this.getInstance().getManagerByClass(WeaponManager.class);

        final Predicate<ItemStack> predicate = (itemStack -> {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return false;
            }

            final Weapon<?, ?, ?> weapon = weaponManager.getWeaponByItemStack(itemStack);

            return weapon instanceof Legendary || weapon instanceof TNT;
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
}