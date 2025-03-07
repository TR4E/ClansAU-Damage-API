package me.trae.api.combat.log;

import me.trae.core.countdown.types.PlayerCountdown;
import me.trae.core.utility.UtilMessage;
import me.trae.core.utility.UtilPlayer;
import me.trae.core.utility.UtilString;
import me.trae.core.utility.UtilTitle;
import org.bukkit.entity.Player;

import java.util.Collections;

public class LogCountdown extends PlayerCountdown {

    public LogCountdown(final long duration, final Player player) {
        super("Safe Log", duration, player);
    }

    @Override
    public void onInitial(final Player player) {
        if (this.getDuration() > 0L) {
            UtilMessage.simpleMessage(player, "Safe Log", "Commencing in <green><var></green>.", Collections.singletonList(this.getDurationString()));
        }
    }

    @Override
    public void onUpdater(final Player player) {
        UtilTitle.sendTitle(player, " ", UtilString.format("Logging in <light_purple>%s", this.getRemainingString()), false, 1000L);
    }

    @Override
    public void onExpired(final Player player) {
        UtilTitle.sendTitle(player, " ", "<green>Safe Logged", true, 1000L);

        UtilPlayer.kick(player, Collections.singletonList("You logged out safely!"));
    }

    @Override
    public boolean removeOnDamage() {
        return true;
    }

    @Override
    public boolean removeOnMovement() {
        return true;
    }
}