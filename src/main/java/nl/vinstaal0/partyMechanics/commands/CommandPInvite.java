package nl.vinstaal0.partyMechanics.commands;

import minecade.dungeonrealms.CommunityMechanics.CommunityMechanics;
import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.ModerationMechanics.ModerationMechanics;
import minecade.dungeonrealms.managers.PlayerManager;
import nl.vinstaal0.partyMechanics.PartyMechanics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * Created by Vinstaal0 on 2023-07-05
 */
public class CommandPInvite implements CommandExecutor {

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

        if(strings.length != 1) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Syntax. " + ChatColor.RED + "/pinvite <player>");
            sender.sendMessage(ChatColor.GRAY + "You can also " + ChatColor.UNDERLINE + "LEFT CLICK" + ChatColor.GRAY + " players with your " + ChatColor.ITALIC + "Character Journal" + ChatColor.GRAY + " to invite them.");
            return true;
        }

        String playerName = strings[0];
        Player receiver = Bukkit.getPlayer(playerName);

        if (Bukkit.getPlayer(strings[0]) != null && !Bukkit.getPlayer(strings[0]).equals(sender)) {
            if (Main.getPartyMechanics().isInParty(receiver.getName())) {
                sender.sendMessage(ChatColor.RED + "That player is already in a party");
                return true;
            }

            if (Main.getPartyMechanics().isInParty(sender.getName())) {
                if (Main.getPartyMechanics().getParty(sender).get().getMembers().size() >= 7) {
                    sender.sendMessage(ChatColor.RED + "Your party has reached the max player count!");
                    return true;
                }

                if (PlayerManager.getPlayerModel(playerName).getToggleList() != null && PlayerManager.getPlayerModel(playerName).getToggleList().contains("party")) {
                    if (!CommunityMechanics.isPlayerOnBuddyList(playerName, sender.getName())) {
                        // They're not buddies and this player doesn't want non-bud invites.
                        sender.sendMessage(ChatColor.RED.toString() + sender + " has Non-BUD party invites " + ChatColor.BOLD + "DISABLED");
                        return true;
                    }
                }

                if (CommunityMechanics.isPlayerOnIgnoreList(receiver, sender.getName()) || ModerationMechanics.isPlayerVanished(playerName)) {
                    sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + playerName + ChatColor.RED + " is OFFLINE.");
                    return true;
                }

                if (receiver != null) {
                    Main.getPartyMechanics().invitePlayer(sender, receiver);
                    sender.sendMessage(ChatColor.GREEN + "Invited " + ChatColor.AQUA + receiver.getName() + " " + ChatColor.GREEN + " to your party!");
                } else {
                    sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + playerName + ChatColor.RED + " is OFFLINE");
                }
            } else {
                // create party
                if (receiver != null) {
                    if (!Main.getPartyMechanics().isInParty(receiver)) {
                        Main.getPartyMechanics().createParty(sender);
                        Main.getPartyMechanics().invitePlayer(sender, receiver);
                    } else {
                        sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + strings[0] + ChatColor.RED + " is already in your party.");
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Invalid Syntax." + ChatColor.RED + " /pinvite <player>");
            sender.sendMessage(ChatColor.GRAY + "You can also " + ChatColor.UNDERLINE + "LEFT CLICK" + ChatColor.GRAY + " players with your " + ChatColor.ITALIC + "Character Journal" + ChatColor.GRAY + " to invite them.");
        }
        return false;
    }
}
