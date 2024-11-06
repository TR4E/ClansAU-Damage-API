package me.trae.api.combat.npc;

import me.trae.api.combat.npc.interfaces.ICombatNPC;
import me.trae.core.npc.NPC;
import me.trae.core.player.events.PlayerDisplayNameEvent;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilMessage;
import me.trae.core.utility.UtilServer;
import org.apache.commons.io.FileUtils;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Collections;

public abstract class CombatNPC extends NPC implements ICombatNPC {

    @Override
    public EntityType getEntityType() {
        return EntityType.SHEEP;
    }

    @Override
    public String getDisplayName() {
        return String.format("<yellow><bold>%s <white>- <aqua><bold>%s", this.getPlayer().getName(), "Right-Click me");
    }

    @Override
    public void onInteract(final Player player) {
        this.remove();

        if (this.getContents() != null) {
            for (final ItemStack itemStack : this.getContents()) {
                player.getWorld().dropItemNaturally(this.getEntity().getLocation(), itemStack);
            }

            this.getContents().clear();
        }

        try {
            FileUtils.forceDelete(new File(String.format("%s/playerdata/%s.dat", player.getWorld().getName(), this.getPlayer().getUniqueId().toString())));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        for (final Player target : UtilServer.getOnlinePlayers()) {
            final PlayerDisplayNameEvent playerDisplayNameEvent = new PlayerDisplayNameEvent(player, target);
            UtilServer.callEvent(playerDisplayNameEvent);

            UtilMessage.simpleMessage(target, "Log", "<var> has dropped their inventory for logging in combat!", Collections.singletonList(playerDisplayNameEvent.getPlayerName()));
        }
    }

    @Override
    public void updateEntity(final LivingEntity entity) {
        UtilJava.cast(Sheep.class, entity).setColor(DyeColor.CYAN);
    }

    @Override
    public void purge() {
        if (this.getContents() != null) {
            for (final ItemStack itemStack : this.getContents()) {
                this.getLocation().getWorld().dropItemNaturally(this.getLocation(), itemStack);
            }

            this.getContents().clear();
        }

        super.purge();
    }
}