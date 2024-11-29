package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.config.annotations.ConfigInject;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.updater.annotations.Update;
import me.trae.core.updater.interfaces.Updater;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.UtilTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HandleCustomDamageDisplayOnPlayerLevel extends SpigotListener<Core, DamageManager> implements Updater {

    private final Map<UUID, Long> MAP = new HashMap<>();

    @ConfigInject(type = Long.class, path = "Duration", defaultValue = "10_000")
    private long duration;

    public HandleCustomDamageDisplayOnPlayerLevel(final DamageManager manager) {
        super(manager);
    }

    private void reset(final Player player) {
        this.MAP.remove(player.getUniqueId());
        player.setLevel(0);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        final Player damager = event.getDamagerByClass(Player.class);

        damager.setLevel((int) Math.round(event.getDamage()));

        this.MAP.put(damager.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.reset(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.reset(event.getPlayer());
    }

    @Update(delay = 250L, asynchronous = true)
    public void onUpdater() {
        this.MAP.entrySet().removeIf(entry -> {
            final Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                if (!(UtilTime.elapsed(entry.getValue(), this.duration))) {
                    return false;
                }

                player.setLevel(0);
            }

            return true;
        });
    }

    @Override
    public void onShutdown() {
        UtilServer.getOnlinePlayers().forEach(this::reset);
    }
}