package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.core.framework.SpigotPlugin;
import me.trae.core.framework.types.frame.SpigotUpdater;
import me.trae.core.updater.annotations.Update;
import me.trae.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HandleCombatUpdater extends SpigotUpdater<SpigotPlugin, CombatManager> {

    public HandleCombatUpdater(final CombatManager manager) {
        super(manager);
    }

    @Update(delay = 250L)
    public void onUpdater() {
        this.getManager().getCombatMap().values().removeIf(combat -> {
            if (!(combat.hasExpired())) {
                return false;
            }

            final Player player = Bukkit.getPlayer(combat.getUUID());
            if (player != null) {
                UtilMessage.message(player, "Combat", "You are no longer in Combat!");
            }

            return true;
        });
    }
}