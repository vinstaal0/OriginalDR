package nl.vinstaal0.Dungeonrealms.Commands;

import minecade.dungeonrealms.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CompileTime implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You do not have");
        }

        commandSender.sendMessage("Compile date and time: " + Main.compileTimedate);

        return true;
    }
}
