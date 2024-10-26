package me.trae.api.damage.events.interfaces;

import me.trae.core.event.types.IEntityEvent;
import me.trae.core.utility.enums.WeaponMaterialType;
import me.trae.core.utility.enums.WeaponSlotType;
import org.bukkit.entity.LivingEntity;

public interface IWeaponReductionEvent extends IEntityEvent<LivingEntity> {

    WeaponSlotType getSlotType();

    WeaponMaterialType getMaterialType();

    double getReduction();

    void setReduction(final double reduction);
}