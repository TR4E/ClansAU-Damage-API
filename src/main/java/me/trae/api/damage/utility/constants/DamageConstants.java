package me.trae.api.damage.utility.constants;

import me.trae.api.damage.events.CustomDamageEvent;
import me.trae.core.utility.UtilColor;
import me.trae.core.utility.UtilJava;
import me.trae.core.utility.UtilString;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

public class DamageConstants {

    public static Sound getEntityHurtSound(final Entity entity) {
        switch (entity.getType()) {
            case BLAZE:
                return Sound.BLAZE_HIT;
            case OCELOT:
                return Sound.CAT_HIT;
            case CHICKEN:
                return Sound.CHICKEN_HURT;
            case COW:
                return Sound.COW_HURT;
            case SILVERFISH:
                return Sound.SILVERFISH_HIT;
            case SKELETON:
                return Sound.SKELETON_HURT;
            case SLIME:
                return Sound.SLIME_ATTACK;
            case WITHER:
                return Sound.WITHER_HURT;
            case WOLF:
                return Sound.WOLF_HURT;
            case ZOMBIE:
                return Sound.ZOMBIE_HURT;
            case PIG_ZOMBIE:
                return Sound.ZOMBIE_PIG_HURT;
            case HORSE:
                switch (UtilJava.cast(Horse.class, entity).getVariant()) {
                    case UNDEAD_HORSE:
                        return Sound.HORSE_ZOMBIE_HIT;
                    case SKELETON_HORSE:
                        return Sound.HORSE_SKELETON_HIT;
                }

                return Sound.HORSE_HIT;
            case VILLAGER:
                return Sound.VILLAGER_HIT;
        }

        return Sound.HURT_FLESH;
    }

    public static Sound getEntityDeathSound(final Entity entity) {
        switch (entity.getType()) {
            case BAT:
                return Sound.BAT_DEATH;
            case BLAZE:
                return Sound.BLAZE_DEATH;
            case CREEPER:
                return Sound.CREEPER_DEATH;
            case ENDER_DRAGON:
                return Sound.ENDERDRAGON_DEATH;
            case ENDERMAN:
                return Sound.ENDERMAN_DEATH;
            case GHAST:
                return Sound.GHAST_DEATH;
            case IRON_GOLEM:
                return Sound.IRONGOLEM_DEATH;
            case SPIDER:
                return Sound.SPIDER_DEATH;
            case WITHER:
                return Sound.WITHER_DEATH;
            case WOLF:
                return Sound.WOLF_DEATH;
            case ZOMBIE:
                return Sound.ZOMBIE_DEATH;
            case PIG_ZOMBIE:
                return Sound.ZOMBIE_PIG_DEATH;
            case HORSE:
                switch (UtilJava.cast(Horse.class, entity).getVariant()) {
                    case UNDEAD_HORSE:
                        return Sound.HORSE_ZOMBIE_DEATH;
                    case SKELETON_HORSE:
                        return Sound.HORSE_SKELETON_DEATH;
                }

                return Sound.HORSE_DEATH;
            case VILLAGER:
                return Sound.VILLAGER_DEATH;
        }

        return getEntityHurtSound(entity);
    }

    public static String createDefaultReasonString(final CustomDamageEvent event) {
        String reason = null;

        if (event.getDamager() instanceof LivingEntity) {
            final LivingEntity damager = event.getDamagerByClass(LivingEntity.class);

            final ItemStack itemStack = damager.getEquipment().getItemInHand();
            if (itemStack != null) {
                if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
                    reason = itemStack.getItemMeta().getDisplayName();
                } else {
                    reason = UtilString.clean(itemStack.getType().name());
                }
            }
        }

        if (reason != null) {
            if (UtilColor.isColorized(ChatColor.YELLOW, reason)) {
                reason = ChatColor.stripColor(reason);
            }

            reason = UtilColor.applyIfMissing(ChatColor.GREEN, reason);
        }

        return reason;
    }

    public static String createDefaultCauseString(final CustomDamageEvent event) {
        final Entity damager = event.getDamager();

        if (damager instanceof TNTPrimed) {
            return "TNT Explosion";
        }

        return UtilString.clean(event.getCause().name());
    }
}