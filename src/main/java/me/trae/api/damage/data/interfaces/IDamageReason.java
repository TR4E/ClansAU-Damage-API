package me.trae.api.damage.data.interfaces;

import me.trae.core.utility.components.time.ExpiredComponent;
import me.trae.core.utility.components.time.GetDurationComponent;
import me.trae.core.utility.components.time.GetSystemTimeComponent;

public interface IDamageReason extends GetSystemTimeComponent, GetDurationComponent, ExpiredComponent {

    String getName();

    String getExtraName();

    String getDisplayName();
}