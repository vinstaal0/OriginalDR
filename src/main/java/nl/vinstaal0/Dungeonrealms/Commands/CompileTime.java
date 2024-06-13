package nl.vinstaal0.Dungeonrealms.Commands;

import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.MoneyMechanics.MoneyMechanics;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.InventoryType;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemSerialization;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
import java.util.*;


public class CompileTime implements CommandExecutor {

    private static HashMap<UUID, String> savedInventory = new HashMap<UUID, String>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You do not have");
        }

        commandSender.sendMessage("Compile date and time: " + Main.compileTimedate);

        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        UUID uuid = player.getUniqueId();

        if (strings.length == 1) {

            if (strings[0].equalsIgnoreCase("save")) {

                String inventory = null;

                List<Inventory> inventoryList = MoneyMechanics.bank_contents.get(player.getDisplayName());

                ArrayList<Inventory> inventoryArrayList = new ArrayList<>(inventoryList);

                try {
                    inventory = ItemSerialization.serializeBankInventory(inventoryArrayList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

//                try {
//                    inventory = ItemSerialization.serializePlayerInventory(player.getInventory());
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

                savedInventory.put(uuid, inventory);

            } else if (strings[0].equalsIgnoreCase("load")) {

                ArrayList<Inventory> inventoryArrayList = new ArrayList<>();

                try {
                    inventoryArrayList = ItemSerialization.deserializeInventory(savedInventory.get(uuid), player, InventoryType.BANK, MoneyMechanics.getBankSlots(MoneyMechanics.bank_level.get(player.getDisplayName())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (inventoryArrayList != null) {
                    player.openInventory(inventoryArrayList.get(0));
                }


//                String inventoryString = savedInventory.get(uuid);
//
//                try {
//                    ItemSerialization.deserializeInventory(inventoryString, player, InventoryType.PLAYER_INVENTORY, 0);
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

            }

        }

        return true;
    }
}
