package me.trae.api.combat.npc.interfaces;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ICombatNPC {

    OfflinePlayer getPlayer();

    List<ItemStack> getContents();
}