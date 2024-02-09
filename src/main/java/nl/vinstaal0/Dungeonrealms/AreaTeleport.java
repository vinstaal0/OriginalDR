package nl.vinstaal0.Dungeonrealms;

import minecade.dungeonrealms.DuelMechanics.DuelMechanics;
import minecade.dungeonrealms.LevelMechanics.LevelMechanics;
import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.MountMechanics.MountMechanics;
import minecade.dungeonrealms.PartyMechanics.PartyMechanics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AreaTeleport {

    public static HashMap<String, Long> processing_move = new HashMap<String, Long>();
    // Player Name, Time of last movement check.

    public void onEnable() {

        // Non-main thread playerMove() event.
        Main.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
            public void run() {
                for (final Player pl : Main.plugin.getServer().getOnlinePlayers()) {

                    String region = DuelMechanics.getRegionName(pl.getLocation());

                    if (region.startsWith("chrestguardavalonentrance")) {

                        if (processing_move.containsKey(pl.getName()) && (System.currentTimeMillis() - processing_move.get(pl.getName())) <= (5 * 1000)) {
                            // Don't get them in a TP loop.
                            continue;
                        }

                        MountMechanics.dismount(pl);

                        Location avalon = new Location(Bukkit.getServer().getWorlds().get(0), -314.0, 154.0, -3489.0);

                        pl.teleport(avalon);

                        continue;
                    }

                    if (region.startsWith("avalonchrestguardentrance")) {

                        if (processing_move.containsKey(pl.getName()) && (System.currentTimeMillis() - processing_move.get(pl.getName())) <= (5 * 1000)) {
                            // Don't get them in a TP loop.
                            continue;
                        }

                        MountMechanics.dismount(pl);

                        Location avalon = new Location(Bukkit.getServer().getWorlds().get(0), -1185.0, 102.0, -515.0);

                        pl.teleport(avalon);

                        continue;
                    }
                }
            }
        }, 5 * 20L, 1 * 20L);
    }
}
