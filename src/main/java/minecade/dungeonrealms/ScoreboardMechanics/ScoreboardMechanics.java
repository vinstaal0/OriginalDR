package minecade.dungeonrealms.ScoreboardMechanics;

import java.util.HashMap;

import nl.vinstaal0.ScoreBoardHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardMechanics implements Listener {
	
	private static HashMap<Player, Scoreboard> boards = new HashMap<Player, Scoreboard>();

	public static Scoreboard main;
	public static ScoreboardManager instance;
	
	public ScoreboardMechanics() {
		instance = Bukkit.getScoreboardManager();
		main = instance.getNewScoreboard();

		ScoreBoardHandler handler = new ScoreBoardHandler(instance, main);
		
		Objective objective = main.registerNewObjective("hpdisplay", "dummy");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName("§c" + "❤");
		
		Team green = main.registerNewTeam("green");
		green.setPrefix(ChatColor.GREEN.toString());

		Team dark_green = main.registerNewTeam("dark_green");
		dark_green.setPrefix(ChatColor.DARK_GREEN.toString());
		
		Team yellow = main.registerNewTeam("yellow");
		yellow.setPrefix(ChatColor.YELLOW.toString());
		
		Team red = main.registerNewTeam("red");
		red.setPrefix(ChatColor.RED.toString());
		
		Team dark_red = main.registerNewTeam("dark_red");
		dark_red.setPrefix(ChatColor.DARK_RED.toString());
		
		Team purple = main.registerNewTeam("purple");
		purple.setPrefix(ChatColor.LIGHT_PURPLE.toString());
		
		Team white = main.registerNewTeam("white");
		white.setPrefix(ChatColor.WHITE.toString());
		
		Team aqua = main.registerNewTeam("aqua");
		aqua.setPrefix(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "GM" + ChatColor.AQUA.toString() + " ");
		
		Team TI = main.registerNewTeam("TI");
		TI.setCanSeeFriendlyInvisibles(true);
		TI.setDisplayName("TI");
	}
	
	public static Scoreboard getBoard(final Player plr) {
		if(!boards.containsKey(plr)) {
			Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
		    
			Objective objective = b.registerNewObjective("hpdisplay", "dummy");
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objective.setDisplayName("§c" + "❤");
			
			Team green = b.registerNewTeam("green");
			green.setPrefix(ChatColor.GREEN.toString());
			
			Team dark_green = b.registerNewTeam("dark_green");
			dark_green.setPrefix(ChatColor.DARK_GREEN.toString());
			
			Team yellow = b.registerNewTeam("yellow");
			yellow.setPrefix(ChatColor.YELLOW.toString());
			
			Team red = b.registerNewTeam("red");
			red.setPrefix(ChatColor.RED.toString());
			
			Team dark_red = b.registerNewTeam("dark_red");
			dark_red.setPrefix(ChatColor.DARK_RED.toString());
			
			Team purple = b.registerNewTeam("purple");
			purple.setPrefix(ChatColor.LIGHT_PURPLE.toString());
			
			Team white = b.registerNewTeam("white");
			white.setPrefix(ChatColor.WHITE.toString());
			
			Team aqua = b.registerNewTeam("aqua");
			aqua.setPrefix(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "GM" + ChatColor.AQUA.toString() + " ");
			
			Team TI = b.registerNewTeam("TI");
			TI.setCanSeeFriendlyInvisibles(true);
			TI.setDisplayName("TI");
			
			boards.put(plr, b);
			plr.setScoreboard(b); 
		}
		return boards.get(plr);
	}
	
	public static Team getTeam(Scoreboard board, String name) {
		if(main.getTeam(name) == null) main.registerNewTeam(name);
		if(board.getTeam(name) == null) return board.registerNewTeam(name);
		return board.getTeam(name);
	}
	
	public static void cloneScoreboard(Player forp) {
		Scoreboard board = getBoard(forp);
		for(Team t : main.getTeams()) {
			Team x = getTeam(board, t.getName());
			x.setAllowFriendlyFire(t.allowFriendlyFire());
			x.setCanSeeFriendlyInvisibles(t.canSeeFriendlyInvisibles());
			x.setDisplayName(t.getDisplayName());
			x.setPrefix(t.getPrefix());
			for(OfflinePlayer o : t.getPlayers())
				x.addPlayer(o);
		}
	}
	
	private void removeBoard(Player p) {
		if(boards.containsKey(p)) boards.remove(p);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		removeBoard(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		cloneScoreboard(e.getPlayer());
	}
	
    public static void setOverheadHP(Player pl, int hp) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			ScoreboardMechanics.getBoard(p).getObjective(DisplaySlot.BELOW_NAME).getScore(pl.getName()).setScore(hp);
		}
//		ScoreboardMechanics.getBoard(pl).getObjective(DisplaySlot.BELOW_NAME).getScore(pl.getName()).setScore(hp);
		main.getObjective(DisplaySlot.BELOW_NAME).getScore(pl.getName()).setScore(hp);
	}
	
    private static Team getTeam(Scoreboard sb, OfflinePlayer target){
		if(sb.getTeam(target.getName()) == null) return sb.registerNewTeam(target.getName());
		return sb.getTeam(target.getName());
	}
	
/*	public static void setPlayerColor(ChatColor color, OfflinePlayer pl){  deprecated methods, only updates one each instead of both level and color
		for(Player p : Bukkit.getOnlinePlayers()) {
			Team t = new TeamBuild(getTeam(ScoreboardMechanics.getBoard(p), pl)).setColor(color).getTeam();
			if(!t.hasPlayer(pl)) t.addPlayer(pl);
		}
		Team t = new TeamBuild(getTeam(main, pl)).setColor(color).getTeam();
		if(!t.hasPlayer(pl)) t.addPlayer(pl);
	}
	
	public static void setPlayerLevel(int level, OfflinePlayer pl){
		for(Player p : Bukkit.getOnlinePlayers()) {
			Team t = new TeamBuild(getTeam(ScoreboardMechanics.getBoard(p), pl)).setLevel(level).setColor(KarmaMechanics.getNameplateAlignColor(pl.getName())).getTeam();
			if(!t.hasPlayer(pl)) t.addPlayer(pl);
		}
		Team t = new TeamBuild(getTeam(main, pl)).setLevel(level).setColor(KarmaMechanics.getNameplateAlignColor(pl.getName())).getTeam();
		if(!t.hasPlayer(pl)) t.addPlayer(pl);
	}*/
	
	public static void setPlayerLevelAndColor(ChatColor color, int level, OfflinePlayer pl) {
	    for(Player p : Bukkit.getOnlinePlayers()) {
            Team t = new TeamBuild(getTeam(ScoreboardMechanics.getBoard(p), pl)).setLevel(level).setColor(color).getTeam();
            if(!t.hasPlayer(pl)) t.addPlayer(pl);
        }
        Team t = new TeamBuild(getTeam(main, pl)).setLevel(level).setColor(color).getTeam();
        if(!t.hasPlayer(pl)) t.addPlayer(pl);
	}
	
}
