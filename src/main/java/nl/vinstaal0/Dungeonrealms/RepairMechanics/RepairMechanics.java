package nl.vinstaal0.Dungeonrealms.RepairMechanics;

import minecade.dungeonrealms.Main;
import nl.vinstaal0.Dungeonrealms.Commands.CleanupItem;
import minecade.dungeonrealms.DuelMechanics.DuelMechanics;
import minecade.dungeonrealms.ItemMechanics.ItemMechanics;
import minecade.dungeonrealms.ProfessionMechanics.ProfessionMechanics;
import nl.vinstaal0.Dungeonrealms.ConfigFile;
import nl.vinstaal0.Dungeonrealms.ConfigKey;
import nl.vinstaal0.Dungeonrealms.TierMechanics.Tier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class RepairMechanics extends minecade.dungeonrealms.RepairMechanics.RepairMechanics implements Listener {

    private static final Logger log = Logger.getLogger("Minecraft");
    public static final String DURABILITY_LORE = ChatColor.GRAY + "Durability: ";
    private static HashMap<String, Long> warned_durability = new HashMap<String, Long>();

    @Override
    public void onEnable() {
        super.onEnable();

        Main.getPlugin().getCommand("cleanupitem").setExecutor(new CleanupItem());
    }


    /**
     * @param item the itemstack that needs to have custom currability
     * @param currentDurability the current durability out of the maxDurability ex. 1000 / 1500
     * @param maxDurability the max durability of the item
     *
     * */
    public static ItemStack updateCustomDurability(ItemStack item, int currentDurability, int maxDurability) {

        // CHeck if the item uses durability for something else
        Material material = item.getType();
        switch (material) {
            case INK_SACK:
            case WOOL:
            case CARPET:
            case BANNER:
//            case CONCRETE:
//            case CONCRETE_POWDER:
            case STAINED_GLASS:
            case STAINED_GLASS_PANE:
//            case TERRACOTTA:
//            case SHULKER_BOX
                return item;
            default:
                ItemMeta meta = item.getItemMeta();

                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

                // remove any existing durability rules
                lore.removeIf(line -> line.startsWith(DURABILITY_LORE));

                if (currentDurability > maxDurability) {
                    currentDurability = maxDurability;
                }

                // add a durability rule
                lore.add(DURABILITY_LORE + currentDurability + " / " + maxDurability);

                // Calculate the percent of durability the item still has
                double percent = (float) currentDurability / maxDurability;
                short maxVanillaDurability = item.getType().getMaxDurability();
                short vanillaDurability = (short) Math.round((1 - percent) * maxVanillaDurability);

                // Set the vanilla durability
                item.setDurability(vanillaDurability);

                meta.setLore(lore);
                item.setItemMeta(meta);

                return item;
        }

    }

    public static ItemStack subtractCustomDurability(Player player, ItemStack item, int durability) {
        int current = getCurrentDurability(item);
        int max = getMaxDurability(item);

        // IF armor degrade durability 3 times as quickly
        if (ItemMechanics.isArmor(item)) {
            durability *= 3;
        }

        if(DuelMechanics.isDamageDisabled(player.getLocation()) && (!(ProfessionMechanics.isSkillItem(item)))) {
            durability = 0;
        }

        int new_dur = current - durability;
        int percent_remaining = new_dur / max;

        // TODO add logic for item breaking

        if((!warned_durability.containsKey(item)) || ((System.currentTimeMillis() - warned_durability.get(item)) > 60 * 1000)) {

            if(percent_remaining <= 10 && percent_remaining >= 9) {
                // 10%
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5F, 1F);
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "      *10% DURABILITY " + ChatColor.RED + "LEFT ON " + item.getItemMeta().getDisplayName() + ChatColor.RED.toString() + "*");
                warned_durability.put(item.getItemMeta().getDisplayName(), System.currentTimeMillis());
            }

            if(percent_remaining <= 3 && percent_remaining >= 2) {
                // 2%
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5F, 1F);
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "      *2% DURABILITY " + ChatColor.RED + "LEFT ON " + item.getItemMeta().getDisplayName() + ChatColor.RED.toString() + "*");
                warned_durability.put(item.getItemMeta().getDisplayName(), System.currentTimeMillis());
            }


        }

        return updateCustomDurability(item, new_dur, max);
    }

    public static ItemStack addCustomDurability(Player player, ItemStack item, int durability) {
        int current = getCurrentDurability(item);
        int max = getMaxDurability(item);

        if (current > max) {
            current = max;
        }

        return updateCustomDurability(item, current + durability, max);
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

        if (!hasCustomDurability(item)) {
            // No custom durability found
            double currentDurability = getLegacyCustomDurability(item);
            int maxDurability = 1500; // Default legacy value

            updateCustomDurability(item, (int) currentDurability, maxDurability);

            return getCurrentDurability(item);
        }

        return -1;
    }

    @Deprecated
    public static double getLegacyCustomDurability(ItemStack item) {

        String item_type = "wep";

        try {

            Repairable rp = (Repairable) item.getItemMeta();
            double durability = rp.getRepairCost();

            if(durability > 0) { return durability; }
            if(durability < 0) { return 0; }
            // If durability is 0 or bellow, we need to calculate what it should be.
            double dur_percent = 0;
            dur_percent = getPercentForDurabilityValue(item, item_type);
            setCustomDurability(item, (double) (dur_percent * 15), item_type, true); // dur_percent = 5%, X 10 = 50 hits.

            dur_percent = dur_percent * 15;

            return (double) dur_percent;

        } catch(Exception e) {
            e.printStackTrace();
            log.info("[RepairMechanics] Item durability not registered, getting % value and registering. " + item.toString());

            double dur_percent = getPercentForDurabilityValue(item, item_type);
            setCustomDurability(item, (double) (dur_percent * 15), item_type, true); // dur_percent = 5%, X 10 = 50 hits.
            dur_percent = dur_percent * 15;

            return dur_percent;
        }
    }

    public static boolean hasCustomDurability(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return false;
        }

        List<String> lore = meta.getLore();
        for (String line : lore) {
            if (line.startsWith(DURABILITY_LORE)) {
                return true;
            }
        }
        return false;
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

    public static int getMaxDurability(Tier tier) {
        switch (tier) {
            case ONE :
                double t1 = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER1_DURABILITY);
                return (int) t1;
            case TWO :
                double t2 = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER2_DURABILITY);
                return (int) t2;
            case THREE :
                double t3 = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER3_DURABILITY);
                return (int) t3;
            case FOUR :
                double t4 = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER4_DURABILITY);
                return (int) t4;
            case FIVE :
                double t5 = ConfigFile.getInstance().getConfigValue(ConfigKey.TIER5_DURABILITY);
                return (int) t5;

            default: return 1500;
        }
    }

    /**
     * @param item the item for which the durability percent is to be checked
     * @return returns a percentage of the durability of the item ex 0,05. old version showed 5% as 5.
     * */
    public static double getDurabilityPercent(ItemStack item) {
        int currentDurability = getCurrentDurability(item);
        int maxDurability = getMaxDurability(item);

        return (double) currentDurability / maxDurability;
    }

}
