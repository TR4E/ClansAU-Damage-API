package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.events.DamageDelayEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilBlock;
import me.trae.core.utility.UtilServer;
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

    private boolean isDelayed(final CustomDamageEvent event, final long delay) {
        if (!(this.MAP.containsKey(event.getDamagee().getUniqueId()))) {
            this.MAP.put(event.getDamagee().getUniqueId(), new HashMap<>());
        }

        final Map<String, Long> map = this.MAP.get(event.getDamagee().getUniqueId());

        final String key = event.hasDamager() ? event.getDamager().getUniqueId().toString() : event.getCause().name();

        if (map.containsKey(key)) {
            if (UtilTime.elapsed(map.get(key))) {
                map.remove(key);
                return false;
            }

            return true;
        }

        map.put(key, System.currentTimeMillis() + delay);
        return false;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final DamageDelayEvent damageDelayEvent = new DamageDelayEvent(event, this.getDefaultDamageDelay(event));
        UtilServer.callEvent(damageDelayEvent);
        if (damageDelayEvent.isCancelled()) {
            return;
        }

        if (damageDelayEvent.hasDelay()) {
            if (!(this.isDelayed(event, damageDelayEvent.getDelay()))) {
                return;
            }
        }

        event.setCancelled(true);
    }

    private long getDefaultDamageDelay(final CustomDamageEvent event) {
        // Already in lava
        if (Arrays.asList(EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE_TICK).contains(event.getCause()) && UtilBlock.isInLava(event.getDamagee().getLocation())) {
            return 0L;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return 700L;
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
            return 1L;
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