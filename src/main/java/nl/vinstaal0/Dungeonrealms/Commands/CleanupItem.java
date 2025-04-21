package nl.vinstaal0.Dungeonrealms.Commands;

import nl.vinstaal0.Dungeonrealms.Repair.RepairableItems;
import nl.vinstaal0.Dungeonrealms.RepairMechanics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CleanupItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player;

        if (!(commandSender instanceof Player)) {
            return true;
        }

        player = (Player) commandSender;

        ItemStack item = player.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();

        // The item has durability so it cannot be cleanedup with this command
        if (RepairableItems.canHaveDurability(item)) {
            return true;
        }

        List<String> newLore = new ArrayList<>();

        for (String string : itemMeta.getLore()) {
            if (!string.startsWith(RepairMechanics.DURABILITY_LORE)) {
                newLore.add(string);
            }
        }

        itemMeta.setLore(newLore);
        item.setItemMeta(itemMeta);

        player.updateInventory();

        return true;
    }
}
