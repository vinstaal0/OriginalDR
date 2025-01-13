package nl.vinstaal0.Dungeonrealms.PartyMechanics;

import minecade.dungeonrealms.ChatMechanics.ChatMechanics;
import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.MonsterMechanics.MonsterMechanics;
import minecade.dungeonrealms.jsonlib.JSONMessage;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks.Money;
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

    private ArrayList<Player> members;

    private boolean updateScoreboard;

    private Scoreboard partyScoreboard;

    private LootType lootType = LootType.NONE;
    private int roundRobinNext = 0;

    private final double defaultDistance = 32;

    /**
     *
     * @param owner set the owner of the part
     * @param members a list of all members
     */
    public Party(Player owner, List<Player> members) {
        this.owner = owner;
        this.members = new ArrayList<>();
        this.members.addAll(members);
        System.out.println(members);
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

        // Remove old members
        for (Player player : this.members) {
            this.members.remove(player);
        }

        // Add new members
        this.members.addAll(members);

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

    public void sendMessageToMember(Player player, String rawMessage) {

        String messageToSend = ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + ">" + ChatColor.GRAY + rawMessage + " ";

        player.sendMessage(messageToSend);

    }

    public static boolean isWithinDistance(Player toCheck, Player player, double distance) {
        Location dropperLocation = toCheck.getLocation();
        Location playerLocation = player.getLocation();

        // Ensure they are in the same world
        if (!dropperLocation.getWorld().equals(playerLocation.getWorld())) {
            return false;
        }

        return dropperLocation.distance(playerLocation) <= distance;
    }

    /**
     *
     * @param source the player who caused the gems to drop
     * @param loc the location the drop happens
     * @param item the item top drop
     * @param entity the entity that was killed
     */
    public void dropItemToParty(Player source, Location loc, ItemStack item, Entity entity) {

        String mobName = MonsterMechanics.getMobType(entity, false);
        Location dropLocation = loc;

        JSONMessage msg;
        String prefix = ChatColor.LIGHT_PURPLE + "<" + ChatColor.BOLD + "P" + ChatColor.LIGHT_PURPLE + "> " + ChatColor.GRAY;
        String aprefix = ": " + ChatColor.WHITE;

        String before = "";
        String after = ".";

        List<Player> newMembers = new ArrayList<>(members);
        newMembers.add(owner);

        List<Player> nearbyPlayers = new ArrayList<>(newMembers.stream()
                .filter(player -> isWithinDistance(source, player, defaultDistance))
                .toList());

        switch (this.lootType) {

            case ROUNDROBIN : {

                String receiver = source.getName();

                if(this.roundRobinNext <= nearbyPlayers.size()-1) {

                    Player p = nearbyPlayers.get(this.roundRobinNext);
                    receiver = p.getName();
                    dropLocation = p.getLocation();

                    this.roundRobinNext++;

                    if (this.roundRobinNext >= nearbyPlayers.size()) {
                        this.roundRobinNext = 0;
                    }

                } else {
                    roundRobinNext = 0; // reset back to 0
                }

                before = ChatColor.GREEN + receiver + " has received ";
                after = ChatColor.GREEN + " as a drop.";

                break;
            }

            case RANDOM : {

                Random rand = new Random();
                Player randomMember = nearbyPlayers.get(rand.nextInt(nearbyPlayers.size()));

                String receiver = source.getName();

                if (randomMember.getLocation().getWorld().getName().equalsIgnoreCase(entity.getWorld().getName())) {
                    receiver = randomMember.getName();
                    dropLocation = randomMember.getLocation();
                } else {
                    // If selected player is not in the correct world select the killer
                    dropLocation = source.getLocation();
                }

                before = ChatColor.GREEN + receiver + " has received ";
                after = ChatColor.GREEN + " as a drop.";

                break;
            }
            case HIDDEN : {

                dropLocation = loc;
                break;
            }
            case NONE : {

                before = ChatColor.GREEN + mobName + ChatColor.GREEN + " has been killed by " + source.getName() + ChatColor.GREEN + " and dropped ";
                after = ChatColor.GREEN + ".";
            }
        }

        msg = new JSONMessage(prefix + ChatColor.GRAY + aprefix, ChatColor.WHITE);
        msg.addText(before + " ");
        msg.addItem(item, ChatColor.BOLD + "SHOW", ChatColor.UNDERLINE);
        msg.addText(after);

        JSONMessage toSent = msg;

        for (Player p : nearbyPlayers) {
            toSent.sendToPlayer(p);
        }

        dropLocation.getWorld().dropItemNaturally(dropLocation, item);

        nearbyPlayers.clear();
        newMembers.clear();
    }

    /**
     *
     * @param source the player who caused the gems to drop
     * @param amount the amount of gems to drop
     */
    public void dropGemToParty(Player source, int amount) {

        List<Player> nearbyPlayers = this.getMembers().stream()
                .filter(player -> isWithinDistance(source, player, defaultDistance))
                .toList();

        int partySize = nearbyPlayers.size();

        int baseAmount = amount / partySize;
        int remainder = amount % partySize;

        Random random = new Random();
        int startIndex = random.nextInt(this.getMembers().size());

        for (Player player : nearbyPlayers) {
            ItemStack itemStack = Money.makeGems(baseAmount);
            Location loc = player.getLocation();
            loc.getWorld().dropItemNaturally(loc, itemStack);
        }

        for (int i = 0; i < remainder; i++) {
            int currentIndex = (startIndex + 1) % partySize;

            ItemStack itemStack = Money.makeGems(baseAmount);
            Location loc = nearbyPlayers.get(currentIndex).getLocation();
            loc.getWorld().dropItemNaturally(loc, itemStack);
        }

    }

    public LootType getLootType() {
        return lootType;
    }

    public void setLootType(LootType lootType) {
        this.lootType = lootType;
    }

    public void addMember(Player player) {
        this.members.add(player);
        sendMessageToParty(player.getDisplayName() + " has joined the party.");
    }
}
