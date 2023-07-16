package nl.vinstaal0.Dungeonrealms.PartyMechanics.Commands;

import minecade.dungeonrealms.Main;
import nl.vinstaal0.Dungeonrealms.PartyMechanics.PartyMechanics;
import nl.vinstaal0.Dungeonrealms.PartyMechanics.Party;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.logging.Logger;

public class CommandPChat implements CommandExecutor {

    static Logger log = Logger.getLogger("Minecraft");

    /**
     * @param commandSender
     * @param command
     * @param s
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;
        PartyMechanics partyMechanics = Main.getPartyMechanics();

        Optional<Party> optionalParty = partyMechanics.getParty(player);

        Party party = null;

        if (!partyMechanics.isInParty(player)) {
            player.sendMessage(ChatColor.RED + "You are not in a party.");
        }

        if (optionalParty.isPresent()) {
            party = optionalParty.get();
        }

        if (party == null) {
            return true;
        }

        if (args.length == 0) {
            if(!(PartyMechanics.partyOnlyChat.contains(player))) {
                // Doesn't have party only chat enabled
                PartyMechanics.partyOnlyChat.add(player);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Messages will now be default sent to <P>. Type " + ChatColor.UNDERLINE + "/l <msg>" + ChatColor.LIGHT_PURPLE + " to speak in local.");
                player.sendMessage(ChatColor.GRAY + "To change this back, type " + ChatColor.BOLD + "/p" + ChatColor.GRAY + " again.");
            } else if(PartyMechanics.partyOnlyChat.contains(player)) {
                PartyMechanics.partyOnlyChat.remove(player);
                player.sendMessage(ChatColor.GRAY + "Messages will now be default sent to local chat.");
            }
        }

        if (!(args.length == 0)) {
            StringBuilder message = new StringBuilder();

            for(String str : args) {
                message.append(" ");
                message.append(str);
            }
            party.sendMessageToParty(player, message.toString());
        }

        return true;
    }
}
