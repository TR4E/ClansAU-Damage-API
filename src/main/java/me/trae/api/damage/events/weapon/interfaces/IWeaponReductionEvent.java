package me.trae.api.damage.events.weapon.interfaces;

import me.trae.core.event.types.IEntityEvent;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface IWeaponReductionEvent extends IEntityEvent<LivingEntity> {

    WeaponSlotType getSlotType();

    WeaponMaterialType getMaterialType();

    ItemStack getItemStack();

    double getReduction();

    void setReduction(final double reduction);
}