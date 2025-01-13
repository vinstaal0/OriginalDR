package nl.vinstaal0.Dungeonrealms;

public enum ConfigKey {

    SERVER_PORT("serverport", 25565),

    HIVE_IP("hiveip", "192.168.20.10"),
    HIVE_IP_BACKUP("hiveipbackup", "localhost"),
    FTP_PORT("ftp_port", 21),
    LEVEL_RESTRICTIONS("levelrestrictions", true),
    TIER1_DROPRATE("tier1Droprate", 12),
    TIER2_DROPRATE("tier1Droprate", 5),
    TIER3_DROPRATE("tier1Droprate", 5),
    TIER4_DROPRATE("tier1Droprate", 3),
    TIER5_DROPRATE("tier1Droprate", 1),
    T1ORE("tier1ore", 90),
    T2ORE("tier1ore", 275),
    T3ORE("tier1ore", 460),
    T4ORE("tier1ore", 820),
    T5ORE("tier1ore", 1025),
    COMMON_DROPRATE("commonDroprate", 80),
    UNCOMMON_DROPRATE("uncommonDroprate", 15),
    RARE_DROPRATE("rareDroprate", 4),
    UNIQUE_DROPRATE("uniqueDroprate", 1),
    ELITE_DROPRATE_MODIFIER("eliteDroprateModifier", 1.5),
    ORB_OF_PEACE("Drop_orb_of_peace", true),
    EASTER_EVENT("Halloween_event", false),
    HALLOWEEN_EVENT("Easter_event", false);

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
