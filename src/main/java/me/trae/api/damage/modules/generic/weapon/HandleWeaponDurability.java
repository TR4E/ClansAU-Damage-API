package me.trae.api.damage.modules.generic.weapon;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.damage.events.weapon.WeaponDurabilityEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilItem;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HandleWeaponDurability extends SpigotListener<Core, DamageManager> {

    public HandleWeaponDurability(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamager() instanceof LivingEntity)) {
            return;
        }

        final LivingEntity damager = event.getDamagerByClass(LivingEntity.class);

        if (damager instanceof Player && Arrays.asList(GameMode.CREATIVE, GameMode.SPECTATOR).contains(event.getDamagerByClass(Player.class).getGameMode())) {
            return;
        }

        final ItemStack itemStack = event.getItemStack();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        if (!(this.canTakeDurability(itemStack))) {
            return;
        }

        final WeaponSlotType slotType = WeaponSlotType.getByMaterial(itemStack.getType());
        if (slotType == null) {
            return;
        }

        final WeaponMaterialType materialType = WeaponMaterialType.getByMaterial(itemStack.getType());
        if (materialType == null) {
            return;
        }

        final WeaponDurabilityEvent weaponDurabilityEvent = new WeaponDurabilityEvent(slotType, materialType, itemStack, damager, 1);
        UtilServer.callEvent(weaponDurabilityEvent);
        if (weaponDurabilityEvent.isCancelled() || weaponDurabilityEvent.getDurability() == 0) {
            if (event.getDamager() instanceof Player) {
                event.getDamagerByClass(Player.class).updateInventory();
            }
            return;
        }

        UtilItem.takeDurability(damager, itemStack, false, true);
    }

    private boolean canTakeDurability(final ItemStack itemStack) {
        return itemStack.getType().getMaxDurability() > 0;
    }
}