package me.trae.api.damage.modules.generic.armour;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.ArmourReductionEvent;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class HandleArmourReduction extends SpigotListener<Core, DamageManager> {

    public HandleArmourReduction(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamagee() instanceof LivingEntity)) {
            return;
        }

        final LivingEntity damagee = event.getDamageeByClass(LivingEntity.class);

        double reductionPoints = 0.0D;

        for (final ItemStack itemStack : damagee.getEquipment().getArmorContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            final ArmourSlotType slotType = ArmourSlotType.getByMaterial(itemStack.getType());
            if (slotType == null) {
                continue;
            }

            final ArmourMaterialType materialType = ArmourMaterialType.getByMaterial(itemStack.getType());
            if (materialType == null) {
                continue;
            }

            final double defaultReduction = this.getValueByMaterial(itemStack.getType());
            if (defaultReduction == 0.0D) {
                continue;
            }

            final ArmourReductionEvent armourReductionEvent = new ArmourReductionEvent(slotType, materialType, damagee, defaultReduction);
            UtilServer.callEvent(armourReductionEvent);
            if (armourReductionEvent.isCancelled()) {
                continue;
            }

            reductionPoints += armourReductionEvent.getReduction();
        }

        event.setDamage(event.getDamage() * (100.0D / reductionPoints) / 100.0D);
    }

    private double getValueByMaterial(final Material material) {
        switch (material) {
            case DIAMOND_HELMET:
            case LEATHER_LEGGINGS:
                return 10.0D;

            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case GOLD_HELMET:
            case IRON_BOOTS:
            case GOLD_BOOTS:
                return 8.0D;

            case LEATHER_HELMET:
            case LEATHER_BOOTS:
                return 6.0D;

            case DIAMOND_CHESTPLATE:
            case IRON_CHESTPLATE:
                return 24.0D;

            case GOLD_CHESTPLATE:
                return 22.0D;

            case CHAINMAIL_CHESTPLATE:
            case IRON_LEGGINGS:
            case GOLD_LEGGINGS:
                return 20.0D;

            case LEATHER_CHESTPLATE:
                return 14.0D;

            case DIAMOND_LEGGINGS:
                return 18.0D;

            case CHAINMAIL_LEGGINGS:
                return 16.0D;

            case DIAMOND_BOOTS:
                return 12.0D;

            case CHAINMAIL_BOOTS:
                return 1.8D;
        }

        return 0.0D;
    }
}