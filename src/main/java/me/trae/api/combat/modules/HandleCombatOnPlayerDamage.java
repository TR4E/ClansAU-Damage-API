package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleCombatOnPlayerDamage extends SpigotListener<Core, CombatManager> {

    public HandleCombatOnPlayerDamage(final CombatManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamagee() instanceof Player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        final Player damagee = event.getDamageeByClass(Player.class);
        final Player damager = event.getDamagerByClass(Player.class);

        this.getManager().addCombat(damagee);
        this.getManager().addCombat(damager);
    }
}