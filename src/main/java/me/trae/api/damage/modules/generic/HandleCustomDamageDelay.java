package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPreDamageEvent;
import me.trae.core.Core;
import me.trae.core.config.annotations.ConfigInject;
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

    @ConfigInject(type = Long.class, path = "Entity-Attack-Delay", defaultValue = "500")
    private long entityAttackDelay;

    @ConfigInject(type = Long.class, path = "Poison-Delay", defaultValue = "1_000")
    private long poisonDelay;

    @ConfigInject(type = Long.class, path = "Wither-Delay", defaultValue = "800")
    private long witherDelay;

    @ConfigInject(type = Long.class, path = "Suffocation-Delay", defaultValue = "800")
    private long suffocationDelay;

    @ConfigInject(type = Long.class, path = "Void-Delay", defaultValue = "200")
    private long voidDelay;

    @ConfigInject(type = Long.class, path = "Fire-Delay", defaultValue = "600")
    private long fireDelay;

    @ConfigInject(type = Long.class, path = "Fire-Tick-Delay", defaultValue = "1_000")
    private long fireTickDelay;

    @ConfigInject(type = Long.class, path = "Lava-Delay", defaultValue = "400")
    private long lavaDelay;

    @ConfigInject(type = Long.class, path = "Default-Delay", defaultValue = "1_000")
    private long defaultDelay;

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
            return this.entityAttackDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.POISON) {
            return this.poisonDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.WITHER) {
            return this.witherDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            return this.suffocationDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            return this.voidDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            return this.fireDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            return this.fireTickDelay;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return this.lavaDelay;
        }

        return this.defaultDelay;
    }
}