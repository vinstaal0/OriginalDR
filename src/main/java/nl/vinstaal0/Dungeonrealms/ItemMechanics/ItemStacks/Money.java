package nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Money {
    public static ItemStack t1_gem_pouch = ItemMechanics.createItemStack(Material.INK_SACK, ChatColor.WHITE.toString() + "Small Gem Pouch" + ChatColor.GREEN + ChatColor.BOLD.toString() + " 0g", ChatColor.GRAY.toString() + "A small linen pouch that holds " + ChatColor.BOLD + "100g");
    public static ItemStack t2_gem_pouch = ItemMechanics.createItemStack(Material.INK_SACK, ChatColor.GREEN.toString() + "Medium Gem Sack" + ChatColor.GREEN + ChatColor.BOLD.toString() + " 0g", ChatColor.GRAY.toString() + "A medium wool sack that holds " + ChatColor.BOLD + "150g");
    public static ItemStack t3_gem_pouch = ItemMechanics.createItemStack(Material.INK_SACK, ChatColor.AQUA.toString() + "Large Gem Satchel" + ChatColor.GREEN + ChatColor.BOLD.toString() + " 0g", ChatColor.GRAY.toString() + "A large leather satchel that holds " + ChatColor.BOLD + "200g");
    public static ItemStack t4_gem_pouch = ItemMechanics.createItemStack(Material.INK_SACK, ChatColor.LIGHT_PURPLE.toString() + "Gigantic Gem Container" + ChatColor.GREEN + ChatColor.BOLD.toString() + " 0g", ChatColor.GRAY.toString() + "A giant container that holds " + ChatColor.BOLD + "300g");

    public static ItemStack signBankNote(ItemStack i, String name, String desc) {
        List<String> new_lore = new ArrayList<>();

        for(String s : desc.split(",")) {
            if(s.length() <= 1) {
                continue;
            }
            new_lore.add(s);
        }

        ItemMeta im = i.getItemMeta();
        im.setLore(new_lore);
        im.setDisplayName(name);

        i.setItemMeta(im);

        return i;
    }

    public static ItemStack makeGems(int amount) {
        ItemStack i = new ItemStack(Material.EMERALD, amount);
        List<String> new_lore = new ArrayList<>(Arrays.asList(ChatColor.GRAY + "The currency of Andalucia"));

        ItemMeta im = i.getItemMeta();
        im.setLore(new_lore);

        im.setDisplayName(ChatColor.WHITE + "Gem");
        i.setItemMeta(im);
        i.setAmount(amount);

        return i;
    }
}
