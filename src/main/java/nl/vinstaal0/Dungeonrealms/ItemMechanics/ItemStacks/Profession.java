package nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Profession {

    public static ItemStack pickaxe_scroll = ItemMechanics.createItemStack(Material.EMPTY_MAP,
            ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Scroll:" + ChatColor.YELLOW.toString() + " Pickaxe Enchant",
            ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Imbues a pickaxe with special attribute(s).");

    public static ItemStack fishing_scroll = ItemMechanics.createItemStack(Material.EMPTY_MAP,
            ChatColor.WHITE.toString() + "" + ChatColor.BOLD.toString() + "Scroll:" + ChatColor.YELLOW.toString() + " Fishingrod Enchant",
            ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Imbues a fishingrod with special attribute(s).");

}
