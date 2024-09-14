package me.trae.api.damage.data.interfaces;

import me.trae.core.utility.components.ExpiredComponent;
import me.trae.core.utility.components.GetDurationComponent;
import me.trae.core.utility.components.GetSystemTimeComponent;

public interface IDamageReason extends GetSystemTimeComponent, GetDurationComponent, ExpiredComponent {

    String getName();
}