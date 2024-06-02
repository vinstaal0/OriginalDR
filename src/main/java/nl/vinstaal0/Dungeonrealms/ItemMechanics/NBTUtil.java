package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import net.minecraft.server.v1_8_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    /**
     * @param item the item to which you would like to add NBT data to
     * @param key the key for the NBT tag
     * @param value the value you want to the NBT tag
     *
     * @return itemstack with added nbt data
     * */
    public static ItemStack setCustomIntTag(ItemStack item, String key, int value) {

        // Cover Bukkit item to NMS ItemStack
        net.minecraft.server.v1_8_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        // Get or create the NBT tag compound
        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();

        tag.setInt(key, value);

        nmsItem.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    /**
     * @param item the itemstack for which you want to get the NBT integer
     * @param key the key for the NBT tag
     *
     * @return The integer stored in the NBT tag
     * */
    public static int getCustomIntTag(ItemStack item, String key) {

        // Cover Bukkit item to NMS ItemStack
        net.minecraft.server.v1_8_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        // Get the NBT tag compound
        NBTTagCompound tag = nmsItem.getTag();

        // Return the custom integer tag value if it exists, or 0 if it does not
        return tag != null && tag.hasKey(key) ? tag.getInt(key) : 0;
    }

}
