package me.trae.api.damage.modules.generic.armour;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.armour.ArmourDurabilityEvent;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilItem;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class HandleArmourDurability extends SpigotListener<Core, DamageManager> {

    public HandleArmourDurability(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamagee() instanceof LivingEntity)) {
            return;
        }

        final LivingEntity damagee = event.getDamageeByClass(LivingEntity.class);

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

            final ArmourDurabilityEvent armourDurabilityEvent = new ArmourDurabilityEvent(slotType, materialType, damagee, 1);
            UtilServer.callEvent(armourDurabilityEvent);
            if (armourDurabilityEvent.isCancelled()) {
                continue;
            }

            UtilItem.takeDurability(damagee, itemStack, true, true);
        }
    }
}