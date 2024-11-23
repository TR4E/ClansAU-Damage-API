package me.trae.api.damage.utility;

import me.trae.core.utility.UtilJava;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class UtilDamage {

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