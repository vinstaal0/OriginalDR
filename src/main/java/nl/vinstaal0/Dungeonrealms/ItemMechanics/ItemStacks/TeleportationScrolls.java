package nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.ChatColor.*;

public class TeleportationScrolls {

    public static ItemStack Cyrennica_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE + "" + BOLD + "Teleport:" + WHITE + " Cyrennica",
            GRAY + "Teleports the user to the grand City of Cyrennica.");

    public static ItemStack Harrison_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE + "" + BOLD + "Teleport:" + WHITE + " Harrison Field",
            GRAY + "Teleports the user to Harrison Field.");

    public static ItemStack Dark_Oak_Tavern_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE + "" + BOLD + "Teleport:" + WHITE + " Dark Oak Tavern",
            GRAY + "Teleports the user to the tavern in Dark Oak Forest.");

    public static ItemStack Deadpeaks_Mountain_Camp_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE + "" + BOLD + "Teleport:" + WHITE + " Deadpeaks Mountain Camp",
            GRAY + "Teleports the user to the Deadpeaks." + "," + ChatColor.RED + BOLD + "WARNING:" + ChatColor.RED + " CHAOTIC ZONE");

    public static ItemStack Jagged_Rocks_Tavern = ItemMechanics.createItemStack(Material.BOOK, WHITE.toString() + BOLD + "Teleport:" + WHITE + " Trollsbane Tavern",
            GRAY + "Teleports the user to Trollsbane Tavern.");

    public static ItemStack Tripoli_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE.toString() + BOLD + "Teleport:" + WHITE + " Tripoli",
            GRAY + "Teleports the user to Tripoli.");

    public static ItemStack Swamp_safezone_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE.toString() + BOLD + "Teleport:" + WHITE + " Gloomy Hollows ",
            GRAY + "Teleports the user to the Gloomy Hollows.");

    public static ItemStack Crestguard_keep_scroll = ItemMechanics.createItemStack(Material.BOOK, WHITE.toString() + BOLD + "Teleport:" + WHITE + " Crestguard Keep ",
            GRAY + "Teleports the user to the Crestguard Keep.");
}
