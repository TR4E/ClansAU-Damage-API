package me.trae.api.combat.modules;

import me.trae.api.combat.CombatManager;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotUpdater;
import me.trae.core.updater.annotations.Update;

public class HandleCombatLogUpdater extends SpigotUpdater<Core, CombatManager> {

    public HandleCombatLogUpdater(final CombatManager manager) {
        super(manager);
    }

    @Update(delay = 250L)
    public void onUpdater() {
        this.getManager().getCombatNpcMap().values().removeIf(npc -> {
            if (!(npc.hasExpired())) {
                return false;
            }

            npc.purge();

            return true;
        });
    }
}