package nl.vinstaal0.PartyMechanics;

import nl.vinstaal0.ScoreBoardHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

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


    public Scoreboard createScoreboard() {
        Scoreboard scoreBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        ScoreBoardHandler instance = ScoreBoardHandler.getInstance();

        instance.registerHealth(scoreBoard);

        return scoreBoard;
    }
}
