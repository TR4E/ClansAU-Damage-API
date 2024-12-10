package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.api.combat.npc.CombatNPC;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilMessage;
import me.trae.core.utility.UtilServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HandleCombatLogOnPlayerJoinAndQuit extends SpigotListener<Core, CombatManager> {

    public HandleCombatLogOnPlayerJoinAndQuit(final CombatManager manager) {
        super(manager);
    }

    private void createCombatNPC(final Player player) {
        final CombatNPC combatNPC = new CombatNPC(player, this.getManager().combatNpcDuration) {
            @Override
            public void onDestroy() {
                super.onDestroy();
                getManager().removeCombatNpc(this);
                getManager().getCombatMap().remove(this.getPlayer().getUniqueId());
            }
        };

        combatNPC.spawn();

        this.getManager().addCombatNpc(combatNPC);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (this.getManager().isSafeByPlayer(player)) {
            return;
        }

        this.createCombatNPC(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final CombatNPC npc = this.getManager().getCombatNpcMap(player);
        if (npc == null) {
            return;
        }

        npc.destroy();

        UtilMessage.simpleMessage(player, "Combat Log", "You saved yourself!");
    }
}