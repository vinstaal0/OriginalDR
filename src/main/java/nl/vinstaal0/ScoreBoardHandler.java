package nl.vinstaal0;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreBoardHandler {

    private static ScoreBoardHandler instance;
    private ScoreboardManager manager;
    private Scoreboard mainBoard;

    public ScoreBoardHandler(ScoreboardManager m, Scoreboard mb) {
        manager = m;
        mainBoard = mb;
        instance = this;
        mainBoard = m.getMainScoreboard();
    }
    public static ScoreBoardHandler getInstance() {
        return instance;
    }

    public Scoreboard getMainBoard() {
        return mainBoard;
    }

    public void registerHealth(Scoreboard scoreBoard) {
        Objective objective = null;

        if (scoreBoard.getObjective("playerScoreBoard") != null) {
            scoreBoard.getObjective("playerScoreBoard");
        } else {
            scoreBoard.registerNewObjective("playerScoreBoard", "dummy");
        }
    }

    public Team getPlayerTeam(Scoreboard scoreBoard, Player player) {
        if (scoreBoard.getTeam(player.getName()) == null) {
            Team team = scoreBoard.registerNewTeam(player.getName());
            return team;
        }
        return scoreBoard.getTeam(player.getName());
    }

}
