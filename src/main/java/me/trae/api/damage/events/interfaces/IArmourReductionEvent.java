package me.trae.api.damage.events.interfaces;

import me.trae.core.event.types.IEntityEvent;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.entity.LivingEntity;

public interface IArmourReductionEvent extends IEntityEvent<LivingEntity> {

    ArmourSlotType getSlotType();

    ArmourMaterialType getMaterialType();

    double getReduction();

    void setReduction(final double reduction);
}