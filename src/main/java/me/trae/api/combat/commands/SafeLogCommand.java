package me.trae.api.combat.commands;

import me.trae.api.combat.CombatManager;
import me.trae.api.combat.log.LogCountdown;
import me.trae.core.Core;
import me.trae.core.client.Client;
import me.trae.core.client.enums.Rank;
import me.trae.core.command.types.Command;
import me.trae.core.command.types.models.PlayerCommandType;
import me.trae.core.config.annotations.ConfigInject;
import me.trae.core.countdown.CountdownManager;
import me.trae.core.gamer.Gamer;
import me.trae.core.utility.UtilMessage;
import org.bukkit.entity.Player;

public class SafeLogCommand extends Command<Core, CombatManager> implements PlayerCommandType {

    @ConfigInject(type = Long.class, path = "Duration", defaultValue = "10_000")
    private long duration;

    public SafeLogCommand(final CombatManager manager) {
        super(manager, "safelog", new String[]{"combatlog", "log"}, Rank.DEFAULT);
    }

    @Override
    public String getDescription() {
        return "Log out safely from Combat";
    }

    @Override
    public void execute(final Player player, final Client client, final Gamer gamer, final String[] args) {
        final CountdownManager countdownManager = this.getInstance().getManagerByClass(CountdownManager.class);

        if (countdownManager.getCountdownByPlayer(player) instanceof LogCountdown) {
            UtilMessage.message(player, "Safe Log", "You are already attempting to Safe Log!");
            return;
        }

        final LogCountdown logCountdown = new LogCountdown(this.duration, player);

        countdownManager.addCountdown(logCountdown);
    }
}