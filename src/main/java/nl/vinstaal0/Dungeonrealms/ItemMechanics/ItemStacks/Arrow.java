package nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Arrow {
    public static ItemStack t1_arrow = ItemMechanics.createItemStack(Material.ARROW,  ChatColor.WHITE + "Bent Arrow",
            ChatColor.GRAY + "Increase arrow damage by 5%");
    public static ItemStack t2_arrow = ItemMechanics.createItemStack(Material.ARROW,  ChatColor.GREEN + "Precise Arrow",
            ChatColor.GRAY + "Increase arrow damage by 10%");
    public static ItemStack t3_arrow = ItemMechanics.createItemStack(Material.ARROW,  ChatColor.AQUA + "Reinforced Arrow",
            ChatColor.GRAY + "Increase arrow damage by 20%");
    public static ItemStack t4_arrow = ItemMechanics.createItemStack(Material.ARROW,  ChatColor.LIGHT_PURPLE + "Pointed Arrow",
            ChatColor.GRAY + "Increase arrow damage by 40%");
    public static ItemStack t5_arrow = ItemMechanics.createItemStack(Material.ARROW, ChatColor.YELLOW + "Piercing Arrow",
            ChatColor.GRAY + "Increase arrow damage by 80%");
    public static ItemStack t1_quiver = ItemMechanics.createItemStack(Material.FLOWER_POT_ITEM, ChatColor.GOLD + "Quiver " + ChatColor.WHITE + "0 / 200", Arrays
            .asList(ChatColor.GRAY + "A sturdy quiver to hold arrows.", ChatColor.WHITE + "Tier 1: " + ChatColor.WHITE + ChatColor.BOLD + "0", ChatColor.GREEN
                    + "Tier 2: " + ChatColor.WHITE + ChatColor.BOLD + "0", ChatColor.AQUA + "Tier 3: " + ChatColor.WHITE + ChatColor.BOLD + "0",
                    ChatColor.LIGHT_PURPLE + "Tier 4: " + ChatColor.WHITE + ChatColor.BOLD + "0", ChatColor.YELLOW + "Tier 5: " + ChatColor.WHITE
                            + ChatColor.BOLD + "0"));
}
