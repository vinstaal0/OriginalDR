package nl.vinstaal0.Dungeonrealms.ProfessionMechanics;


import nl.vinstaal0.Dungeonrealms.TierMechanics.Tier;
import nl.vinstaal0.Dungeonrealms.TierMechanics.TierMechanics;
import org.bukkit.inventory.ItemStack;

public class Profession {



    public void addEXP(ItemStack item, int EXP) {

    }

    public int getEXPNeeded(int level) {
        if (level == 1) {
            return 176; // formula doesn't work on level 1.
        }
        if (level == 100) {
            return 0; // green bar
        }
        int previous_level = level - 1;
        return (int) (Math.pow((previous_level), 2) + ((previous_level) * 20) + 150 + ((previous_level) * 4) + getEXPNeeded(previous_level));
    }

    public void levelUp(ItemStack item) {

    }

    public Tier getTier(ItemStack item) {
        return TierMechanics.getTier(item);
    }

}
