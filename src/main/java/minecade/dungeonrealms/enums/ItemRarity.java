package minecade.dungeonrealms.enums;

import nl.vinstaal0.Dungeonrealms.ConfigFile;
import nl.vinstaal0.Dungeonrealms.ConfigKey;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum ItemRarity {

    COMMON(ConfigKey.COMMON_DROPRATE),
    UNCOMMON(ConfigKey.UNCOMMON_DROPRATE),
    RARE(ConfigKey.RARE_DROPRATE),
    UNIQUE(ConfigKey.UNIQUE_DROPRATE);

    private final ConfigKey dropRateKey;

    ItemRarity(ConfigKey dropRateKey) {
        this.dropRateKey = dropRateKey;
    }

    public double getDropRate() {
        return ConfigFile.getInstance().getConfigValue(dropRateKey);
    }

    public static ItemRarity getRarityFromItem(ItemStack is) {
        if (!is.hasItemMeta() || !is.getItemMeta().hasLore()) return null;
        for (String line : is.getItemMeta().getLore()) {
            for (ItemRarity rarity : values()) {
                if (line.toLowerCase().contains(rarity.name().toLowerCase())) {
                    return valueOf(ChatColor.stripColor(line).toUpperCase());
                }
            }
        }
        return null;
    }

    public static ItemRarity getRarityFromRoll(double roll) {
        double cumulativeSum = 0.0;

        // Normalize and find the rarity based on the roll
        for (ItemRarity rarity : values()) {
            cumulativeSum += rarity.getDropRate();
            if (roll < cumulativeSum) {
                return rarity;
            }
        }
        return COMMON;  // Default fallback
    }
	
}
