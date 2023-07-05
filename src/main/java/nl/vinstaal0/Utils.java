package nl.vinstaal0;

import minecade.dungeonrealms.Main;
import nl.vinstaal0.commands.FixMissingTextureCommand;
import nl.vinstaal0.commands.ActivateLootbuff;
import org.bukkit.event.Listener;
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

}
