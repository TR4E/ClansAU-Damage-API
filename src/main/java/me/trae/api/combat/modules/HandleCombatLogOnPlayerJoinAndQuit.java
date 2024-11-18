package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.api.combat.npc.CombatNPC;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleCombatLogOnPlayerJoinAndQuit extends SpigotListener<Core, CombatManager> {

    public HandleCombatLogOnPlayerJoinAndQuit(final CombatManager manager) {
        super(manager);
    }

    private void createCombatNPC(final Player player) {
        final List<ItemStack> contents = new ArrayList<>();

        contents.addAll(Arrays.asList(player.getInventory().getArmorContents()));
        contents.addAll(Arrays.asList(player.getInventory().getContents()));

        contents.removeIf(itemStack -> itemStack == null || itemStack.getType() == Material.AIR);

        final CombatNPC combatNPC = new CombatNPC(player) {
            @Override
            public void remove() {
                super.remove();
                getManager().removeCombatNpc(this);
            }

            @Override
            public Location getLocation() {
                return player.getLocation().clone();
            }

            @Override
            public OfflinePlayer getPlayer() {
                return player;
            }

            @Override
            public List<ItemStack> getContents() {
                return contents;
            }
        };

        combatNPC.spawn();

        this.getManager().addCombatNpc(combatNPC);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (this.getInstance().isServerStopping()) {
            return;
        }

        if (this.getManager().isSafeOnLogByPlayer(player)) {
            return;
        }

        this.createCombatNPC(player);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final CombatNPC npc = this.getManager().getCombatNpcMap(player);
        if (npc == null) {
            return;
        }

        npc.remove();
    }
}