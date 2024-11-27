package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPreDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilBlock;
import me.trae.core.utility.UtilTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HandleCustomDamageDelay extends SpigotListener<Core, DamageManager> {

    private final Map<UUID, Map<String, Long>> MAP = new HashMap<>();

    public HandleCustomDamageDelay(final DamageManager manager) {
        super(manager);
    }

    private boolean isDelayed(final CustomPreDamageEvent event, final long delay) {
        if (!(this.MAP.containsKey(event.getDamagee().getUniqueId()))) {
            this.MAP.put(event.getDamagee().getUniqueId(), new HashMap<>());
        }

        final Map<String, Long> map = this.MAP.get(event.getDamagee().getUniqueId());

        final String key = event.hasDamager() ? event.getDamager().getUniqueId().toString() : event.getCause().name();

        if (map.containsKey(key) && !(UtilTime.elapsed(map.get(key)))) {
            return true;
        }

        map.put(key, System.currentTimeMillis() + delay);
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCustomPreDamage(final CustomPreDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.hasDelay())) {
            event.setDelay(this.getDefaultDamageDelay(event));
        }

        if (event.hasDelay()) {
            if (!(this.isDelayed(event, event.getDelay()))) {
                return;
            }
        }

        event.setCancelled(true);
    }

    private long getDefaultDamageDelay(final CustomPreDamageEvent event) {
        // Already in lava
        if (Arrays.asList(EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE_TICK).contains(event.getCause()) && UtilBlock.isInLava(event.getDamagee().getLocation())) {
            return 0L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return 600L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.POISON) {
            return 1000L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.WITHER) {
            return 800L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            return 800L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            return 200L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            return 600L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            return 1000L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return 400L;
        }

        return 1000L;
    }
}