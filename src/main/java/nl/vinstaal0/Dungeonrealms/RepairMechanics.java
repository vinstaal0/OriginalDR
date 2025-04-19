package nl.vinstaal0.Dungeonrealms;

import minecade.dungeonrealms.ItemMechanics.ItemMechanics;
import minecade.dungeonrealms.ProfessionMechanics.ProfessionMechanics;
import nl.vinstaal0.Dungeonrealms.TierMechanics.Tier;
import nl.vinstaal0.Dungeonrealms.TierMechanics.TierMechanics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RepairMechanics extends minecade.dungeonrealms.RepairMechanics.RepairMechanics {

    private static final String DURABILITY_LORE = ChatColor.GRAY + "Durability: ";

    /**
     * @param item the itemstack that needs to have custom currability
     * @param currentDurability the current durability out of the maxDurability ex. 1000 / 1500
     * @param maxDurability the max durability of the item
     *
     * */
    public static ItemStack addCustomDurability(ItemStack item, int currentDurability, int maxDurability) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

        // remove any existing durability rules
        lore.removeIf(line -> line.startsWith(DURABILITY_LORE));

        // add a durability rule
        lore.add(DURABILITY_LORE + currentDurability + " / " + maxDurability);



        double percent = (float) currentDurability / maxDurability;
        short maxVanillaDurability = item.getType().getMaxDurability();
        short vanillaDurability = (short) Math.round((1 - percent) * maxVanillaDurability);

        item.setDurability(vanillaDurability);

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static int getCurrentDurability(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return -1;
        }

        for (String line : meta.getLore()) {
            if (line.startsWith(DURABILITY_LORE)) {
                String[] parts = line.replace(DURABILITY_LORE, "").split(" / ");
                if (parts.length == 2) {
                    try {
                        return Integer.parseInt(parts[0]);
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                }
            }
        }

        // No custom durability found
        double currentDurability = minecade.dungeonrealms.RepairMechanics.RepairMechanics.getCustomDurability(item, "wep");
        int maxDurability = 1500; // Default legacy value

        addCustomDurability(item, (int) currentDurability, maxDurability);

        return getCurrentDurability(item);
    }

    public static int getMaxDurability(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return -1;
        }

        for (String line : meta.getLore()) {
            if (line.startsWith(DURABILITY_LORE)) {
                String[] parts = line.replace(DURABILITY_LORE, "").split(" / ");
                if (parts.length == 2) {
                    try {
                        return Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                }
            }
        }
        return -1;
    }

    public static ItemStack updateDurability(ItemStack item, int newDurability) {
        int maxDurability = getMaxDurability(item);
        if (maxDurability == -1) {
            return item;
        }

        return addCustomDurability(item, newDurability, maxDurability);
    }

    // TODO Replace all methods from the original repairmechanics class with my own one before I can even properly test it. Check the compiltetime command
    /*
    *
    * Used to update from legacy durability to the new durability
    *
    * */
    @Deprecated
    public static void subtractCustomDurability(final Player player, final ItemStack item, double amount_to_subtract, String item_type, boolean ignoreSafe) {

        if(!ItemMechanics.isArmor(item) && ItemMechanics.getDamageData(item).equalsIgnoreCase("no") && !ProfessionMechanics.isSkillItem(item)) {
            return; // Not a weapon or armour or a profession tool so no durability reduction needed.
        }

        double currentDurability = minecade.dungeonrealms.RepairMechanics.RepairMechanics.getCustomDurability(item, item_type);

        double percent = getPercentForDurabilityValue(item, item_type);

        Tier tier = TierMechanics.getTier(item);
        double maxDurability = 1500.0; // Old max value, use as default

        switch (tier) { //Check what the max durability needs to be
            case ONE : maxDurability = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER1_DURABILITY);
            case TWO : maxDurability = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER2_DURABILITY);
            case TREE : maxDurability = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER3_DURABILITY);
            case FOUR : maxDurability = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER4_DURABILITY);
            case FIVE : maxDurability = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER5_DURABILITY);
        }

        double updatedCurrentDurability = Math.round(percent * maxDurability);

        updateDurability(item, (int) updatedCurrentDurability);

    }

    public static double getDurabilityPercent(ItemStack item) {
        int currentDurability = getCurrentDurability(item);
        int maxDurability = getMaxDurability(item);

        return (double) currentDurability / maxDurability;
    }

    /*@EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        int currentDurability = getCurrentDurability(item);
        int maxDurability = getMaxDurability(item);

        if (currentDurability == -1 || maxDurability == -1) {
            return; // No custom durability found
        }

        // Cancel the base event
        event.setCancelled(true);

        // Update the custom durability
        int newDurability = currentDurability - 1;
        if (newDurability <= 0) {
            // Item breaks
            event.getPlayer().getInventory().setItemInHand(null);
        } else {
            // Update the durability
            event.getPlayer().getInventory().setItemInHand(updateDurability(item, newDurability));
        }
    }*/

}
