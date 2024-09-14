package me.trae.api.damage.utility;

import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.utility.UtilJava;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

public class UtilDamage {

    public static void applyVanillaDamageMechanics(final CustomDamageEvent event) {
        final EntityLiving entityLivingDamagee = event.getDamageeByClass(CraftLivingEntity.class).getHandle();
        final EntityLiving entityLivingDamager = (event.getDamager() instanceof LivingEntity ? event.getDamagerByClass(CraftLivingEntity.class).getHandle() : null);

        // Apply interaction between damagee and damager
        if (entityLivingDamager != null) {
            entityLivingDamagee.b(entityLivingDamager);
        }

        // Handle death scenario
        if (entityLivingDamagee.getHealth() <= 0.0F) {
            DamageSource damageSource = DamageSource.GENERIC;

            if (entityLivingDamager != null) {
                if (entityLivingDamager instanceof EntityHuman) {
                    final EntityHuman entityHumanDamager = UtilJava.cast(EntityHuman.class, entityLivingDamager);

                    entityLivingDamagee.killer = entityHumanDamager;
                    damageSource = DamageSource.playerAttack(entityHumanDamager);
                } else {
                    damageSource = DamageSource.mobAttack(entityLivingDamager);
                }
            }

            entityLivingDamagee.die(damageSource);
        }
    }
}