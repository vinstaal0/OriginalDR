package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.MissingResourceException;
import java.util.logging.*;

public class ItemLogger extends Logger {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ItemLogger instance;

    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    protected ItemLogger(String name, String resourceBundleName) throws IOException {
        super(name, resourceBundleName);

        FileHandler fileHandler = new FileHandler("plugins/dungeonrealms/itemtracker.log", true);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                LocalDateTime now = LocalDateTime.now();
                String timestamp = now.format(DATE_TIME_FORMATTER);

                return String.format("%s - %s\n", timestamp, record.getMessage());
            }
        });
        this.addHandler(fileHandler);
        this.setLevel(Level.ALL);
    }

    public static ItemLogger getLogger() {
        if (instance == null) {
            try {
                instance = new ItemLogger("ItemLogger", null);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return instance;
    }

    public void logItem(ItemStack item, Player player, int secret) {

        String itemString = ChatColor.stripColor(item.getItemMeta().getDisplayName());

//        String playerString = player.getDisplayName() != null ? player.getDisplayName() : "N/A";

        String playerString = "N/A";

        if (player != null) {
            playerString = player.getDisplayName();
        }

        String logEntry = String.format("Item: %s, Player: %s, Secret: %d", itemString, playerString, secret);
        this.log(Level.INFO, logEntry);
    }

    public void logItem(ItemStack item, int secret) {
        logItem(item, null, secret);
    }

}
