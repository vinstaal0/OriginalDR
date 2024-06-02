package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import minecade.dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeleportationScrolls {

    //Teleportation scrolls
//    public static ItemStack Cyrennica_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 1, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Cyrennica",
//            ChatColor.GRAY.toString() + "Teleports the user to the grand City of Cyrennica.");


    public static ItemStack Cyrennica_scroll = nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics.createItemStack(Material.BOOK, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Cyrennica",
            ChatColor.GRAY.toString() + "Teleports the user to the grand City of Cyrennica.");

    public static ItemStack Harrison_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 2, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Harrison Field",
            ChatColor.GRAY.toString() + "Teleports the user to Harrison Field.");

    public static ItemStack Dark_Oak_Tavern_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 3, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Dark Oak Tavern",
            ChatColor.GRAY.toString() + "Teleports the user to the tavern in Dark Oak Forest.");

    public static ItemStack Deadpeaks_Mountain_Camp_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 4, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Deadpeaks Mountain Camp",
            ChatColor.GRAY.toString() + "Teleports the user to the Deadpeaks." + "," + ChatColor.RED.toString() + "" + ChatColor.BOLD.toString() + "WARNING:" + ChatColor.RED.toString() + " CHAOTIC ZONE");

    public static ItemStack Jagged_Rocks_Tavern = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 5, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Trollsbane Tavern",
            ChatColor.GRAY.toString() + "Teleports the user to Trollsbane Tavern.");

    public static ItemStack Tripoli_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 6, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Tripoli",
            ChatColor.GRAY.toString() + "Teleports the user to Tripoli.");

    public static ItemStack Swamp_safezone_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 7, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Gloomy Hollows ",
            ChatColor.GRAY.toString() + "Teleports the user to the Gloomy Hollows.");

    public static ItemStack Crestguard_keep_scroll = ItemMechanics.signNewCustomItem(Material.BOOK, (short) 7, ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Teleport:" + ChatColor.WHITE.toString() + " Crestguard Keep ",
            ChatColor.GRAY.toString() + "Teleports the user to the Crestguard Keep.");
}
