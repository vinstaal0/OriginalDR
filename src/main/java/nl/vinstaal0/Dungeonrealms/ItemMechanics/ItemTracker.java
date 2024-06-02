package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ItemTracker {

    private Logger logger;
    FileHandler fileHandler;

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

        return NBTUtil.setCustomIntTag(item, "serial", serial);
    }

}
