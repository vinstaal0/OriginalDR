package nl.vinstaal0.Dungeonrealms.PartyMechanics;

import minecade.dungeonrealms.ChatMechanics.ChatMechanics;
import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.MonsterMechanics.MonsterMechanics;
import minecade.dungeonrealms.jsonlib.JSONMessage;
import nl.vinstaal0.Dungeonrealms.ScoreBoardHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * Created by Vinstaal0 on 2023-07-05
 */
public class Party {

    private Player owner;

    private List<Player> members;

    private boolean updateScoreboard;

    private Scoreboard partyScoreboard;

    /**
     *
     * @param owner set the owner of the part
     * @param members a list of all members
     */
    public Party(Player owner, List<Player> members) {
        this.owner = owner;
        this.members = members;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public boolean isUpdateScoreboard() {
        return updateScoreboard;
    }

    public void setUpdateScoreboard(boolean updateScoreboard) {
        this.updateScoreboard = updateScoreboard;
    }

    public Scoreboard getPartyScoreboard() {
        return partyScoreboard;
    }

    public void setPartyScoreboard(Scoreboard partyScoreboard) {
        this.partyScoreboard = partyScoreboard;
    }

    private LootType lootType = LootType.NONE;
    private int roundRobinNext = 0;

    public Scoreboard createScoreboard() {
        Scoreboard scoreBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        ScoreBoardHandler instance = ScoreBoardHandler.getInstance();

        instance.registerHealth(scoreBoard);

        return scoreBoard;
    }

    public void sendMessageToParty(Player sender, String rawMessage) {

        String prefix = ChatMechanics.getPlayerPrefix(sender.getName(), true);

        String filteredMessage = rawMessage;

        for (Player receiver : members) {

            ChatColor playerColor = ChatMechanics.getPlayerColor(sender, receiver);


            if(ChatMechanics.hasAdultFilter(receiver.getName())) {
                filteredMessage = ChatMechanics.censorMessage(rawMessage);
            }

            filteredMessage.trim();
            filteredMessage = ChatMechanics.fixCapsLock(filteredMessage);

            receiver.sendMessage(ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + ">" + ChatColor.GRAY + " " + prefix +
                    playerColor + sender.getName() + ": " + ChatColor.GRAY + filteredMessage);
        }

        if(ChatMechanics.hasAdultFilter(owner.getName())) {
            filteredMessage = ChatMechanics.censorMessage(rawMessage);
        }

        ChatColor playerColor = ChatMechanics.getPlayerColor(sender, owner);

        filteredMessage.trim();
        filteredMessage = ChatMechanics.fixCapsLock(filteredMessage);

        owner.sendMessage(ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + ">" + ChatColor.GRAY + " " + prefix +
                playerColor + sender.getName() + ": " + ChatColor.GRAY + filteredMessage);

        Main.log.info("<P> " + sender.getName() + ": " + rawMessage);

    }

    public void sendMessageToParty(String rawMessage) {

        String messageToSend = ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + ">" + ChatColor.GRAY + rawMessage + " ";

        for (Player receiver : members) {

            receiver.sendMessage(messageToSend);
        }

        owner.sendMessage(messageToSend);

    }

    public void dropItemtoParty(Player player, Location loc, ItemStack item, Entity entity) {

        String mobName = MonsterMechanics.getMobType(entity, false);
        Location dropLocation = loc;

        JSONMessage msg;
        String prefix = ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + "> " + ChatColor.GRAY;
        String aprefix = ": " + ChatColor.WHITE;

        String before = "";
        String after = ".";

        List<Player> newMembers = new ArrayList<>();
        newMembers.addAll(members);
        newMembers.add(owner);

        // TODO check if players are nearby

        switch (this.lootType) {

            case ROUNDROBIN -> {

                // TODO deal with players who aren't nearby

                String receiver = player.getName();

                if(this.roundRobinNext <= newMembers.size()-1) {

                    Player p = newMembers.get(this.roundRobinNext);
                    receiver = p.getName();

                    dropLocation = p.getLocation();

                    this.roundRobinNext++;

                    if (this.roundRobinNext >= newMembers.size()) {
                        this.roundRobinNext = 0;
                    }

                } else {
                    roundRobinNext = 0; // reset back to 0
                }

                before = ChatColor.GREEN + receiver + " has received ";
                after = ChatColor.GREEN + " as a drop.";
            }
            case RANDOM -> {

                Random rand = new Random();
                Player randomMember = newMembers.get(rand.nextInt(newMembers.size()));

                String receiver = player.getName();

                if (randomMember.getLocation().getWorld().getName().equalsIgnoreCase(entity.getWorld().getName())) {
                    receiver = randomMember.getName();
                    dropLocation = randomMember.getLocation();
                } else {
                    // If selected player is not in the correct world select the killer
                    dropLocation = player.getLocation();
                }

                before = ChatColor.GREEN + receiver + " has received ";
                after = ChatColor.GREEN + " as a drop.";
            }
            case HIDDEN -> {

                dropLocation = loc;
                return;
            }
            case NONE -> {

                before = ChatColor.GREEN + mobName + ChatColor.GREEN + " has been killed by " + player.getName() + ChatColor.GREEN + " and dropped ";
                after = ChatColor.GREEN + ".";
            }
        }

        msg = new JSONMessage(prefix + ChatColor.GRAY + aprefix, ChatColor.WHITE);
        msg.addText(before + " ");
        msg.addItem(item, ChatColor.BOLD + "SHOW", ChatColor.UNDERLINE);
        msg.addText(after);

        JSONMessage toSent = msg;

        for (Player p : newMembers) {
            toSent.sendToPlayer(p);
        }

        dropLocation.getWorld().dropItemNaturally(dropLocation, item);

        newMembers.clear();
    }

    public LootType getLootType() {
        return lootType;
    }

    public void setLootType(LootType lootType) {
        this.lootType = lootType;
    }
}
