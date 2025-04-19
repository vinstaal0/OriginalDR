package nl.vinstaal0.Dungeonrealms.TierMechanics;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TierMechanics {

    /**
     * @param item the item to check the tier of
     */
    public static Tier getTier(ItemStack item) {

        Material material = item.getType();

        switch (material) {
            case WOOD_SWORD, WOOD_AXE, WOOD_SPADE, WOOD_HOE, LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS:
                return Tier.ONE;
            case STONE_SWORD, STONE_AXE, STONE_SPADE, STONE_HOE, CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS:
                return Tier.TWO;
            case IRON_SWORD, IRON_AXE, IRON_SPADE, IRON_HOE, IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS:
                return Tier.TREE;
            case DIAMOND_SWORD, DIAMOND_AXE, DIAMOND_SPADE, DIAMOND_HOE, DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS:
                return Tier.FOUR;
            case GOLD_SWORD, GOLD_AXE, GOLD_SPADE, GOLD_HOE, GOLD_HELMET, GOLD_CHESTPLATE, GOLD_LEGGINGS, GOLD_BOOTS:
                return Tier.FIVE;

            default: return Tier.ONE;
        }

    }

    public static int getTier(LivingEntity entity) {
        ItemStack[] armour = entity.getEquipment().getArmorContents();
        ItemStack weapon = entity.getEquipment().getItemInHand();

        ArrayList<ItemStack> gear = new ArrayList<>(List.of(armour));
        gear.add(weapon);

        int tier = 0;

        for (ItemStack itemStack : gear) {
            tier += getTier(itemStack).toInt();
        }

        return tier / 5;

    }

    /**
    * @param attacker the entity that is attacking
     * @param defender the entity that is being attacked
     *
     *                 Used to check if the attacker is allowed to kill the defender
    * */
    public static boolean allowed(LivingEntity attacker, LivingEntity defender) {


        int tier_attacker = getTier(attacker);
        int tier_defender = getTier(defender);

        double modifier = 1.3;

        return tier_attacker >= (tier_defender - modifier);

    }

}
