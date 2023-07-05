package nl.vinstaal0.partyMechanics;

import minecade.dungeonrealms.HealthMechanics.HealthMechanics;
import minecade.dungeonrealms.Hive.Hive;
import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.managers.PlayerManager;
import nl.vinstaal0.ScoreBoardHandler;
import nl.vinstaal0.partyMechanics.commands.CommandPAccept;
import nl.vinstaal0.partyMechanics.commands.CommandPInvite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 *
 * Created by Vinstaal0 on 2023-07-05
 * based on the code made by Nick on 2015-09-11
 */
public class PartyMechanics extends minecade.dungeonrealms.PartyMechanics.PartyMechanics implements Listener {

    static Logger log = Logger.getLogger("Minecraft");

    public CopyOnWriteArrayList<Party> parties = new CopyOnWriteArrayList<>();
    public static ConcurrentHashMap<Player, Party> invitations = new ConcurrentHashMap<>();

    public void onEnable() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);

        Main.plugin.getCommand("pinvite").setExecutor(new CommandPInvite());
        Main.plugin.getCommand("paccept").setExecutor(new CommandPAccept());

        if (Main.getPartyMechanics().getClass().isInstance(minecade.dungeonrealms.PartyMechanics.PartyMechanics.class)) {
            log.warning("[PartyMechanics] Original partymechanics loaded by mistake!");
        } else {
            log.info("[PartyMechanics] has been ENABLED.");
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                parties.forEach(party -> {
                    if (party.getOwner() == null) {
                        removeParty(party);
                    } else {
                        // Set up the scoreboard
                        Scoreboard board;
                        if (party.getPartyScoreboard() == null) {
                            board = party.createScoreboard();
                            party.setPartyScoreboard(board);
                        } else {
                            board = party.getPartyScoreboard();
                        }

                        Objective objective = board.getObjective("party");
                        if (objective == null) {
                            objective = board.registerNewObjective("party", "dummy");
                            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                            objective.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Party");
                        }

                        List<Player> members = new ArrayList<>();
                        members.add(party.getOwner());
                        members.addAll(party.getMembers());

                        for (Player player : members) {
                            if (player != null) {
                                if (player.isOnline()) {
                                    Score score = objective.getScore(player.getName());
//                                score.setScore(PlayerManager.getPlayerModel(player).getHealth()); // TODO check if this is actually the DR HP and not the vanilla HP
                                    score.setScore(HealthMechanics.getPlayerHP(player.getName()));
                                    if (player.getScoreboard() != board) {
                                        player.setScoreboard(board);
                                    }
                                } else {
                                    removeMember(player, false);
                                }
                            }
                        }

                    }
                });
            }
        }, 0 ,15);

    }

    public void invitePlayer(Player sender, Player receiver) {
        invitations.put(receiver, getParty(sender).get());
        receiver.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.UNDERLINE + sender.getName() + ChatColor.GRAY + " has invited you to join their party. To accept, type " + ChatColor.LIGHT_PURPLE.toString() + "/paccept" + ChatColor.GRAY + " or to decline, type " + ChatColor.LIGHT_PURPLE.toString() + "/pdecline");
        sender.sendMessage(ChatColor.GRAY + "You have invited " + ChatColor.LIGHT_PURPLE.toString() + receiver.getName() + ChatColor.GRAY + " to join your party.");
    }

    public void removeMember(Player player, boolean kicked) {
        if (!getParty(player).isPresent());
        if (isOwner(player)) {
            removeParty(getParty(player).get());
        };

        Party party = getParty(player).get();

        party.getMembers().remove(player);
        Scoreboard board = party.getPartyScoreboard();
        board.resetScores(player.getName());

        if (kicked) {
            player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "You have been kicked out of the party.");
        } else {
            player.sendMessage(ChatColor.RED + "You have left the party.");
        }

        party.getOwner().sendMessage(ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + "> " + ChatColor.GRAY + player.getDisplayName() + " has " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + "left" + ChatColor.GRAY + " the party.");
        party.getMembers().stream().filter(player1 -> !player1.getName().equals(party.getOwner().getName())).forEach(player1 -> player1.sendMessage(ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + "> " + ChatColor.GRAY + player.getDisplayName() + " has " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + "left" + ChatColor.GRAY + " the party."));

        player.setScoreboard(ScoreBoardHandler.getInstance().getMainBoard());

        // TODO fix when players are in a dungeon
    }

    public boolean isOwner(Player player) {

        if (isInParty(player)) {
            if (getParty(player).get().getOwner().equals(player)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInParty(Player player) {
        for (Party party : parties) {
            if (party.getOwner().getName().equals(player.getName())) {
                return true;
            }

            for (Player member : party.getMembers()) {
                if (member.getName().equals(member.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean areInSameParty(Player p1, Player p2) {
        if (isInParty(p1) && isInParty(p2)) {
            if (getParty(p1).get().getOwner().getName().equalsIgnoreCase(getParty(p2).get().getOwner().getName().toLowerCase())) {
                return true;
            }
        }
        return false; // At least one of the two isn't in a party
    }

    public void createParty(Player player) {
        parties.add(new Party(player, new ArrayList<>()));
        player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Your party has been created!");
        player.sendMessage(ChatColor.GRAY + "To invite more people to join your party, " + ChatColor.UNDERLINE + "Left Click" + ChatColor.GRAY + " them with your character journal or use " + ChatColor.BOLD + "/pinvite" + ChatColor.GRAY + ". To kick, use " + ChatColor.BOLD + "/pkick" + ChatColor.GRAY + ". To chat with party, use " + ChatColor.BOLD + "/p" + ChatColor.GRAY + ". To change the loot profile, use " + ChatColor.BOLD + "/ploot");
        // TODO achievement
    }

    public Optional<Party> getParty(Player player) {
        return parties.stream().filter(party0 -> party0.getOwner().equals(player) || party0.getMembers().contains(player)).findFirst();
    }

    public void removeParty(Party party) {

        List<Player> players = new ArrayList<>();
        players.add(party.getOwner());
        players.addAll(party.getMembers());

        players.forEach(player -> {
            if (Hive.isPlayerOnline(player.getName())) {
                if (true) { // TODO check if player is in Dungeon

                }

                player.setScoreboard(ScoreBoardHandler.getInstance().getMainBoard()); // TODO add scoreboardhandeler
                player.sendMessage(ChatColor.RED + "Your party has been disbanded.");
            }
        });

        log.info("The party of " + party.getOwner().getName() + " has been disbanded.");

        parties.remove(party);

    }

}
