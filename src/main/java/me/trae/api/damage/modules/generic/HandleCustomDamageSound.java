package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.damage.utility.constants.DamageConstants;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleCustomDamageSound extends SpigotListener<Core, DamageManager> {

    public HandleCustomDamageSound(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomDamage(final CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.hasSoundCreator())) {
            return;
        }

        if (!(event.getDamagee() instanceof LivingEntity)) {
            return;
        }

        SoundCreator soundCreator = event.getSoundCreator();

        final LivingEntity damagee = event.getDamageeByClass(LivingEntity.class);

        if (soundCreator.getSound() == Sound.HURT_FLESH) {
            final Sound sound = (event.getDamageeByClass(LivingEntity.class).getHealth() > 0.0D ? DamageConstants.getEntityHurtSound(damagee) : DamageConstants.getEntityDeathSound(damagee));
            if (sound != null) {
                soundCreator = new SoundCreator(sound);
            }
        }

        soundCreator.play(damagee.getLocation());
    }
}