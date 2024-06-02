package nl.vinstaal0.Dungeonrealms.Commands;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemMechanics;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemTracker;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.TeleportationScrolls;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FixMissingTextureCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        ItemStack itemStack = TeleportationScrolls.Cyrennica_scroll.clone();

        ItemStack item = ItemTracker.addSerialNumber(itemStack, player);

        player.getInventory().addItem(item);


//        ItemMechanics.itemTracker.addSerialNumber()


//        ItemStack itemInHand = player.getItemInHand();
//        int amount = itemInHand.getAmount();
//        net.minecraft.server.v1_8_R1.ItemStack nmsIS = CraftItemStack.asNMSCopy(itemInHand);
//        CraftItemStack craftItemStack = CraftItemStack.asCraftMirror(nmsIS);
//
//        craftItemStack.getItemMeta().removeItemFlags();
//
//        ItemMeta im = craftItemStack.getItemMeta();
//        Material mat = craftItemStack.getType();
//
//        ItemStack newItem = new ItemStack(mat, amount);
//        newItem.setItemMeta(im);
//        newItem.setDurability((short)0);
//
//        player.setItemInHand(newItem);

        return true;
    }
}
