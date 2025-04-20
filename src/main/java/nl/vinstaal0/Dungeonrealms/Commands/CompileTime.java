package nl.vinstaal0.Dungeonrealms.Commands;

import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.ProfessionMechanics.ProfessionMechanics;
import nl.vinstaal0.Dungeonrealms.RepairMechanics;
import nl.vinstaal0.Dungeonrealms.TierMechanics.TierMechanics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class CompileTime implements CommandExecutor {

    private static HashMap<UUID, String> savedInventory = new HashMap<UUID, String>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.isOp()) {
            if (!commandSender.getName().equalsIgnoreCase("vinstaal0")) {
                commandSender.sendMessage(ChatColor.RED + "You do not have the permission to use this command");
                return true;
            }
        }

        commandSender.sendMessage("Compile date and time: " + Main.compileTimedate);

        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        UUID uuid = player.getUniqueId();

        if (strings.length == 1) {

            if (strings[0].equalsIgnoreCase("level")) {

                ItemStack tool = player.getItemInHand();

                ProfessionMechanics.addEXP(player, tool, 900, "pickaxe");

            } else if (strings[0].equalsIgnoreCase("repair")) {

                Random r = new Random();
                int newDura = r.nextInt(1490-10) + 10;

                ItemStack returnStack = RepairMechanics.updateCustomDurability(player.getItemInHand(), newDura, RepairMechanics.getMaxDurability(TierMechanics.getTier(player.getItemInHand())));

//                player.getInventory().addItem(returnStack);

                player.sendMessage("Current durability: " + RepairMechanics.getCurrentDurability(player.getItemInHand()));

            }


        }

        return true;
    }
}
