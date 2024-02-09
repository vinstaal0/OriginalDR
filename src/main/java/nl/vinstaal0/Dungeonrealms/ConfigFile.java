package nl.vinstaal0.Dungeonrealms;

import minecade.dungeonrealms.Main;

import java.io.*;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class ConfigFile {

    static Logger log = Logger.getLogger("Minecraft");

    private final Properties properties;

    private Set<String> keys;

    public static int transfer_port; // Server port
    public static String Hive_IP; // Hive ip
    public static String Hive_IP_backup; // Backup hive ip
    public static boolean levelRestrictions; // set the level restrictions to be enabled
    public static int tier1Droprate;
    public static int tier2Droprate;
    public static int tier3Droprate;
    public static int tier4Droprate;
    public static int tier5Droprate;
    public static double eliteDroprateModifier;
    public static int commonDroprate;
    public static int uncommonDroprate;
    public static int rareDroprate;

    public static int uniqueDroprate;


    public ConfigFile() {
        properties = new Properties();

        loadConfigFile();
    }

    public void createDefaultConfigFile() {

        File dir = new File("./plugins/" + Main.plugin.getName() + "/");
        if (!dir.exists()) {
            boolean mkdir = dir.mkdir();
            if (!mkdir) log.severe("[WARNING] config file directory cannot be created!");
        }

        try {
            File file = new File("./plugins/" + Main.plugin.getName() + "/" + Main.plugin.getName() + ".properties");
            boolean configFileCreated = file.createNewFile();
            if (!configFileCreated) log.severe("[WARNING] config file cannot be created");
            
            FileReader reader = new FileReader(file);
            FileWriter writer = new FileWriter(file);

            properties.load(reader);

            // List all default properties
            properties.setProperty("serverport", "25565");
            properties.setProperty("hiveip", "192.168.0.107");
            properties.setProperty("hiveipbackup", "localhost");
            properties.setProperty("levelrestrictions", "true");
            properties.setProperty("tier1Droprate", "12");
            properties.setProperty("tier2Droprate", "5");
            properties.setProperty("tier3Droprate", "5");
            properties.setProperty("tier4Droprate", "3");
            properties.setProperty("tier5Droprate", "1");
            properties.setProperty("eliteDroprateModifier", "1.5");
            properties.setProperty("commonDroprate", "80");
            properties.setProperty("uncommonDroprate", "15");
            properties.setProperty("rareDroprate", "4");
            properties.setProperty("uniqueDroprate", "1");

            properties.store(writer, "Config file for the " + Main.plugin.getName() + " plugin.");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void loadConfigFile() {
        File file = new File("./plugins/" + Main.plugin.getName() + "/" + Main.plugin.getName() + ".properties");

        try {
            Reader reader = new FileReader(file);

            try {
                properties.load(reader);
            } catch (FileNotFoundException fileNotFoundException) {
                log.severe("[WARNING] config file not found");
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            keys = properties.stringPropertyNames();

            // Bind the config data to the values
            transfer_port = Integer.parseInt(properties.getProperty("serverport"));
            Hive_IP = properties.getProperty("hiveip");
            Hive_IP_backup = properties.getProperty("hiveipbackup");
            levelRestrictions = Boolean.parseBoolean(properties.getProperty("levelrestrictions"));
            tier1Droprate = Integer.parseInt(properties.getProperty("tier1Droprate"));
            tier2Droprate = Integer.parseInt(properties.getProperty("tier2Droprate"));
            tier3Droprate = Integer.parseInt(properties.getProperty("tier3Droprate"));
            tier4Droprate = Integer.parseInt(properties.getProperty("tier4Droprate"));
            tier5Droprate = Integer.parseInt(properties.getProperty("tier5Droprate"));
            eliteDroprateModifier = Double.parseDouble(properties.getProperty("eliteDroprateModifier"));
            commonDroprate = Integer.parseInt(properties.getProperty("commonDroprate"));
            uncommonDroprate = Integer.parseInt(properties.getProperty("uncommonDroprate"));
            rareDroprate = Integer.parseInt(properties.getProperty("rareDroprate"));
            uniqueDroprate = Integer.parseInt(properties.getProperty("uniqueDroprate"));

        } catch (FileNotFoundException e) {
            createDefaultConfigFile();
        }
    }
}
