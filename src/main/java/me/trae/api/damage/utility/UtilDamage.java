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

    public static void damage(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage, final String reasonName, final long reasonDuration) {
        final CustomPreDamageEvent event = new CustomPreDamageEvent(damagee, damager, cause, damage);

        event.setReason(reasonName, reasonDuration);

        UtilServer.callEvent(event);
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