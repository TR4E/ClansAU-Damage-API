package me.trae.api.damage.events.armour;

import me.trae.api.damage.events.armour.interfaces.IArmourReductionEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ArmourReductionEvent extends CustomCancellableEvent implements IArmourReductionEvent {

    private final ArmourSlotType slotType;
    private final ArmourMaterialType materialType;
    private final ItemStack itemStack;
    private final LivingEntity entity;

    private double reduction;

    public ArmourReductionEvent(final ArmourSlotType slotType, final ArmourMaterialType materialType, final ItemStack itemStack, final LivingEntity entity, final double reduction) {
        this.slotType = slotType;
        this.materialType = materialType;
        this.itemStack = itemStack;
        this.entity = entity;
        this.reduction = reduction;
    }

    @Override
    public ArmourSlotType getSlotType() {
        return this.slotType;
    }

    @Override
    public ArmourMaterialType getMaterialType() {
        return this.materialType;
    }

    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public double getReduction() {
        return this.reduction;
    }

    @Override
    public void setReduction(final double reduction) {
        this.reduction = reduction;
    }
}