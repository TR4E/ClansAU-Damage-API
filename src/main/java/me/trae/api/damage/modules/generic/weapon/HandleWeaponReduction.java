package me.trae.api.damage.modules.generic.weapon;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPreDamageEvent;
import me.trae.api.damage.events.weapon.WeaponReductionEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class HandleWeaponReduction extends SpigotListener<Core, DamageManager> {

    public HandleWeaponReduction(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCustomPreDamage(final CustomPreDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamager() instanceof LivingEntity)) {
            return;
        }

        final LivingEntity damager = event.getDamagerByClass(LivingEntity.class);

        final ItemStack itemStack = event.getItemStack();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
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

        final double defaultReduction = this.getValueByItemStack(itemStack);

        final WeaponReductionEvent weaponReductionEvent = new WeaponReductionEvent(slotType, materialType, damager, itemStack, defaultReduction);
        UtilServer.callEvent(weaponReductionEvent);
        if (weaponReductionEvent.isCancelled()) {
            return;
        }

        if (weaponReductionEvent.getReduction() == 0.0D) {
            return;
        }

        event.setDamage(weaponReductionEvent.getReduction());
    }

    private double getValueByItemStack(final ItemStack itemStack) {
        switch (itemStack.getType()) {
            case STONE_SWORD:
                return 3.0D;

            case STONE_AXE:
            case WOOD_SWORD:
                return 2.0D;

            case WOOD_AXE:
                return 1.0D;
        }

        return 0.0D;
    }
}