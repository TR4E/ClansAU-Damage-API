package me.trae.api.damage.events.weapon;

import me.trae.api.damage.events.weapon.interfaces.IWeaponDurabilityEvent;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class WeaponDurabilityEvent extends CustomCancellableEvent implements IWeaponDurabilityEvent {

    private final WeaponSlotType slotType;
    private final WeaponMaterialType materialType;
    private final ItemStack itemStack;
    private final LivingEntity entity;

    private int durability;

    public WeaponDurabilityEvent(final WeaponSlotType slotType, final WeaponMaterialType materialType, final ItemStack itemStack, final LivingEntity entity, final int durability) {
        this.slotType = slotType;
        this.materialType = materialType;
        this.itemStack = itemStack;
        this.entity = entity;
        this.durability = durability;
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