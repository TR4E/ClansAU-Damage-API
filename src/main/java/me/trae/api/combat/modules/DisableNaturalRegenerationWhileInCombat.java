package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.core.Core;
import me.trae.core.client.ClientManager;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.injectors.annotations.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class DisableNaturalRegenerationWhileInCombat extends SpigotListener<Core, CombatManager> {

    @Inject
    private ClientManager clientManager;

    public DisableNaturalRegenerationWhileInCombat(final CombatManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRegainHealth(final EntityRegainHealthEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        final Player player = UtilJava.cast(Player.class, event.getEntity());

        if (!(this.getManager().isCombatByPlayer(player))) {
            return;
        }

        if (this.clientManager.getClientByPlayer(player).isAdministrating()) {
            return;
        }

        event.setCancelled(true);
    }
}