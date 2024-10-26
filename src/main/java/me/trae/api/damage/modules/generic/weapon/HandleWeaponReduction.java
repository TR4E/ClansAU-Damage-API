package me.trae.api.damage.modules.generic.weapon;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.events.WeaponReductionEvent;
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
    public void onCustomDamage(final CustomDamageEvent event) {
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

        final double defaultReduction = this.getValueByMaterial(itemStack.getType());
        if (defaultReduction == 0.0D) {
            return;
        }

        final WeaponReductionEvent weaponReductionEvent = new WeaponReductionEvent(slotType, materialType, damager, defaultReduction);
        UtilServer.callEvent(weaponReductionEvent);
        if (weaponReductionEvent.isCancelled()) {
            return;
        }

        event.setDamage(weaponReductionEvent.getReduction());
    }

    private double getValueByMaterial(final Material material) {
        switch (material) {
            case DIAMOND_SWORD:
                return 6.0D;

            case DIAMOND_AXE:
            case GOLD_SWORD:
                return 5.0D;

            case GOLD_AXE:
            case IRON_SWORD:
                return 4.0D;

            case IRON_AXE:
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