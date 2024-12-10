package me.trae.api.damage.events.armour.interfaces;

import me.trae.core.event.types.IEntityEvent;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface IArmourReductionEvent extends IEntityEvent<LivingEntity> {

    ArmourSlotType getSlotType();

    ArmourMaterialType getMaterialType();

    ItemStack getItemStack();

    double getReduction();

    void setReduction(final double reduction);
}