package nl.vinstaal0.Dungeonrealms.PartyMechanics.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandPKick implements CommandExecutor {
    /**
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        System.out.println("Trigger");

        return false;
    }
}
