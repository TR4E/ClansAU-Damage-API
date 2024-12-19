package me.trae.api.damage.commands;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.utility.UtilDamage;
import me.trae.core.Core;
import me.trae.core.client.Client;
import me.trae.core.client.enums.Rank;
import me.trae.core.command.types.Command;
import me.trae.core.command.types.models.PlayerCommandType;
import me.trae.core.gamer.Gamer;
import me.trae.core.utility.UtilPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class KillCommand extends Command<Core, DamageManager> implements PlayerCommandType {

    public KillCommand(final DamageManager manager) {
        super(manager, "kill", new String[]{"suicide"}, Rank.ADMIN);
    }

    @Override
    public void execute(final Player player, final Client client, final Gamer gamer, final String[] args) {
        if (args.length == 0) {
            UtilDamage.damage(player, EntityDamageEvent.DamageCause.SUICIDE, player.getHealth());
            return;
        }

        if (args.length == 1) {
            final Player targetPlayer = UtilPlayer.searchPlayer(player, args[0], true);
            if (targetPlayer == null) {
                return;
            }

            UtilDamage.damage(targetPlayer, EntityDamageEvent.DamageCause.SUICIDE, targetPlayer.getHealth());
        }
    }
}