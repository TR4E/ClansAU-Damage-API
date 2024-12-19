package me.trae.api.damage.events.damage.abstracts;

import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.damage.abstracts.interfaces.IDamageEvent;
import me.trae.api.damage.utility.constants.DamageConstants;
import me.trae.core.Core;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.player.events.PlayerDisplayNameEvent;
import me.trae.core.utility.UtilPlugin;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageEvent extends CustomCancellableEvent implements IDamageEvent {

    private final Entity damagee, damager;
    private final Projectile projectile;
    private final EntityDamageEvent.DamageCause cause;
    private final String causeString, reasonString;

    private long systemTime;

    private ItemStack itemStack;

    private double damage, knockback;
    private long delay;

    private SoundCreator soundCreator;
    private DamageReason reason;

    private String damageeName, damagerName;

    public DamageEvent(final IDamageEvent event) {
        this.systemTime = event.getSystemTime();

        this.damagee = event.getDamagee();
        this.damager = event.getDamager();
        this.projectile = event.getProjectile();
        this.cause = event.getCause();
        this.causeString = event.getCauseString();
        this.reasonString = event.getReasonString();

        this.itemStack = event.getItemStack();

        this.damage = event.getDamage();
        this.knockback = event.getKnockback();
        this.delay = event.getDelay();

        this.soundCreator = event.getSoundCreator();
        this.reason = event.getReason();

        this.damageeName = event.getDamageeName();
        this.damagerName = event.getDamagerName();
    }

    // PreDamageEvent
    public DamageEvent(final Entity damagee, final Entity damager, final Projectile projectile, final EntityDamageEvent.DamageCause cause, final double damage) {
        this.systemTime = System.currentTimeMillis();

        this.damagee = damagee;
        this.damager = damager;
        this.projectile = projectile;
        this.cause = cause;

        if (damager instanceof LivingEntity) {
            this.itemStack = this.getDamagerByClass(LivingEntity.class).getEquipment().getItemInHand();
        }

        this.damage = damage;
        this.knockback = 1.0D;
        this.delay = 0L;

        this.soundCreator = new SoundCreator(Sound.HURT_FLESH);

        this.causeString = DamageConstants.createDefaultCauseString(this);
        this.reasonString = DamageConstants.createDefaultReasonString(this);

        if (this.getDamagee() instanceof Player && this.getDamager() instanceof Player) {
            final Player damageePlayer = this.getDamageeByClass(Player.class);
            final Player damagerPlayer = this.getDamagerByClass(Player.class);

            this.damageeName = UtilServer.getEvent(new PlayerDisplayNameEvent(damageePlayer, damagerPlayer)).getPlayerName();
            this.damagerName = UtilServer.getEvent(new PlayerDisplayNameEvent(damagerPlayer, damageePlayer)).getPlayerName();
        } else {
            this.damageeName = String.format("<yellow>%s", this.getDamagee().getName());
            this.damagerName = String.format("<yellow>%s", (this.getDamager() != null ? this.getDamager().getName() : this.getCauseString()));
        }
    }

    @Override
    public Entity getDamagee() {
        return this.damagee;
    }

    @Override
    public Entity getDamager() {
        return this.damager;
    }

    @Override
    public Projectile getProjectile() {
        return this.projectile;
    }

    @Override
    public EntityDamageEvent.DamageCause getCause() {
        return this.cause;
    }

    @Override
    public long getSystemTime() {
        return this.systemTime;
    }

    @Override
    public void setSystemTime(final long systemTime) {
        this.systemTime = systemTime;
    }

    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public double getDamage() {
        return this.damage;
    }

    @Override
    public void setDamage(final double damage) {
        this.damage = damage;
    }

    @Override
    public double getKnockback() {
        return this.knockback;
    }

    @Override
    public void setKnockback(final double knockback) {
        this.knockback = knockback;
    }

    @Override
    public long getDelay() {
        return this.delay;
    }

    @Override
    public void setDelay(final long delay) {
        this.delay = delay;
    }

    @Override
    public SoundCreator getSoundCreator() {
        return this.soundCreator;
    }

    @Override
    public void setSoundCreator(final SoundCreator soundCreator) {
        this.soundCreator = soundCreator;
    }

    @Override
    public DamageReason getReason() {
        return this.reason;
    }

    @Override
    public void setReason(final DamageReason reason) {
        this.reason = reason;

        UtilPlugin.getInstanceByClass(Core.class).getManagerByClass(DamageManager.class).addLastReason(this.getDamagee(), this.getDamager(), this.getReason());
    }

    @Override
    public void setReason(final String name, final long duration) {
        this.setReason(new DamageReason(name, duration));
    }

    @Override
    public String getCauseString() {
        return this.causeString;
    }

    @Override
    public String getReasonString() {
        return this.reasonString;
    }

    @Override
    public String getDamageeName() {
        return this.damageeName;
    }

    @Override
    public void setDamageeName(final String damageeName) {
        this.damageeName = damageeName;
    }

    @Override
    public String getDamagerName() {
        return this.damagerName;
    }

    @Override
    public void setDamagerName(final String damagerName) {
        this.damagerName = damagerName;
    }
}