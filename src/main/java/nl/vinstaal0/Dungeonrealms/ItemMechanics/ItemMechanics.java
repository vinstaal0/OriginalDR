package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemMechanics extends minecade.dungeonrealms.ItemMechanics.ItemMechanics {

    public static ItemStack createItemStack(Material material, String displayname, String... lore) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayname);

        List<String> loreArray = new ArrayList<>(Arrays.asList(lore));

        itemMeta.setLore(loreArray);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createItemStack(Material material, String displayname, List<String> lore) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayname);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
