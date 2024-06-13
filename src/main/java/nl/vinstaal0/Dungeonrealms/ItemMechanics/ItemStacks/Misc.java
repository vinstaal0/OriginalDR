package nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Misc {

    public static ItemStack orb_of_peace = ItemMechanics.createItemStack(Material.ENDER_PEARL, ChatColor.GREEN + "Orb of Peace",
            ChatColor.GRAY + "Set realm to " + ChatColor.UNDERLINE + "SAFE ZONE" + ChatColor.GRAY + " for 1 hour(s).");

    public static ItemStack orb_of_flight = ItemMechanics.createItemStack(Material.FIREWORK_CHARGE , ChatColor.AQUA + "Orb of Flight", ChatColor.GRAY + "Enables " + ChatColor.UNDERLINE + "FLYING" + ChatColor.GRAY + " in realm for the owner " + ","
            + ChatColor.GRAY + "and all builders for 30 minute(s)." + "," + ChatColor.RED + ChatColor.BOLD + "REQ:"
            + ChatColor.RED + " Active Orb of Peace");
    public static ItemStack easter_egg = ItemMechanics.createItemStack(Material.EGG, ChatColor.LIGHT_PURPLE + "Easter Egg",
            ChatColor.GRAY + "Gives you a random item!" + "," + ChatColor.LIGHT_PURPLE + "Rare");
    // Realm Shop Items {
    public static ItemStack divider = ItemMechanics.createItemStack(Material.THIN_GLASS, " ", "");
    public static ItemStack next_page_1 = ItemMechanics.createItemStack(Material.ARROW,
            ChatColor.YELLOW + "Next Page " + ChatColor.BOLD + "->", ChatColor.GRAY + "Page 2/3");
    public static ItemStack next_page_2 = ItemMechanics.createItemStack(Material.ARROW,
            ChatColor.YELLOW + "Next Page " + ChatColor.BOLD + "->", ChatColor.GRAY + "Page 3/3");
    public static ItemStack previous_page_2 = ItemMechanics.createItemStack(Material.ARROW, ChatColor.YELLOW.toString() + ChatColor.BOLD + "<-"
            + ChatColor.YELLOW + " Previous Page ", ChatColor.GRAY + "Page 1/3");
    public static ItemStack previous_page_3 = ItemMechanics.createItemStack(Material.ARROW, ChatColor.YELLOW.toString() + ChatColor.BOLD + "<-"
            + ChatColor.YELLOW + " Previous Page ", ChatColor.GRAY + "Page 2/3");
}
