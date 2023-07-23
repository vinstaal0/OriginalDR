package nl.vinstaal0.Dungeonrealms;

import minecade.dungeonrealms.Main;
import nl.vinstaal0.Dungeonrealms.Commands.FixMissingTextureCommand;
import nl.vinstaal0.Dungeonrealms.Commands.ActivateLootbuff;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Utils implements Listener {

    static Logger log = Logger.getLogger("Minecraft");

    public static Plugin plugin = null;

    public Utils() {
        this.plugin = Main.plugin;
    }

    public static void onEnable() {
        Main.plugin.getCommand("fixmissingtexture").setExecutor(new FixMissingTextureCommand());
        Main.plugin.getCommand("activatelootbuff").setExecutor(new ActivateLootbuff());
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e) {
        if (!e.getPlayer().isOp() && e.getPlayer().getWorld().getName().equalsIgnoreCase(Bukkit.getWorlds().get(0).getName())) {
            e.setBuild(false);
            e.setCancelled(true);
        }
    }

}
