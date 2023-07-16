package nl.vinstaal0.Dungeonrealms.PartyMechanics.Commands;

import nl.vinstaal0.Dungeonrealms.PartyMechanics.Party;
import nl.vinstaal0.Dungeonrealms.PartyMechanics.PartyMechanics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPDecline implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if(args.length != 0) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Syntax. " + ChatColor.RED + "/pdecline");
            return true;
        }

        if (!PartyMechanics.invitations.containsKey(player)) {
            player.sendMessage(ChatColor.RED + "No pending party invites.");
            return true;
        }

        Party party = PartyMechanics.invitations.get(player);
        PartyMechanics.invitations.remove(player);
        player.sendMessage(ChatColor.RED + "Declined " + ChatColor.BOLD + party.getOwner().getName() + "'s" + ChatColor.RED + " party invitation.");

        party.getOwner().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + player.getName() + ChatColor.RED.toString() + " has " + ChatColor.UNDERLINE + "DECLINED" + ChatColor.RED + " your party invitation.");

        return true;
    }
}
