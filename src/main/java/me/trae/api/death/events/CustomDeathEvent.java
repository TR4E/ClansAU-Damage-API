package me.trae.api.death.events;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.death.events.interfaces.ICustomDeathEvent;
import me.trae.core.Core;
import me.trae.core.event.CustomEvent;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CustomDeathEvent extends CustomEvent implements ICustomDeathEvent {

    private final CustomPostDamageEvent damageEvent;
    private final int assists;

    public CustomDeathEvent(final CustomPostDamageEvent damageEvent) {
        this.damageEvent = damageEvent;
        this.assists = UtilJava.get(new ArrayList<>(UtilPlugin.getInstanceByClass(Core.class).getManagerByClass(DamageManager.class).getListOfDamageDataByDamagee(damageEvent.getDamagee())), list -> {
            list.removeIf(event -> !(event.getDamager() instanceof Player));

            if (damageEvent.hasDamager()) {
                list.removeIf(event -> event.hasDamager() && event.getDamager().equals(damageEvent.getDamager()));
            }

            return list.size();
        });
    }

    @Override
    public CustomPostDamageEvent getDamageEvent() {
        return this.damageEvent;
    }

    @Override
    public int getAssists() {
        return this.assists;
    }
}