package nl.vinstaal0.Dungeonrealms.PartyMechanics.Commands;

import nl.vinstaal0.Dungeonrealms.PartyMechanics.PartyMechanics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * Created by Vinstaal0 on 2023-07-05
 */
public class CommandPAccept implements CommandExecutor {
    /**
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player sender = (Player) commandSender;

        if(strings.length == 0) {
            if (PartyMechanics.invitations.containsKey(sender) && PartyMechanics.invitations.get(sender) != null) {
                PartyMechanics.invitations.get(sender).getMembers().add(sender);
                PartyMechanics.invitations.remove(sender);

                sender.sendMessage(ChatColor.GREEN + "You have joined the party!");
                // TODY add achievement for joining a party
            } else {
                sender.sendMessage(ChatColor.RED + "No pending party invites.");
            }
        }

        return true;
    }
}
