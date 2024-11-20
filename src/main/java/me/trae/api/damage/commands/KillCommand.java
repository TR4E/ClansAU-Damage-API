package me.trae.api.damage.commands;

import me.trae.api.damage.DamageManager;
import me.trae.core.Core;
import me.trae.core.client.Client;
import me.trae.core.client.enums.Rank;
import me.trae.core.command.types.Command;
import me.trae.core.command.types.models.PlayerCommandType;
import me.trae.core.gamer.Gamer;
import me.trae.core.utility.UtilServer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class KillCommand extends Command<Core, DamageManager> implements PlayerCommandType {

    public KillCommand(final DamageManager manager) {
        super(manager, "kill", new String[]{"suicide"}, Rank.ADMIN);
    }

    @Override
    public void execute(final Player player, final Client client, final Gamer gamer, final String[] args) {
        if (args.length == 0) {
            UtilServer.callEvent(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, Integer.MAX_VALUE));
        }
    }
}