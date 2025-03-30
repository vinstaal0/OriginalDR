package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ItemTracker {

    private Logger logger;
    FileHandler fileHandler;

    /**
     * List of items that are allowed to receive a serial ID.
    * */
    private static final Set<Material> WHITELIST = EnumSet.of(
            Material.BOOK, //Teleportation books
            Material.BLAZE_ROD, Material.BLAZE_POWDER, Material.JUKEBOX, Material.SPECKLED_MELON, Material.FIREWORK, Material.DIAMOND, //eCash items excluding the ink sack
            Material.EXP_BOTTLE, Material.BONE, Material.ROTTEN_FLESH, Material.ENCHANTED_BOOK, Material.NAME_TAG, Material.GOLDEN_CARROT, //eCash items excluding the ink sack
            Material.POTION, Material.ICE, Material.PORTAL, Material.GOLD_BLOCK, Material.SPIDER_EYE, //eCash items excluding the ink sack
            Material.BOW,
            Material.WOOD_SWORD, Material.WOOD_PICKAXE, Material.WOOD_AXE, Material.WOOD_SPADE, Material.WOOD_HOE, //T1 weapons/tools
            Material.STONE_SWORD, Material.STONE_PICKAXE, Material.STONE_AXE, Material.STONE_SPADE, Material.STONE_HOE, //T2 weapons/tools
            Material.IRON_SWORD, Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SPADE, Material.IRON_HOE, //T3 weapons/tools
            Material.DIAMOND_SWORD, Material.DIAMOND_PICKAXE, Material.DIAMOND_AXE, Material.DIAMOND_SPADE, Material.DIAMOND_HOE, //T4 weapons/tools
            Material.GOLD_SWORD, Material.GOLD_PICKAXE, Material.GOLD_AXE, Material.GOLD_SPADE, Material.GOLD_HOE, //T5 weapons/tools
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, //T1 armour
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, //T2 armour
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, //T3 amour
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, //T4 armour
            Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS //T5 armour
    );

    public ItemTracker() {
        new ItemTracker(Logger.getLogger("ItemLogger"));
    }

    public ItemTracker(Logger logger) {
        this.logger = logger;

        try {
            fileHandler = new FileHandler("plugins/dungeonrealms/itemtracker.log");
            this.logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack addSerialNumber(ItemStack item) {
        return addSerialNumber(item, null);
    }

    public static ItemStack addSerialNumber(ItemStack item, Player player) {

        int serial = (int) System.currentTimeMillis();

        ItemLogger.getLogger().logItem(item, player, serial);

        if (isWhitelisted(item)) {
            return NBTUtil.setCustomIntTag(item, "serial", serial);
        } else {
            return item;
        }
    }

    /**
     * @param item check if the item is whitelisted as an item that is whitelisted to get an ID added.
     * */
    private static boolean isWhitelisted(ItemStack item) {
        return item != null && WHITELIST.contains(item.getType());
    }

}
