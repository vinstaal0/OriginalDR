package nl.vinstaal0.Dungeonrealms.Monstermechanics;

import nl.vinstaal0.Dungeonrealms.Tier;

public class SpawnerData {

    // @zombie:1-1,skeleton:1-2,skeleton:2-3@30#1-5$

    //TODO add defaults
    private String mobType;
    private String customName;
    public boolean elite;
    private Tier tier;
    private int amount = 1;


    public SpawnerData() {

    }
    public SpawnerData(String mobType) {
        this.mobType = mobType;
    }

    public SpawnerData(String mobType, boolean elite, Tier tier, int amount) {
        this.mobType = mobType;
        this.elite = elite;
        this.tier = tier;
        this.amount = amount;
    }

    public SpawnerData(String mobType, String customName, boolean elite, Tier tier, int amount) {
        this.mobType = mobType;
        this.customName = customName;
        this.elite = elite;
        this.tier = tier;
        this.amount = amount;
    }

    public void setMobType(String mobType) {
        this.mobType = mobType;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public boolean isElite() {
        return elite;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Deprecated
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(mobType);

        if (elite) sb.append("*");

        if (customName != null) sb.append("(" + customName + ")");

        sb.append(":");

        if (tier != null) {
            sb.append(tier.toInt());
        } else {
            sb.append("1");
        }

        sb.append("-");
        sb.append(amount);
        sb.append(",");

        return sb.toString();
    }
}
