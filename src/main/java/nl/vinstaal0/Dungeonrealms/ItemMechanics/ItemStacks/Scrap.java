package nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Scrap {
    public static ItemStack T1_scrap = ItemMechanics.createItemStack(Material.LEATHER, ChatColor.WHITE + "Leather Armor Scrap",
            ChatColor.GRAY + "Recovers 3% Durability of " + ChatColor.WHITE + "Leather Equipment");
    public static ItemStack T2_scrap = ItemMechanics.createItemStack(Material.IRON_FENCE, ChatColor.GREEN + "Chainmail Armor Scrap",
            ChatColor.GRAY + "Recovers 3% Durability of " + ChatColor.GREEN + "Chainmail Equipment");
    public static ItemStack T3_scrap = ItemMechanics.createItemStack(Material.INK_SACK, ChatColor.AQUA + "Iron Armor Scrap",
            ChatColor.GRAY + "Recovers 3% Durability of " + ChatColor.AQUA + "Iron Equipment");
    public static ItemStack T4_scrap = ItemMechanics.createItemStack(Material.INK_SACK,
            ChatColor.LIGHT_PURPLE + "Diamond Armor Scrap",
            ChatColor.GRAY + "Recovers 3% Durability of " + ChatColor.LIGHT_PURPLE + "Diamond Equipment");
    public static ItemStack T5_scrap = ItemMechanics.createItemStack(Material.INK_SACK, ChatColor.YELLOW + "Golden Armor Scrap",
            ChatColor.GRAY + "Recovers 3% Durability of " + ChatColor.YELLOW + "Gold Equipment");
}
