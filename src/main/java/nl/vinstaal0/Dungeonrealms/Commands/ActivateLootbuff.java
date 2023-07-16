package nl.vinstaal0.Dungeonrealms.Commands;

import minecade.dungeonrealms.Hive.Hive;
import minecade.dungeonrealms.MonsterMechanics.MonsterMechanics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ActivateLootbuff implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!commandSender.isOp()) {
            commandSender.sendMessage("You don't have enough persmissions to use this command");
            return true;
        }

        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(
                ChatColor.GOLD + "" + ChatColor.BOLD + ">> " + "(" + Hive.getServerNumFromPrefix(Bukkit.getMotd()) + ") " + ChatColor.RESET + commandSender + ChatColor.GOLD
                        + " has just activated " + ChatColor.UNDERLINE + "+20% Global Drop Rates" + ChatColor.GOLD
                        + " for 30 minutes by using 'Global Loot Buff' from the E-CASH store!");
        Bukkit.getServer().broadcastMessage("");

        MonsterMechanics.loot_buff = true;

        return true;
    }
}
