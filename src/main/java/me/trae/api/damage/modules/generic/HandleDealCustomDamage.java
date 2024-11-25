package me.trae.api.damage.modules.generic;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.events.damage.CustomPostDamageEvent;
import me.trae.api.death.events.CustomDeathEvent;
import me.trae.core.Core;
import me.trae.core.framework.types.frame.SpigotListener;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilMath;
import me.trae.core.utility.UtilServer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.EntityEffect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HandleDealCustomDamage extends SpigotListener<Core, DamageManager> {

    public HandleDealCustomDamage(final DamageManager manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCustomPostDamage(final CustomPostDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamagee() instanceof Item) {
            event.getDamagee().remove();
            return;
        }

        if (!(event.getDamagee() instanceof Damageable)) {
            return;
        }

        final EntityLiving entityLivingDamagee = event.getDamageeByClass(CraftLivingEntity.class).getHandle();
        final EntityLiving entityLivingDamager = (event.getDamager() instanceof LivingEntity ? event.getDamagerByClass(CraftLivingEntity.class).getHandle() : null);

        if (entityLivingDamager != null) {
            // Apply interaction between damagee and damager (E.G: Hitting a Spider in Daylight, causes it to target the Damager)
            entityLivingDamagee.b(entityLivingDamager);
        }

        this.getManager().addLastDamageData(event);

        if (entityLivingDamagee.getHealth() > 0.0F) {
            this.handleDamage(event, entityLivingDamagee);
        }

        if (entityLivingDamagee.getHealth() <= 0.0F) {
            event.setKnockback(0.0D);
            this.handleDeath(event, entityLivingDamagee, entityLivingDamager);
        }
    }

    private void handleDamage(final CustomPostDamageEvent event, final EntityLiving entityLivingDamagee) {
        event.getDamagee().playEffect(EntityEffect.HURT);

        entityLivingDamagee.setHealth(UtilMath.getMinAndMax(Float.class, 0.0F, entityLivingDamagee.getMaxHealth(), entityLivingDamagee.getHealth() - (float) event.getDamage()));
    }

    private void handleDeath(final CustomPostDamageEvent event, final EntityLiving entityLivingDamagee, final EntityLiving entityLivingDamager) {
        DamageSource damageSource = DamageSource.GENERIC;

        final Entity damager = event.getDamager();

        if (entityLivingDamager instanceof EntityHuman) {
            final EntityHuman entityHumanDamager = UtilJava.cast(EntityHuman.class, entityLivingDamager);

            entityLivingDamagee.killer = entityHumanDamager;
            damageSource = DamageSource.playerAttack(entityHumanDamager);
        } else if (damager instanceof EntityArrow) {
            damageSource = DamageSource.arrow(UtilJava.cast(EntityArrow.class, damager), entityLivingDamagee);
        } else if (damager instanceof EntityFireball) {
            damageSource = DamageSource.fireball(UtilJava.cast(EntityFireball.class, damager), entityLivingDamagee);
        } else if (damager instanceof EntityProjectile) {
            damageSource = DamageSource.projectile(UtilJava.cast(EntityProjectile.class, damager), entityLivingDamagee);
        } else if (damager instanceof Explosion) {
            damageSource = DamageSource.explosion(UtilJava.cast(Explosion.class, damager));
        } else if (entityLivingDamager != null) {
            damageSource = DamageSource.mobAttack(entityLivingDamager);
        }

        entityLivingDamagee.die(damageSource);

        event.getDamagee().playEffect(EntityEffect.DEATH);

        CustomPostDamageEvent data = this.getInstance().getManagerByClass(DamageManager.class).getLastDamageDataByDamagee(event.getDamagee());
        if (data == null) {
            data = event;
        }

        UtilServer.callEvent(new CustomDeathEvent(data));
    }
}