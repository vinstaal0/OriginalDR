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

/**
 *
 * Created by Vinstaal0 on 2023-07-06
 */
public class CommandPQuit implements CommandExecutor {

    /**
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;
        PartyMechanics partyMechanics = Main.getPartyMechanics();

        if (!partyMechanics.isInParty(player)) {
            player.sendMessage(ChatColor.RED + "You are not in a party.");
            return true;
        }

        Optional<Party> optionalParty = partyMechanics.getParty(player);
        partyMechanics.removeMember(player, false);

        return true;
    }
}
