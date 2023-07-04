package nl.vinstaal0.commands;

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

        ItemStack itemInHand = player.getItemInHand();
        int amount = itemInHand.getAmount();
        net.minecraft.server.v1_8_R1.ItemStack nmsIS = CraftItemStack.asNMSCopy(itemInHand);
        CraftItemStack craftItemStack = CraftItemStack.asCraftMirror(nmsIS);

        craftItemStack.getItemMeta().removeItemFlags();

        ItemMeta im = craftItemStack.getItemMeta();
        Material mat = craftItemStack.getType();

        ItemStack newItem = new ItemStack(mat, amount);
        newItem.setItemMeta(im);
        newItem.setDurability((short)0);

        player.setItemInHand(newItem);

        return true;
    }
}
