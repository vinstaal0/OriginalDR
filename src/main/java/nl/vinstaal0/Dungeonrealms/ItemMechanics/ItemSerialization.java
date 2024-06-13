package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import minecade.dungeonrealms.Hive.Hive;
import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.MoneyMechanics.MoneyMechanics;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks.Misc;
import nl.vinstaal0.Dungeonrealms.Utils;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.*;

public class ItemSerialization {

    private static final String concat = "@CONCAT@";

    /**
     * Used to serialize an itemstack list. Serialising the inventory directly is preferred
     *
     * @param itemStacks the itemstack array to be serialized
     */
    public static String serializeInventory(List<ItemStack> itemStacks, InventoryType inventoryType) throws IOException {

        ArrayList<SerializableItemStack> items = new ArrayList<>();

        for (ItemStack item : itemStacks) {

            if (item != null) {
                items.add(new SerializableItemStack(item));
            } else {
                items.add(null);
            }
        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.size());

            // Save every element in the list
            for (SerializableItemStack i : items) {
                dataOutput.writeObject(i);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IOException();
        }

    }

    public static String serializeInventory(Inventory inventory, InventoryType inventoryType) throws IOException {

        List<ItemStack> items = new ArrayList<>();

        for (ItemStack item : inventory.getContents()) {

            if (item != null) {
                items.add(item);
            } else {
                items.add(null);
            }
        }

        return serializeInventory(items, inventoryType);

    }

    /**
     * Used to convert the current players inventory to a String
     */
    public static String serializePlayerInventory(PlayerInventory inventory) throws IOException {

        String playerInventory = serializeInventory(Arrays.asList(inventory.getContents()), InventoryType.PLAYER_INVENTORY);
        String playerArmor = serializeInventory(Arrays.asList(inventory.getArmorContents()), InventoryType.PLAYER_ARMOR);

        return playerInventory + concat + playerArmor;
    }

