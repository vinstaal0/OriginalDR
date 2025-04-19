package nl.vinstaal0.Dungeonrealms.TierMechanics;


import org.bukkit.ChatColor;

public enum Tier {

    ONE(1, ChatColor.WHITE),
    TWO(2, ChatColor.GREEN),
    THREE(3, ChatColor.AQUA),
    FOUR(4, ChatColor.LIGHT_PURPLE),
    FIVE(5, ChatColor.YELLOW);

    private final int toInt;
    private final ChatColor color;

    Tier(int toInt, ChatColor color) {
        this.toInt = toInt;
        this.color = color;
    }

    public static Tier getTier(String string) throws IllegalArgumentException {
        switch(string) {
            case "1" :
                return ONE;
            case "2" :
                return TWO;
            case "3" :
                return THREE;
            case "4" :
                return FOUR;
            case "5" :
                return FIVE;
            default :
                return null;
        }
    }

    public static Tier getTier(int i) throws IllegalArgumentException {
        switch(i) {
            case 1 :
                return ONE;
            case 2 :
                return TWO;
            case 3 :
                return THREE;
            case 4 :
                return FOUR;
            case 5 :
                return FIVE;
            default :
                return null;
        }
    }

    public int toInt() {

        return toInt;

    }

    public ChatColor getColour() {

        return color;

    }
}