package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class HandleCustomDamagePotionEffects extends SpigotListener<Core, DamageManager> {

    public HandleCustomDamagePotionEffects(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }

        if (!(event.getDamagee() instanceof LivingEntity)) {
            return;
        }

        if (!(event.getDamager() instanceof LivingEntity)) {
            return;
        }

        final LivingEntity damagee = event.getDamageeByClass(LivingEntity.class);
        final LivingEntity damager = event.getDamagerByClass(LivingEntity.class);

        double damage = event.getDamage();

        if (UtilEntity.hasPotionEffect(damager, PotionEffectType.INCREASE_DAMAGE)) {
            damage += UtilEntity.getPotionEffectAmplifier(damager, PotionEffectType.INCREASE_DAMAGE) * 1.5D;
        }

        if (UtilEntity.hasPotionEffect(damagee, PotionEffectType.WEAKNESS)) {
            damage += UtilEntity.getPotionEffectAmplifier(damager, PotionEffectType.WEAKNESS) * 1.5D;
        }

        if (UtilEntity.hasPotionEffect(damagee, PotionEffectType.DAMAGE_RESISTANCE)) {
            damage -= UtilEntity.getPotionEffectAmplifier(damager, PotionEffectType.DAMAGE_RESISTANCE) * 1.5D;
        }

        event.setDamage(damage);
    }
}