    public static String serializeBankInventory(ArrayList<Inventory> inventories) throws IOException {

        ArrayList<ArrayList<SerializableItemStack>> rawItemCollections = new ArrayList<>();

        for (Inventory inv : inventories) {
            ArrayList<SerializableItemStack> items = new ArrayList<>();

            for (ItemStack item : inv.getContents()) {

                if (item != null) {
                    items.add(new SerializableItemStack(item));
                } else {
                    items.add(null);
                }
            }

            rawItemCollections.add(items);

        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(rawItemCollections.size());

            // Save every element in the list
            for (ArrayList<SerializableItemStack> i : rawItemCollections) {
                dataOutput.writeObject(i);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IOException();
        }
    }

    /**
     * Deserialize an Inventory String back to the corresponding inventory.
     *
     * @param data          the String of the inventory that is being serialised.
     * @param player        (optional) the owner of the inventory.
     * @param inventoryType the type of inventory being deserialized.
     * @param size          the amount of slots the inventory needs.
     * @return returns the corresponding inventory. In case of a PLAYER_INVENTORY it will return the inventory
     * of the player after returning the inventory.
     */
    public static ArrayList<Inventory> deserializeInventory(String data, Player player, InventoryType inventoryType, int size) throws IOException {

        ArrayList<Inventory> result = new ArrayList<>();

        switch (inventoryType) {

            case PLAYER_INVENTORY:

                // Check if legacy item String is stored
                if (data.contains("@item")) {
                    Hive.convertStringToInventory(player, data, null, 0);
                    Main.log.info("Legacy inventory loaded for " + player.getDisplayName());
                } else {
                    deserializePlayerInventory(data, player);
                }

                result.add(player.getInventory());

                break;

            case BANK :

                // Check if legacy bank String is stored
                if (!Base64.isBase64(data)) {

                    int slots = MoneyMechanics.bank_map.get(player.getDisplayName());
                    int level = MoneyMechanics.bank_level.get(player.getDisplayName());

                    return legacyBankDeserialization(data, slots,level);

                } else {

                    ArrayList<Inventory> bank = deserializeBank(data, player);

                    return bank;
                }

            case E_CASH:

                if (data.isEmpty() || data == null) {

                    Inventory emptyEcashInventory = Bukkit.createInventory(player, InventoryType.E_CASH.getSize(), InventoryType.E_CASH.displayname());

                    result.add(emptyEcashInventory);

                    return result;
                }

                // Default case
            case MULE: case COLLECTION_BIN: case SHOP:

                if (!Base64.isBase64(data)) {

                    result.add(Hive.convertStringToInventory(player, data, null, 0));
                    Main.log.info("Legacy inventory loaded for " + player.getDisplayName() + " of type " + inventoryType);

                } else {
                    ArrayList<ItemStack> inventoryArray = deserializeInventoryAsArray(data, player);

                    Inventory returnInventory = Bukkit.createInventory(player, size, inventoryType.displayname());

                    for (int i = 0; i < inventoryArray.size(); i++) {

                        returnInventory.setItem(i, inventoryArray.get(i));
                    }

                    result.add(returnInventory);
                }

                break;

        }
        return result;
    }


    /**
     * Deserialize the inventory string to an itemstack array.
     * @param data the serialized inventory String.
     * @param player (optional) the player who owns the inventory. May be null if no player is present.
     */
    public static ArrayList<ItemStack> deserializeInventoryAsArray(String data, Player player) throws IOException {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        ItemStack[] items = new ItemStack[dataInput.readInt()];

        Set<Integer> serialNumbers = new HashSet<>();

        // Read the serialized inventory
        for (int i = 0; i < items.length; i++) {

            SerializableItemStack sItemStack;
            try {
                sItemStack = (SerializableItemStack) dataInput.readObject();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (sItemStack != null) {

                if (sItemStack.getSerial() != 0) {
                    // Add serial number to the list
                    if (!serialNumbers.add(sItemStack.getSerial())) {
                        if (player != null) {
                            Main.getLog().warning("Item found with duplicate serialnumber in the inventory of " + player.getDisplayName());
                        } else {
                            Main.getLog().warning("Duplicate item found with serial number " + sItemStack.getSerial());
                        }
                        continue;
                    }
                }
                items[i] = sItemStack.getBukkitStack();
            }
        }

        dataInput.close();

        ArrayList<ItemStack> returnArray = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {

            if (items[i] != null) {

                returnArray.add(i, items[i]);

            } else {
                // Adding null to fill the array and prevent an IndexOutOfBoundsException
                returnArray.add(i, null);
            }
        }

        return returnArray;
    }

    /**
     * Used to convert a player-inventory stored in a String back into the players inventory.
     * This will overwrite the current players inventory.
     */
    private static void deserializePlayerInventory(String data, Player player) throws IOException {

        String playerInventory;
        String playerArmor;

        if (data.contains(concat)) {
            playerInventory = data.split(concat)[0];
            playerArmor = data.split(concat)[1];
        } else {
            // If concat is not present, the entire data will be the players inventory.
            playerInventory = data;
            playerArmor = null;
        }

        // Convert the string back to inventory items
        ArrayList<ItemStack> inventoryArray = deserializeInventoryAsArray(playerInventory, player);

        // Get the players inventory
        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        // Set the items in the inventory
        for (int i = 0; i < inventoryArray.size(); i++) {

            if (inventoryArray.get(i) != null) {
                inventory.setItem(i, inventoryArray.get(i));
            }
        }

        if (playerArmor != null) {
            // Convert the string back to armor items
            ArrayList<ItemStack> armorArray = deserializeInventoryAsArray(playerArmor, player);

            if (armorArray.get(0) != null) {
                inventory.setHelmet(armorArray.get(3));
            }

            if (armorArray.get(1) != null) {
                inventory.setChestplate(armorArray.get(2));
            }

            if (armorArray.get(2) != null) {
                inventory.setLeggings(armorArray.get(1));
            }

            if (armorArray.get(3) != null) {
                inventory.setBoots(armorArray.get(0));
            }
        }
    }

    private static ArrayList<Inventory> deserializeBank(String data, Player player) throws IOException {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

        int size = dataInput.readInt();
        ArrayList<Inventory> returnInventories = new ArrayList<>();

        Set<Integer> serialNumbers = new HashSet<>();

        // Read the serialized inventory list
        for (int i = 0; i < size; i++) {

            // Create a list of serializable ItemStacks
            ArrayList<SerializableItemStack> sInventory;

            try {

                // Try reading the data
                sInventory = (ArrayList<SerializableItemStack>) dataInput.readObject();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


            if (sInventory != null) {

                // Bank Chest (1/1)

                String pageName = "Bank Chest (" + (i + 1) + "/" + size + ")";

                // Create a new inventory to put the items into
                Inventory inventory = Bukkit.createInventory(player, sInventory.size(), pageName);

                // Convert the sItemstacks back into normal item
                for (int h = 0 ; h < sInventory.size() ; h++) {

                    if (sInventory.get(h) != null) {

                        SerializableItemStack sItemStack = sInventory.get(h);

                        // Check the serialnumber of the item
                        if (sItemStack.getSerial() != 0) {
                            // Add serial number to the list
                            if (!serialNumbers.add(sItemStack.getSerial())) {
                                if (player != null) {
                                    Main.getLog().warning("Item found with duplicate serialnumber in the inventory of " + player.getDisplayName());
                                } else {
                                    Main.getLog().warning("Duplicate item found with serial number " + sItemStack.getSerial());
                                }
                                continue;
                            }
                        }

                        inventory.setItem(h, sItemStack.getBukkitStack());
                    }

                }

                returnInventories.add(inventory);

            }

        }

        dataInput.close();

        return returnInventories;

    }

    /**
     * Method to load bank inventories from the Minecade era
     * @param data the bank String as stored on the database
     * @param money the amount of money the player has
     * @param level the level of the bank chest
     * @return returns an array of Inventories to be opened or stored.
     * */
    @Deprecated
    public static ArrayList<Inventory> legacyBankDeserialization(String data, int money, int level) {

        String bank_content = null;
        int slots = MoneyMechanics.getBankSlots(level);

        try {
            data = data.replace('?','&');
            data = data.replace(ChatColor.COLOR_CHAR, '&');
            bank_content = ChatColor.translateAlternateColorCodes('&', data);
        } catch (NullPointerException ignored) {
            // Bank is empty, just ignore
        }

        if(money < 0) {
            // Negative balance?
            money = 0;
        }

        if(level == -2) {
            // We messed up badly, calculate level.
            level = 0;
            while(bank_content != null && bank_content.contains("@item@") && (bank_content.split("@item@").length > slots - 1) && level < 17) {
                level++;
            }
        }

        ArrayList<Inventory> bank_pages = new ArrayList<Inventory>();
        int max_slots = MoneyMechanics.getBankSlots(level);

        if(bank_content != null && !(bank_content.equalsIgnoreCase("null"))) {
            int total_pages = 1;
            int current_page = 1;
            if(max_slots > 54 && !(bank_content.contains("@page_break@"))) {
                bank_content += "@page_break@";
            }

            if(bank_content.contains("@page_break@")) {
                // They need to have multiple pages if it reaches this point.
                total_pages = bank_content.split("@page_break@").length;
                int last_page_slots = max_slots - ((total_pages - 1) * 54); // The amount of slots to allocate to the last page. All other pages = 54
                // storage, 63 for extra row.

                for(String s_bank_content : bank_content.split("@page_break@")) {
                    if(s_bank_content.contains("@item@")) {
                        int page_slots = 63; // All pages are 63 slots except the last page.
                        if(current_page == total_pages && total_pages > 1) {
                            page_slots = last_page_slots + 9; // Add 9, need the last row for the 'back' button.
                        }
                        Inventory page = Hive.convertStringToInventory(null, s_bank_content, "Bank Chest (" + current_page + "/" + total_pages + ")", page_slots);

                        if(page_slots == 63) {
                            int start_slot = 54 - 1;
                            int end_slot = 63 - 1;
                            // boolean empty = true;

                            start_slot--; // Offset by 1 since we add first.
                            while(start_slot < end_slot) {
                                start_slot++;
                                if(page.getItem(start_slot) != null && page.getItem(start_slot).getType() != Material.AIR) {
                                    // empty = false;
                                    break;
                                }
                            }

                            // if(empty){
                            if(current_page == 1) {
                                page.setItem(54, Misc.divider);
                            } else {
                                page.setItem(54, MoneyMechanics.generateArrowButton("previous", current_page, total_pages));
                            }
                            page.setItem(55, Misc.divider);
                            page.setItem(56, Misc.divider);
                            page.setItem(57, Misc.divider);
                            page.setItem(58, Misc.divider);
                            page.setItem(59, Misc.divider);
                            page.setItem(60, Misc.divider);
                            page.setItem(61, Misc.divider);
                            if(current_page == total_pages) { // Last page, no 'next'
                                page.setItem(62, Misc.divider);
                            } else {
                                page.setItem(62, MoneyMechanics.generateArrowButton("next", current_page, total_pages));
                            }
                        }

                        bank_pages.add(page);
                        current_page++;
                    }
                }
            } else if(!(bank_content.contains("@page_break@"))) {
                Inventory inv_page = Hive.convertStringToInventory(null, bank_content, "Bank Chest (1/1)", slots);
                bank_pages.add(inv_page);
            }
        }

        else if(bank_pages.isEmpty()) {
            bank_pages.add(Bukkit.createInventory(null, slots, "Bank Chest (1/1)"));
        }

        return bank_pages;

    }
}