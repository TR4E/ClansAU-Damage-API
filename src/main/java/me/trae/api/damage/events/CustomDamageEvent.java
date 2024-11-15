package me.trae.api.damage.events;

import com.mysql.cj.protocol.x.XProtocol;
import me.trae.api.damage.DamageManager;
import me.trae.api.damage.data.DamageReason;
import me.trae.api.damage.events.interfaces.ICustomDamageEvent;
import me.trae.api.damage.utility.constants.DamageConstants;
import me.trae.core.Core;
import me.trae.core.event.CustomCancellableEvent;
import me.trae.core.player.events.PlayerDisplayNameEvent;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilPlugin;
import me.trae.core.utility.UtilServer;
import me.trae.core.utility.objects.SoundCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class CustomDamageEvent extends CustomCancellableEvent implements ICustomDamageEvent {

    private final long systemTime;

    private final Entity damagee, damager;
    private final Projectile projectile;
    private final EntityDamageEvent.DamageCause cause;
    private final String causeString, reasonString;

    private ItemStack itemStack;
    private double damage, knockback;
    private SoundCreator soundCreator;
    private DamageReason reason;

    private String damageeName, damagerName;

    public CustomDamageEvent(final Entity damagee, final Entity damager, final Projectile projectile, final EntityDamageEvent.DamageCause cause, final double damage) {
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

    public CustomDamageEvent(final Entity damagee, final Entity damager, final EntityDamageEvent.DamageCause cause, final double damage) {
        this(damagee, damager, null, cause, damage);
    }

    public CustomDamageEvent(final Entity damagee, final EntityDamageEvent.DamageCause cause, final double damage) {
        this(damagee, null, null, cause, damage);
    }

    public CustomDamageEvent(final EntityDamageEvent event) {
        this(event.getEntity(), null, null, event.getCause(), event.getDamage());
    }

    public CustomDamageEvent(final EntityDamageByEntityEvent event) {
        this(event.getEntity(), event.getDamager(), null, event.getCause(), event.getDamage());
    }

    public CustomDamageEvent(final EntityDamageByEntityEvent event, final Projectile projectile) {
        this(event.getEntity(), UtilJava.cast(Entity.class, projectile.getShooter()), projectile, EntityDamageEvent.DamageCause.PROJECTILE, event.getDamage());
    }

    @Override
    public long getSystemTime() {
        return this.systemTime;
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
    public void setReason(final String name, final long duration) {
        this.reason = new DamageReason(name, duration);

        UtilPlugin.getInstance(Core.class).getManagerByClass(DamageManager.class).addLastReason(this.getDamagee(), this.getDamager(), this.getReason());
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