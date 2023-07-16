package nl.vinstaal0.Dungeonrealms.PartyMechanics.Commands;

import minecade.dungeonrealms.Main;
import nl.vinstaal0.Dungeonrealms.PartyMechanics.LootType;
import nl.vinstaal0.Dungeonrealms.PartyMechanics.Party;
import nl.vinstaal0.Dungeonrealms.PartyMechanics.PartyMechanics;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandPLootType implements TabExecutor {
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
            return true;
        }

        if (optionalParty.isPresent()) {
            party = optionalParty.get();
        }

        if (party == null) {
            player.sendMessage(ChatColor.RED + "You are not in a party.");
            return true;
        }

        if (!partyMechanics.isOwner(player)) {
            player.sendMessage(ChatColor.RED.toString() + "You are NOT the leader of your party.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Invalid syntax, use /ploot loottype.");
            player.sendMessage(ChatColor.RED + "Available loottypes are ");
            LootType[] arr = LootType.values();

            int i = 0;

            while (i <= arr.length-1) {
                player.sendMessage(ChatColor.RED + "- " + arr[i]);
                i++;
            }

            return true;

        }

        if (args[0].equalsIgnoreCase(LootType.NONE.toString())) {

            party.setLootType(LootType.NONE);

            party.sendMessageToParty(" The loot profile of this party has been set to: " + ChatColor.LIGHT_PURPLE + LootType.NONE);

            return true;
        }

        if (args[0].equalsIgnoreCase(LootType.RANDOM.toString())) {

            party.setLootType(LootType.RANDOM);

            party.sendMessageToParty(" The loot profile of this party has been set to: " + ChatColor.LIGHT_PURPLE + LootType.RANDOM);

            return true;
        }

        if (args[0].equalsIgnoreCase(LootType.HIDDEN.toString())) {

            party.setLootType(LootType.HIDDEN);

            party.sendMessageToParty(" The loot profile of this party has been set to: " + ChatColor.LIGHT_PURPLE + LootType.HIDDEN);

            return true;
        }

        if (args[0].equalsIgnoreCase(LootType.ROUNDROBIN.toString())) {

            party.setLootType(LootType.ROUNDROBIN);

            party.sendMessageToParty(" The loot profile of this party has been set to: " + ChatColor.LIGHT_PURPLE + LootType.ROUNDROBIN);

            return true;
        }

        player.sendMessage(ChatColor.RED + "Invalid syntax, use /ploot loottype.");
        player.sendMessage(ChatColor.RED + "Available loottypes are ");
        LootType[] arr = LootType.values();

        int i = 0;

        while (i <= arr.length-1) {
            player.sendMessage(ChatColor.RED + "- " + arr[i]);
            i++;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        commands.add("none");
        commands.add("random");
        commands.add("hidden");
        commands.add("roundrobin");
        StringUtil.copyPartialMatches(args[0], commands, completions);

        return completions;
    }
}
