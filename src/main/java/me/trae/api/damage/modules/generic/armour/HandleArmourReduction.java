package me.trae.api.damage.modules.generic.armour;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.armour.ArmourReductionEvent;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
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

            final ArmourReductionEvent armourReductionEvent = new ArmourReductionEvent(slotType, materialType, itemStack, damagee, this.getValueByMaterial(itemStack.getType()));
            UtilServer.callEvent(armourReductionEvent);
            if (armourReductionEvent.isCancelled() || armourReductionEvent.getReduction() == 0.0D) {
                continue;
            }

            reductionPoints += armourReductionEvent.getReduction();
        }

        event.setDamage(event.getDamage() * (100.0D - reductionPoints) / 100.0D);
    }

    private double getValueByMaterial(final Material material) {
        switch (material) {
            case DIAMOND_HELMET:
            case DIAMOND_BOOTS:
            case GOLD_LEGGINGS:
            case LEATHER_CHESTPLATE:
                return 3.0;

            case DIAMOND_CHESTPLATE:
                return 8.0;

            case DIAMOND_LEGGINGS:
            case IRON_CHESTPLATE:
                return 6.0;

            case IRON_HELMET:
            case IRON_BOOTS:
            case GOLD_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_LEGGINGS:
                return 2.0;

            case IRON_LEGGINGS:
            case GOLD_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
                return 5.0;

            case GOLD_BOOTS:
            case LEATHER_HELMET:
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
                return 1.0;

            case CHAINMAIL_LEGGINGS:
                return 4.0;
        }

        return 0.0D;
    }
}