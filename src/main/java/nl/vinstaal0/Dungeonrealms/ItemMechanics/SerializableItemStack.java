package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import net.minecraft.server.v1_8_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class SerializableItemStack implements Serializable {

    private static final transient long serialisationUID = 13278234566L;

    private final ItemStack bukkitStack;

    // Custom fields
    private int serial = 0;

    /**
     *  Convert a Bukkit ItemStack to a serializable object with the custom NBT data included
     * */
    public SerializableItemStack(ItemStack bukkitItemStack) {
        this.bukkitStack = bukkitItemStack;
        net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(bukkitItemStack);

        if (nmsStack != null) {
            NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();

            serial = compound.getInt("serial");
        }
    }

    /**
     * Convert the object to a Bukkit ItemStack
     * @return ItemStack
     * */
    public ItemStack getBukkitStack() {

        net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(bukkitStack);

        if (nmsStack != null) {
            NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
            compound.setInt("serial", serial);

            nmsStack.setTag(compound);
        }

        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public int getSerial() {
        return this.serial;
    }

}
