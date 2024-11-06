package me.trae.api.combat.npc.interfaces;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface ICombatNPC {

    UUID getUUID();

    OfflinePlayer getPlayer();

    List<ItemStack> getContents();
}