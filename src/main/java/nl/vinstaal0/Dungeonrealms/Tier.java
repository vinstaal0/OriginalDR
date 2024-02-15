package nl.vinstaal0.Dungeonrealms;

public enum Tier {

    ONE(1),
    TWO(2),
    TREE(3),
    FOUR(4),
    FIVE(5);

    private final int toInt;

    Tier(int toInt) {
        this.toInt = toInt;
    }

    public static Tier getTier(String string) throws IllegalArgumentException {
        switch(string) {
            case "1" :
                return ONE;
            case "2" :
                return TWO;
            case "3" :
                return TREE;
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
                return TREE;
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
}