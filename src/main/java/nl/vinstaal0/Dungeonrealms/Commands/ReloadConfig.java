package nl.vinstaal0.Dungeonrealms.Commands;

import nl.vinstaal0.Dungeonrealms.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.isOp()) {
            if (!commandSender.getName().equalsIgnoreCase("vinstaal0")) {
                commandSender.sendMessage(ChatColor.RED + "You do not have the permission to use this command");
                return true;
            }
        }

        ConfigFile.getInstance().reloadConfig();

        return true;
    }
}
