package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleCombatOnDamage extends SpigotListener<Core, CombatManager> {

    public HandleCombatOnDamage(final CombatManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamagee() instanceof Player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        this.getManager().addCombat(event.getDamageeByClass(Player.class));
        this.getManager().addCombat(event.getDamagerByClass(Player.class));
    }
}