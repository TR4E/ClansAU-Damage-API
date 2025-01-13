package me.trae.api.death.modules;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.death.DeathManager;
import me.trae.api.death.events.CustomDeathEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilColor;
import me.trae.core.utility.UtilMessage;
import me.trae.core.utility.UtilString;
import me.trae.core.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HandleDeathSummaryMessage extends SpigotListener<Core, DeathManager> {

    public HandleDeathSummaryMessage(final DeathManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCustomDeath(final CustomDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        final Player player = event.getEntityByClass(Player.class);

        final List<CustomPostDamageEvent> list = this.getInstance().getManagerByClass(DamageManager.class).getListOfDamageDataByDamagee(player);
        if (list.isEmpty()) {
            return;
        }

        Collections.reverse(list);

        for (int i = 0; i < list.size(); i++) {
            final CustomPostDamageEvent data = list.get(i);
            final int index = i + 1;

            if (data.getDamager() instanceof Player) {
                UtilMessage.simpleMessage(player, UtilString.pair("<dark_green>#<var>", "<reset>[<var>] [<var>] [<red><var> dmg</red>] [<light_purple><var> Prior</light_purple>]"), Arrays.asList(String.valueOf(index), data.getDamagerName(), this.getReason(data), String.valueOf(data.getDamage()), UtilTime.getTime(System.currentTimeMillis() - data.getSystemTime())));
            } else {
                UtilMessage.simpleMessage(player, UtilString.pair("<dark_green>#<var>", "<reset>[<var>] [<red><var> dmg</red>] [<light_purple><var> Prior</light_purple>]"), Arrays.asList(String.valueOf(index), data.getDamagerName(), String.valueOf(data.getDamage()), UtilTime.getTime(System.currentTimeMillis() - data.getSystemTime())));
            }

            if (index == 10) {
                break;
            }
        }
    }

    private String getReason(final CustomPostDamageEvent data) {
        if (data.hasDamager()) {
            final DamageReason damageReason = this.getInstance().getManagerByClass(DamageManager.class).getLastReasonByDamagee(data.getDamagee(), data.getDamager());
            if (damageReason != null && !(damageReason.hasExpired())) {
                return UtilColor.applyIfMissing(ChatColor.valueOf(this.getManager().customReasonChatColor), damageReason.getName());
            }
        }

        return data.getReasonString();
    }
}