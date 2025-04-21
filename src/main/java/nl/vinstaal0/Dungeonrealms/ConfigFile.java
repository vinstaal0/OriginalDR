package nl.vinstaal0.Dungeonrealms;

import minecade.dungeonrealms.Main;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ConfigFile {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static ConfigFile instance;

    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    private final File configFile;
    private final Yaml yaml;

    public static String version = "25.04.27";

    private ConfigFile() {
        configFile = new File("./plugins/" + Main.plugin.getName() + "/config.yml");

        // YAML formatting options
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setIndent(2);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yaml = new Yaml(options);

        loadConfigFile();
    }

    public static ConfigFile getInstance() {
        if (instance == null) {
            synchronized (ConfigFile.class) {
                if (instance == null) {
                    instance = new ConfigFile();
                }
            }
        }
        return instance;
    }

    private void loadConfigFile() {
        for (ConfigKey key : ConfigKey.values()) {
            cache.putIfAbsent(key.getKey(), key.getDefaultValue());
        }

        if (!configFile.exists()) {
            log.warning("Config file not found, creating default one.");
            saveConfigFile();
        }

        try (InputStream input = new FileInputStream(configFile)) {
            Object loadedObject = yaml.load(input);

            if (loadedObject instanceof Map) {
                Map<?, ?> loadedConfig = (Map<?, ?>) loadedObject;

                for (Map.Entry<?, ?> entry : loadedConfig.entrySet()) {
                    if (entry.getKey() instanceof String) {
                        cache.put((String) entry.getKey(), entry.getValue());
                    }
                }
            }

            log.info("Config loaded into cache.");
        } catch (IOException e) {
            log.severe("Failed to load config file: " + e.getMessage());
        }
    }

    private void saveConfigFile() {
        try (Writer writer = new FileWriter(configFile)) {
            yaml.dump(cache, writer);
            log.info("Config saved to file.");
        } catch (IOException e) {
            log.severe("Failed to save config file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfigValue(ConfigKey key) {
        Object value = cache.getOrDefault(key.getKey(), key.getDefaultValue());

        if (value == null) {
            throw new IllegalStateException("Config value for key " + key.getKey() + " is null.");
        }

        // Switch over class types
        Class<?> valueClass = value.getClass();

        switch (valueClass.getSimpleName()) {
            case "Integer":
                // Convert Integer to Double
                return (T) (Double) ((Integer) value).doubleValue();

            case "Double":
                // Return as is if it's already a Double
                return (T) (Double) value;

            case "Boolean":
                return (T) (Boolean) value;

            case "Long":
                return (T) (Long) value;

            case "Float":
                return (T) (Float) value;

            case "Short":
                return (T) (Short) value;

            case "Byte":
                return (T) (Byte) value;

            case "Character":
                return (T) (Character) value;

            default:
                // If it's any other object type (e.g., String), return it
                return (T) value;
        }


    }


    public void setConfigValue(ConfigKey key, Object value) {
        cache.put(key.getKey(), value);
        saveConfigFile();
    }

    public void reloadConfig() {
        loadConfigFile();
        log.info("Config reloaded.");
    }
}
