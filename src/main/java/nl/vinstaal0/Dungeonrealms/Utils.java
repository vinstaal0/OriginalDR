package nl.vinstaal0.Dungeonrealms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import minecade.dungeonrealms.Main;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.TileEntitySkull;
import nl.vinstaal0.Dungeonrealms.Commands.FixMissingTextureCommand;
import nl.vinstaal0.Dungeonrealms.Commands.ActivateLootbuff;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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

    /**
     * Used to set a custom skin for a mob skull
     * credit to u/Disoriented on spigotmc.org
     * @param skinUrl the URL for the skin, has to be located on textures.minecraft.net
     * **/
    public static ItemStack createCustomhead(String skinUrl) {

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        if (skinUrl == null || skinUrl.isEmpty()) {
            return skull;
        } else {

            ItemMeta skullMeta = skull.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinUrl).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

            Field profileField = null;

            try {
                profileField = skullMeta.getClass().getDeclaredField("profile");
            } catch (NoSuchFieldException | SecurityException exception) {
                exception.printStackTrace();
            }

            if (profileField == null) {
                return skull;
            }

            profileField.setAccessible(true);

            try {
                profileField.set(skullMeta, profile);
            } catch (IllegalArgumentException | IllegalAccessException exception) {
                exception.printStackTrace();
            }

            skull.setItemMeta(skullMeta);

            return skull;
        }

    }

    // TODO fix proper checking if a String is base64 encoded
    public static boolean isBase64EncodedString(String string) {

        return Base64.isBase64(string);
    }

}
