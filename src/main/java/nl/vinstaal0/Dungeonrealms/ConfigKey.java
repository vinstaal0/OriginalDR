package nl.vinstaal0.Dungeonrealms;

public enum ConfigKey {

    SERVER_PORT("serverport", 25565),

    HIVE_IP("hiveip", "192.168.20.10"),
    HIVE_IP_BACKUP("hiveipbackup", "localhost"),
    FTP_PORT("ftp_port", 21),
    LEVEL_RESTRICTIONS("levelrestrictions", true),
    TIER1_DROPRATE("tier1Droprate", 12),
    TIER2_DROPRATE("tier2Droprate", 5),
    TIER3_DROPRATE("tier3Droprate", 5),
    TIER4_DROPRATE("tier4Droprate", 3),
    TIER5_DROPRATE("tier5Droprate", 1),
    TIER1_ORE("tier1ore", 90),
    TIER2_ORE("tier2ore", 275),
    TIER3_ORE("tier3ore", 460),
    TIER4_ORE("tier4ore", 820),
    TIER5_ORE("tier5ore", 1025),
    COMMON_DROPRATE("commonDroprate", 80),
    UNCOMMON_DROPRATE("uncommonDroprate", 15),
    RARE_DROPRATE("rareDroprate", 4),
    UNIQUE_DROPRATE("uniqueDroprate", 1),
    ELITE_DROPRATE_MODIFIER("eliteDroprateModifier", 1.5),
    TIER1_DURABILITY("tier1durability", 1500),
    TIER2_DURABILITY("tier2durability", 1500),
    TIER3_DURABILITY("tier3durability", 1500),
    TIER4_DURABILITY("tier4durability", 1500),
    TIER5_DURABILITY("tier5durability", 1500),
    TIER1_SCRAP("tier1scrap", 3.0D),
    TIER2_SCRAP("tier2scrap", 3.0D),
    TIER3_SCRAP("tier3scrap", 3.0D),
    TIER4_SCRAP("tier4scrap", 3.0D),
    TIER5_SCRAP("tier5scrap", 3.0D),
    TIER3_ORE_VALUE("tier3orevalue", 2.0D),
    TIER4_ORE_VALUE("tier4orevalue", 2.0D),
    TIER5_ORE_VALUE("tier5orevalue", 2.0D),
    TIER2_HORSE_price("tier2horseprice", 3000),
    TIER3_HORSE_price("tier3horseprice", 7000),
    TIER4_HORSE_price("tier4horseprice", 15000),
    TIER5_HORSE_price("tier5horseprice", 30000),
    BANK_UPGRADE_DISCOUNT("bankupgrade", 1.0),
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
