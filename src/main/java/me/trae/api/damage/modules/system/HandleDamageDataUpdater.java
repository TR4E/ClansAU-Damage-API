package me.trae.api.damage.modules.system;

import me.trae.api.combat.constants.CombatConstants;
import me.trae.api.damage.DamageManager;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotUpdater;
import me.trae.core.updater.annotations.Update;
import me.trae.core.utility.UtilTime;

public class HandleDamageDataUpdater extends SpigotUpdater<Core, DamageManager> {

    public HandleDamageDataUpdater(final DamageManager manager) {
        super(manager);
    }

    @Update(delay = 250L)
    public void onUpdater() {
        this.getManager().getDamageDataMap().values().removeIf(list -> {
            list.removeIf(data -> UtilTime.elapsed(data.getSystemTime(), CombatConstants.COMBAT_DURATION));

            return list.isEmpty();
        });
    }
}