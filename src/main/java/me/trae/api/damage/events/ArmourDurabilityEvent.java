package me.trae.api.damage.events;

import me.trae.api.damage.events.interfaces.IArmourDurabilityEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.entity.LivingEntity;

public class ArmourDurabilityEvent extends CustomCancellableEvent implements IArmourDurabilityEvent {

    private final ArmourSlotType slotType;
    private final ArmourMaterialType materialType;
    private final LivingEntity entity;

    private int durability;

    public ArmourDurabilityEvent(final ArmourSlotType slotType, final ArmourMaterialType materialType, final LivingEntity entity, final int durability) {
        this.slotType = slotType;
        this.materialType = materialType;
        this.entity = entity;
        this.durability = durability;
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
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public void setDurability(final int durability) {
        this.durability = durability;
    }
}