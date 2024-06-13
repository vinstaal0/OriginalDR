package nl.vinstaal0.Dungeonrealms.Commands;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemTracker;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks.TeleportationScrolls;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FixMissingTextureCommand implements CommandExecutor {

    @Deprecated
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // TODO return comment to the actual command

        Player player = (Player) commandSender;

        ItemStack itemStack = TeleportationScrolls.Cyrennica_scroll.clone(); // .clone doesn't seem to make a difference

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
//        player.setItemInHand(ItemTracker.addSerialNumber(newItem));

        return true;
    }
}
