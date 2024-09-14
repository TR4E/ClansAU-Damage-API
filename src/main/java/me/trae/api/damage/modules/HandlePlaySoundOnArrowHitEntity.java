package me.trae.api.damage.modules;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandlePlaySoundOnArrowHitEntity extends SpigotListener<Core, DamageManager> {

    public HandlePlaySoundOnArrowHitEntity(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getProjectile() instanceof Arrow)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        new SoundCreator(Sound.ORB_PICKUP).play(event.getDamagerByClass(Player.class));
    }
}