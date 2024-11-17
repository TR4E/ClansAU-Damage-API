package me.trae.api.death.modules;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.death.DeathManager;
import me.trae.api.death.events.CustomDeathEvent;
import me.trae.api.death.events.CustomDeathMessageEvent;
import me.trae.core.Core;
import me.trae.core.config.annotations.ConfigInject;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilColor;
import me.trae.core.utility.UtilMessage;
import me.trae.core.utility.UtilServer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;
import java.util.Collections;

public class HandleCustomDeathMessage extends SpigotListener<Core, DeathManager> {

    @ConfigInject(type = Boolean.class, path = "Players-Only", defaultValue = "true")
    private boolean playersOnly;

    public HandleCustomDeathMessage(final DeathManager manager) {
        super(manager);
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomDeath(final CustomDeathEvent event) {
        if (!(event.getEntity() instanceof Player) && this.playersOnly) {
            return;
        }

        for (final Player target : UtilServer.getOnlinePlayers()) {
            UtilServer.callEvent(new CustomDeathMessageEvent(event, target));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDeathMessage(final CustomDeathMessageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        this.handleCustomDeathMessage(event);
    }

    private void handleCustomDeathMessage(final CustomDeathMessageEvent event) {
        final Player target = event.getTarget();

        final String entityName = event.getEntityName();
        String killerName = event.getKillerName();

        final CustomDamageEvent damageEvent = event.getDeathEvent().getDamageEvent();

        if (damageEvent.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            UtilMessage.simpleMessage(target, "Death", "<var> committed suicide.", Collections.singletonList(entityName));
            return;
        }

        if (!(damageEvent.getDamager() instanceof Player)) {
            if (damageEvent.getDamager() != null) {
                killerName = String.format("a %s", killerName);
            }

            UtilMessage.simpleMessage(target, "Death", "<var> was killed by <var>.", Arrays.asList(entityName, killerName));
            return;
        }

        String reason = damageEvent.getReasonString();

        final DamageReason damageReason = this.getInstance().getManagerByClass(DamageManager.class).getLastReasonByDamagee(damageEvent.getDamagee(), damageEvent.getDamager());
        if (damageReason != null && !(damageReason.hasExpired())) {
            reason = UtilColor.applyIfNotMatched(ChatColor.LIGHT_PURPLE, damageReason.getName());
        }

        if ((damageReason == null || damageReason.hasExpired()) && !(reason.contains("Air"))) {
            reason = String.format("a %s", reason);
        }

        UtilMessage.simpleMessage(target, "Death", "<var> was killed by <var> with <var>.", Arrays.asList(entityName, killerName, reason));
    }
}