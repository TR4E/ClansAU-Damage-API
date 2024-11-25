package me.trae.api.combat.interfaces;

import me.trae.core.utility.components.time.ExpiredComponent;
import me.trae.core.utility.components.time.GetDurationComponent;
import me.trae.core.utility.components.time.GetSystemTimeComponent;
import me.trae.core.utility.components.time.RemainingComponent;

import java.util.UUID;

public interface ICombat extends GetSystemTimeComponent, GetDurationComponent, RemainingComponent, ExpiredComponent {

    UUID getUUID();
}