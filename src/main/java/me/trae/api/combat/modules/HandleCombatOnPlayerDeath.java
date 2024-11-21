package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.death.events.CustomDeathEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleCombatOnPlayerDeath extends SpigotListener<Core, CombatManager> {

    public HandleCombatOnPlayerDeath(final CombatManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDeath(final CustomDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getKiller() instanceof Player)) {
            return;
        }

        final Player player = event.getEntityByClass(Player.class);

        this.getManager().removeCombat(player);
    }
}