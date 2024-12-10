package me.trae.api.combat.npc;

import me.trae.api.combat.npc.interfaces.ICombatNPC;
import me.trae.core.npc.CustomNPC;
import me.trae.core.npc.models.ClickableNPC;
import me.trae.core.npc.models.ExpirableNPC;
import me.trae.core.player.events.PlayerDisplayNameEvent;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilMessage;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.enums.ClickType;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CombatNPC extends CustomNPC implements ClickableNPC, ExpirableNPC, ICombatNPC {

    private final OfflinePlayer player;
    private final List<ItemStack> contents;

    private final long duration;

    public CombatNPC(final Player player, final long duration) {
        super(EntityType.SHEEP, player.getLocation());

        this.player = Bukkit.getOfflinePlayer(player.getUniqueId());
        this.contents = UtilJava.createCollection(new ArrayList<>(), list -> {
            list.addAll(Arrays.asList(player.getInventory().getArmorContents()));
            list.addAll(Arrays.asList(player.getInventory().getContents()));

            list.removeIf(itemStack -> itemStack == null || itemStack.getType() == Material.AIR);
        });

        this.duration = duration;
    }

    @Override
    public String getDisplayName() {
        return String.format("<yellow><bold>%s <white>- <aqua><bold>%s", this.getPlayer().getName(), "Right Click");
    }

    @Override
    public void updateEntity(final Entity entity) {
        UtilJava.cast(Sheep.class, entity).setColor(DyeColor.CYAN);
    }

    @Override
    public void onClick(final Player player, final ClickType clickType) {
        if (clickType != ClickType.RIGHT) {
            return;
        }

        this.destroy();

        if (this.getContents() != null) {
            for (final ItemStack itemStack : this.getContents()) {
                this.getLocation().getWorld().dropItemNaturally(this.getLocation(), itemStack);
            }

            this.getContents().clear();
        }

        for (final Player target : UtilServer.getOnlinePlayers()) {
            final PlayerDisplayNameEvent playerDisplayNameEvent = new PlayerDisplayNameEvent(this.getPlayer(), target);
            UtilServer.callEvent(playerDisplayNameEvent);

            UtilMessage.simpleMessage(target, "Combat Log", "<var> has dropped their inventory for logging in combat!", Collections.singletonList(playerDisplayNameEvent.getPlayerName()));
        }

        try {
            FileUtils.forceDelete(new File(String.format("%s/playerdata/%s.dat", this.getLocation().getWorld().getName(), this.getPlayer().getUniqueId().toString())));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canClick(final Player player, final ClickType clickType) {
        return !(this.getPlayer().isOnline());
    }

    @Override
    public long getDuration() {
        return this.duration;
    }

    @Override
    public OfflinePlayer getPlayer() {
        return this.player;
    }

    @Override
    public List<ItemStack> getContents() {
        return this.contents;
    }
}