package me.trae.api.damage.events.armour;

import me.trae.api.damage.events.armour.interfaces.IArmourDurabilityEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ArmourDurabilityEvent extends CustomCancellableEvent implements IArmourDurabilityEvent {

    private final ArmourSlotType slotType;
    private final ArmourMaterialType materialType;
    private final LivingEntity entity;
    private final ItemStack itemStack;

    private int durability;

    public ArmourDurabilityEvent(final ArmourSlotType slotType, final ArmourMaterialType materialType, final ItemStack itemStack, final LivingEntity entity, final int durability) {
        this.slotType = slotType;
        this.materialType = materialType;
        this.itemStack = itemStack;
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
    public ItemStack getItemStack() {
        return this.itemStack;
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