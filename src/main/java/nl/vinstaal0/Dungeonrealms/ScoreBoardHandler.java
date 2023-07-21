package nl.vinstaal0.Dungeonrealms;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

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

        if (scoreBoard.getObjective("playerScoreBoard") != null) {
            scoreBoard.getObjective("playerScoreBoard");
        } else {
            scoreBoard.registerNewObjective("playerScoreBoard", "dummy");
        }
    }

}
