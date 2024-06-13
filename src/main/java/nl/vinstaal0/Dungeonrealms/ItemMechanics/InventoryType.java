package nl.vinstaal0.Dungeonrealms.ItemMechanics;

import java.io.Serializable;

public enum InventoryType implements Serializable {

    PLAYER_INVENTORY(null),
    PLAYER_ARMOR(null),
    BANK("Bank chest"),
    E_CASH("E-Cash Storage", 54),
    MULE("Mobile storage chest"),
    SHOP(null),
    COLLECTION_BIN("Collection bin", 54);

    private final String displayname;
    private int slots;

    InventoryType(String displayname){
        this.displayname = displayname;
    }

    InventoryType (String displayname, int slots) {
        this.displayname = displayname;
        this.slots = slots;
    }

    public String displayname() {
        return this.displayname;
    }

    public int getSize() {
        return this.slots;
    }
}
