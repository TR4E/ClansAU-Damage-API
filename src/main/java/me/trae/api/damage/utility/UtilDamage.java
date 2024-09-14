package me.trae.api.damage.utility;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.api.death.events.CustomDeathEvent;
import me.trae.core.Core;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilMath;
import me.trae.core.utility.UtilPlugin;
import me.trae.core.utility.UtilServer;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.EntityEffect;
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

        if (entityLivingDamagee.getHealth() > 0.0F) {
            event.getDamagee().playEffect(EntityEffect.HURT);

            entityLivingDamagee.setHealth(UtilMath.getMinAndMax(Float.class, 0.0F, entityLivingDamagee.getMaxHealth(), entityLivingDamagee.getHealth() - (float) event.getFinalDamage()));
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

            event.getDamagee().playEffect(EntityEffect.DEATH);

            CustomDamageEvent data = UtilPlugin.getInstance(Core.class).getManagerByClass(DamageManager.class).getLastDamageDataByDamagee(event.getDamagee());
            if (data == null) {
                data = event;
            }

            UtilServer.callEvent(new CustomDeathEvent(data));
        }
    }
}