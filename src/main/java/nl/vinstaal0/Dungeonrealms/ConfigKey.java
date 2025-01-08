package nl.vinstaal0.Dungeonrealms;

public enum ConfigKey {

    SERVER_PORT("serverport", 25565),
    HIVE_IP("hiveip", "192.168.20.10"),
    HIVE_IP_BACKUP("hiveipbackup", "localhost"),
    LEVEL_RESTRICTIONS("levelrestrictions", true),
    TIER1_DROPRATE("tier1Droprate", 12),
    TIER2_DROPRATE("tier1Droprate", 5),
    TIER3_DROPRATE("tier1Droprate", 5),
    TIER4_DROPRATE("tier1Droprate", 3),
    TIER5_DROPRATE("tier1Droprate", 1),
    COMMON_DROPRATE("commonDroprate", 80),
    UNCOMMON_DROPRATE("uncommonDroprate", 15),
    RARE_DROPRATE("rareDroprate", 4),
    UNIQUE_DROPRATE("uniqueDroprate", 1),
    ELITE_DROPRATE_MODIFIER("eliteDroprateModifier", 1.5);

    private final String key;
    private final Object defaultValue;

    ConfigKey(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
