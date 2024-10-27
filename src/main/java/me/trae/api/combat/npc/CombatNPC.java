package me.trae.api.combat.npc;

import me.trae.api.combat.npc.interfaces.ICombatNPC;
import me.trae.core.npc.NPC;
import me.trae.core.utility.UtilMessage;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }
        }

        try {
            FileUtils.forceDelete(new File(String.format("%s/playerdata/%s.dat", player.getWorld().getName(), this.getPlayer().getUniqueId().toString())));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        UtilMessage.simpleBroadcast("Log", "<yellow><var></yellow> has dropped their inventory!", Collections.singletonList(this.getPlayer().getName()));
    }
}