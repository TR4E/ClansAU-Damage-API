package me.trae.api.damage.utility;

import me.trae.api.damage.events.damage.CustomPreDamageEvent;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilServer;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;

public class UtilDamage {

    public static void damage(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage, final long delay, final String reasonName, final long reasonDuration) {
        final CustomPreDamageEvent event = new CustomPreDamageEvent(damagee, damager, cause, damage);

        event.setDelay(delay);

        if (reasonName != null && reasonDuration > 0) {
            event.setReason(reasonName, reasonDuration);
        }

        UtilServer.callEvent(event);
    }

    public static void damage(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage, final String reasonName, final long reasonDuration) {
        damage(damagee, damager, cause, damage, 0L, reasonName, reasonDuration);
    }

    public static void damage(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage, final long delay) {
        damage(damagee, damager, cause, damage, delay, null, 0L);
    }

    public static void damage(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage) {
        UtilServer.callEvent(new CustomPreDamageEvent(damagee, damager, cause, damage));
    }

    public static void damage(final Entity damagee, final EntityDamageEvent.DamageCause cause, final double damage) {
        UtilServer.callEvent(new CustomPreDamageEvent(damagee, cause, damage));
    }

    public static boolean isInvulnerable(final Entity entity) {
        if (entity instanceof Player) {
            final Player player = UtilJava.cast(Player.class, entity);

            if (Arrays.asList(GameMode.CREATIVE, GameMode.SPECTATOR).contains(player.getGameMode())) {
                return true;
            }

            if (UtilJava.cast(CraftPlayer.class, player).getHandle().isSpectator()) {
                return true;
            }
        }

        return false;
    }
}