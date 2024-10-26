package me.trae.api.damage.events;

import me.trae.api.damage.events.interfaces.IWeaponReductionEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.entity.LivingEntity;

public class WeaponReductionEvent extends CustomCancellableEvent implements IWeaponReductionEvent {

    private final WeaponSlotType slotType;
    private final WeaponMaterialType materialType;
    private final LivingEntity entity;

    private double reduction;

    public WeaponReductionEvent(final WeaponSlotType slotType, final WeaponMaterialType materialType, final LivingEntity entity, final double reduction) {
        this.slotType = slotType;
        this.materialType = materialType;
        this.entity = entity;
        this.reduction = reduction;
    }

    @Override
    public WeaponSlotType getSlotType() {
        return this.slotType;
    }

    @Override
    public WeaponMaterialType getMaterialType() {
        return this.materialType;
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