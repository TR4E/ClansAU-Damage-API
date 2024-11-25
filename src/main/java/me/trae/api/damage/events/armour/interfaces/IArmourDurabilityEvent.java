package me.trae.api.damage.events.armour.interfaces;

import me.trae.core.event.types.IEntityEvent;
import me.trae.core.utility.enums.ArmourMaterialType;
import me.trae.core.utility.enums.ArmourSlotType;
import org.bukkit.entity.LivingEntity;

public interface IArmourDurabilityEvent extends IEntityEvent<LivingEntity> {

    ArmourSlotType getSlotType();

    ArmourMaterialType getMaterialType();

    int getDurability();

    void setDurability(final int durability);
}