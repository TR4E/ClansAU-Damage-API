package me.trae.api.combat.interfaces;

import me.trae.core.utility.components.ExpiredComponent;
import me.trae.core.utility.components.GetDurationComponent;
import me.trae.core.utility.components.GetSystemTimeComponent;
import me.trae.core.utility.components.RemainingComponent;

import java.util.UUID;

public interface ICombat extends GetSystemTimeComponent, GetDurationComponent, RemainingComponent, ExpiredComponent {

    UUID getUUID();
}