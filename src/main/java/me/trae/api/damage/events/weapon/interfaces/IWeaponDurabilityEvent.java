package me.trae.api.damage.events.weapon.interfaces;

import me.trae.core.event.types.IEntityEvent;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.entity.LivingEntity;

public interface IWeaponDurabilityEvent extends IEntityEvent<LivingEntity> {

    WeaponSlotType getSlotType();

    WeaponMaterialType getMaterialType();

    int getDurability();

    void setDurability(final int durability);
}