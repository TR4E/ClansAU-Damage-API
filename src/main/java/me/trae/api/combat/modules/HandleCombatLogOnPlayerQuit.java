package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.api.combat.npc.CombatNPC;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.enums.TimeUnit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleCombatLogOnPlayerQuit extends SpigotListener<Core, CombatManager> {

    public HandleCombatLogOnPlayerQuit(final CombatManager manager) {
        super(manager);
    }

    private void createCombatNPC(final Player player) {
        final List<ItemStack> contents = new ArrayList<>();

        contents.addAll(Arrays.asList(player.getInventory().getArmorContents()));
        contents.addAll(Arrays.asList(player.getInventory().getContents()));

        final CombatNPC combatNPC = new CombatNPC() {
            @Override
            public long getDuration() {
                return TimeUnit.MINUTES.getDuration() * 5;
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

        if (this.getManager().isSafeOnLogByPlayer(player)) {
            return;
        }

        this.createCombatNPC(player);
    }
}