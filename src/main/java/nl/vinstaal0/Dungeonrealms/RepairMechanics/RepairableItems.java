package nl.vinstaal0.Dungeonrealms.RepairMechanics;

import minecade.dungeonrealms.ProfessionMechanics.ProfessionMechanics;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RepairableItems {

    // Method to check if the item can have durability
    public static boolean canHaveDurability(ItemStack item) {
        if (item == null) {
            return false;
        }

        Material material = item.getType();

        switch (material) {
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
//            case NETHERITE_SWORD:
            case WOOD_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLD_PICKAXE:
            case DIAMOND_PICKAXE:
//            case NETHERITE_PICKAXE:
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLD_AXE:
            case DIAMOND_AXE:
//            case NETHERITE_AXE:
            case WOOD_SPADE:
            case STONE_SPADE:
            case IRON_SPADE:
            case GOLD_SPADE:
            case DIAMOND_SPADE:
//            case NETHERITE_SHOVEL:
            case WOOD_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLD_HOE:
            case DIAMOND_HOE:
//            case NETHERITE_HOE:
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case GOLD_HELMET:
            case GOLD_CHESTPLATE:
            case GOLD_LEGGINGS:
            case GOLD_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
//            case NETHERITE_HELMET:
//            case NETHERITE_CHESTPLATE:
//            case NETHERITE_LEGGINGS:
//            case NETHERITE_BOOTS:
            case BOW:
//            case CROSSBOW:
//            case TRIDENT:
            case FISHING_ROD:
            case FLINT_AND_STEEL:
            case SHEARS:
//            case SHIELD:
//            case ELYTRA:
                return true;
            default:

                return ProfessionMechanics.isSkillItem(item);
        }
    }

}
