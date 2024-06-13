package nl.vinstaal0.Dungeonrealms.Monstermechanics;

import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.MonsterMechanics.MonsterMechanics;
import nl.vinstaal0.Dungeonrealms.Monstermechanics.Commands.CommandCreateSpawnerWand;
import nl.vinstaal0.Dungeonrealms.Monstermechanics.Converstation.*;
import nl.vinstaal0.Dungeonrealms.Tier;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class SpawnerMechanics implements Listener {

    private final Logger log = Logger.getLogger("Minecraft");

    private static final HashMap<Player, Spawner> midCreationSpawners = new HashMap<>(); //TODO remove static

    private static final ArrayList<Spawner> spawnerArrayList = new ArrayList<>();

    /**
     * Triggered on plugin enable
     */
    public void onEnable() {
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);

        Main.plugin.getCommand("createspawnerwand").setExecutor(new CommandCreateSpawnerWand());

        log.info("[SpawnerMechanics] has been enabled.");
    }

    /**
     * Triggered on plugin disable or server shutdown
     */
    public void onDisable() {

        log.info("[SpawnerMechanics] has been disabled.");
    }

    /**
     * Event-handler to handle when the players right-click the spawner wand to place a spawner
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player creator = event.getPlayer();
        ItemStack itemInHand = creator.getItemInHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        if (itemInHand.getType() == Material.BLAZE_ROD) {
            if (!itemMeta.hasDisplayName()) {
                return;
            }

            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Spawnerwand")) {
                Location location = event.getClickedBlock().getLocation().add(0,1,0);

                Material materialClickedOn = location.getBlock().getType();

                if (event.getClickedBlock().getType() != Material.MOB_SPAWNER) {

                    location.getBlock().setType(Material.MOB_SPAWNER);
                }

                Spawner spawner;

                if (midCreationSpawners.containsKey(creator)) {
                    spawner = midCreationSpawners.get(creator);

                } else {
                    spawner = new Spawner(location, creator);
                }

                openMobTypeSelectionGUI(creator);
                midCreationSpawners.put(creator, spawner);
            }

        }

    }

    @EventHandler
    private void onInventoryCloseEvent(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player creator = (Player) event.getPlayer();

        if (inventory.getName().equals("select a mob type")) {

            World world = event.getPlayer().getWorld();
            Spawner spawner = midCreationSpawners.get(creator);

            Block block = world.getBlockAt(spawner.getLoc());
            block.setType(Material.AIR);

            midCreationSpawners.remove(creator);
            creator.sendMessage(ChatColor.RED + "Placement of new mob spawner location cancelled.");
        }
    }

    /**
     * Used to check and handle when the player clicks in the inventory menu's for the spawner creation
     * @param event InventoryClickEvent
     */
    @EventHandler
    private void onMenuClick(InventoryClickEvent event) {
        Player creator = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) {
            return;
        }

        if (clickedInventory.getTitle().equalsIgnoreCase("select a mob type") ||
        clickedInventory.getTitle().equalsIgnoreCase("mob options") ||
        clickedInventory.getTitle().equalsIgnoreCase("select a mob type")) {

            Spawner spawner = midCreationSpawners.get(creator);

            SpawnerData spawnerData;

            if (spawner.getSpawnerDataMidCreation() == null) {
                spawnerData = new SpawnerData();
            } else {
                spawnerData = spawner.getSpawnerDataMidCreation();
            }

            if (clickedInventory.getTitle().equals("Select a mob type")) {
                event.setCancelled(true);

                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                    return;
                }

                MobType mobType = MobType.valueOf(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));

                String legacyType = mobType.legacyType;

                creator.sendMessage("Mob selected: " + mobType);
                spawnerData.setMobType(legacyType);
                spawner.addSpawnerData(spawnerData);

                creator.closeInventory();

                openOptionSelectionGUI(creator);

            }

            else if (clickedInventory.getTitle().equals("Mob options")) { // TODO enum
                event.setCancelled(true);

                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                    return;
                }

                String selectedOption = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

                if (selectedOption.equalsIgnoreCase("custom name")) {
                    creator.closeInventory();

                    ConversationFactory conversationFactory;

                    conversationFactory = new ConversationFactory(Main.getPlugin())
                            .withFirstPrompt(new CustomName())
                            .withEscapeSequence("/cancel")
                            .thatExcludesNonPlayersWithMessage("You must be a player to use this command.")
                            .addConversationAbandonedListener(new ConversationAbandonedListener() {
                                @Override
                                public void conversationAbandoned(ConversationAbandonedEvent event) {
                                    ConversationCanceller canceller = event.getCanceller();
                                    ConversationContext context = event.getContext();
                                    Player player = (Player) context.getForWhom();

                                    if (event.gracefulExit()) {
                                        player.sendMessage(ChatColor.GREEN + "Conversation ended successfully.");

                                        Spawner spawner = midCreationSpawners.get(player);
                                        SpawnerData spawnerData = spawner.getSpawnerDataMidCreation();

                                        spawnerData.setCustomName((String) context.getSessionData("customName"));

                                        spawner.addSpawnerData(spawnerData);

                                        openOptionSelectionGUI(player);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Conversation cancelled.");
                                        openOptionSelectionGUI(player);
                                    }

                                    if (canceller != null) {
                                        player.sendMessage(ChatColor.YELLOW + "Reason: " + canceller.getClass().getSimpleName());
                                    }

                                    // Clean up any session data if necessary
                                    context.getAllSessionData().clear();
                                }
                            });

                    Conversation conversation = conversationFactory.buildConversation(creator);
                    conversation.begin();

                }

                else if (selectedOption.equalsIgnoreCase("elite")) {

                    spawnerData.setElite(!spawnerData.isElite());
                    creator.sendMessage("Elite = " + spawnerData.isElite());

                }

                else if (selectedOption.equalsIgnoreCase("spawn range")) {
                    creator.closeInventory();

                    ConversationFactory conversationFactory;

                    conversationFactory = new ConversationFactory(Main.getPlugin())
                            .withFirstPrompt(new SpawnRange())
                            .withEscapeSequence("/cancel")
                            .thatExcludesNonPlayersWithMessage("You must be a player to use this command.")
                            .addConversationAbandonedListener(new ConversationAbandonedListener() {
                                @Override
                                public void conversationAbandoned(ConversationAbandonedEvent event) {
                                    ConversationCanceller canceller = event.getCanceller();
                                    ConversationContext context = event.getContext();
                                    Player player = (Player) context.getForWhom();

                                    if (event.gracefulExit()) {
                                        player.sendMessage(ChatColor.GREEN + "Conversation ended successfully.");

                                        Spawner spawner = midCreationSpawners.get(player);

                                        int[] range = (int[]) context.getSessionData("spawnRange");

                                        spawner.setLowerRange(range[0]);
                                        spawner.setUpperRange(range[1]);

                                        openOptionSelectionGUI(player);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Conversation cancelled.");
                                        openOptionSelectionGUI(player);
                                    }

                                    if (canceller != null) {
                                        player.sendMessage(ChatColor.YELLOW + "Reason: " + canceller.getClass().getSimpleName());
                                    }

                                    // Clean up any session data if necessary
                                    context.getAllSessionData().clear();
                                }
                            });

                    Conversation conversation = conversationFactory.buildConversation(creator);
                    conversation.begin();

                }

                else if (selectedOption.equalsIgnoreCase("spawn interval")) {
                    creator.closeInventory();

                    ConversationFactory conversationFactory;

                    conversationFactory = new ConversationFactory(Main.getPlugin())
                            .withFirstPrompt(new SpawnInterval())
                            .withEscapeSequence("/cancel")
                            .thatExcludesNonPlayersWithMessage("You must be a player to use this command.")
                            .addConversationAbandonedListener(new ConversationAbandonedListener() {
                                @Override
                                public void conversationAbandoned(ConversationAbandonedEvent event) {
                                    ConversationCanceller canceller = event.getCanceller();
                                    ConversationContext context = event.getContext();
                                    Player player = (Player) context.getForWhom();

                                    if (event.gracefulExit()) {
                                        player.sendMessage(ChatColor.GREEN + "Conversation ended successfully.");

                                        Spawner spawner = midCreationSpawners.get(player);

                                        spawner.setSpawnInterval((int) context.getSessionData("spawnInterval"));

                                        openOptionSelectionGUI(player);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Conversation cancelled.");
                                        openOptionSelectionGUI(player);
                                    }

                                    if (canceller != null) {
                                        player.sendMessage(ChatColor.YELLOW + "Reason: " + canceller.getClass().getSimpleName());
                                    }

                                    // Clean up any session data if necessary
                                    context.getAllSessionData().clear();
                                }
                            });

                    Conversation conversation = conversationFactory.buildConversation(creator);
                    conversation.begin();
                }

                else if (selectedOption.equalsIgnoreCase("Mob level difference")) {
                    creator.closeInventory();

                    ConversationFactory conversationFactory;

                    conversationFactory = new ConversationFactory(Main.getPlugin())
                            .withFirstPrompt(new LevelDifference())
                            .withEscapeSequence("/cancel")
                            .thatExcludesNonPlayersWithMessage("You must be a player to use this command.")
                            .addConversationAbandonedListener(new ConversationAbandonedListener() {
                                @Override
                                public void conversationAbandoned(ConversationAbandonedEvent event) {
                                    ConversationCanceller canceller = event.getCanceller();
                                    ConversationContext context = event.getContext();
                                    Player player = (Player) context.getForWhom();

                                    if (event.gracefulExit()) {
                                        player.sendMessage(ChatColor.GREEN + "Conversation ended successfully.");

                                        Spawner spawner = midCreationSpawners.get(player);

                                        spawner.setLevelRange((int) context.getSessionData("levelDifference"));

                                        openOptionSelectionGUI(player);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Conversation cancelled.");
                                        openOptionSelectionGUI(player);
                                    }

                                    if (canceller != null) {
                                        player.sendMessage(ChatColor.YELLOW + "Reason: " + canceller.getClass().getSimpleName());
                                    }

                                    // Clean up any session data if necessary
                                    context.getAllSessionData().clear();
                                }
                            });

                    Conversation conversation = conversationFactory.buildConversation(creator);
                    conversation.begin();

                }

                else if (selectedOption.equalsIgnoreCase("Amount")) {
                    creator.closeInventory();

                    ConversationFactory conversationFactory;

                    conversationFactory = new ConversationFactory(Main.getPlugin())
                            .withFirstPrompt(new Amount())
                            .withEscapeSequence("/cancel")
                            .thatExcludesNonPlayersWithMessage("You must be a player to use this command.")
                            .addConversationAbandonedListener(new ConversationAbandonedListener() {
                                @Override
                                public void conversationAbandoned(ConversationAbandonedEvent event) {
                                    ConversationCanceller canceller = event.getCanceller();
                                    ConversationContext context = event.getContext();
                                    Player player = (Player) context.getForWhom();

                                    if (event.gracefulExit()) {
                                        player.sendMessage(ChatColor.GREEN + "Conversation ended successfully.");

                                        Spawner spawner = midCreationSpawners.get(player);
                                        SpawnerData spawnerData = spawner.getSpawnerDataMidCreation();

                                        spawnerData.setAmount((int) context.getSessionData("amount"));

                                        openOptionSelectionGUI(player);
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Conversation cancelled.");
                                        openOptionSelectionGUI(player);
                                    }

                                    if (canceller != null) {
                                        player.sendMessage(ChatColor.YELLOW + "Reason: " + canceller.getClass().getSimpleName());
                                    }

                                    // Clean up any session data if necessary
                                    context.getAllSessionData().clear();
                                }
                            });

                    Conversation conversation = conversationFactory.buildConversation(creator);
                    conversation.begin();

                }

                else if (selectedOption.equalsIgnoreCase("tier 1")) {
                    creator.sendMessage("Tier 1 selected");
                    spawnerData.setTier(Tier.ONE);
                    spawner.addSpawnerData(spawnerData);

                }

                else if (selectedOption.equalsIgnoreCase("tier 2")) {
                    creator.sendMessage("Tier 2 selected");
                    spawnerData.setTier(Tier.TWO);
                    spawner.addSpawnerData(spawnerData);
                }

                else if (selectedOption.equalsIgnoreCase("tier 3")) {
                    creator.sendMessage("Tier 3 selected");
                    spawnerData.setTier(Tier.TREE);
                    spawner.addSpawnerData(spawnerData);
                }

                else if (selectedOption.equalsIgnoreCase("tier 4")) {
                    creator.sendMessage("Tier 4 selected");
                    spawnerData.setTier(Tier.FOUR);
                    spawner.addSpawnerData(spawnerData);
                }

                else if (selectedOption.equalsIgnoreCase("tier 5")) {
                    creator.sendMessage("Tier 5 selected");
                    spawnerData.setTier(Tier.FIVE);
                    spawner.addSpawnerData(spawnerData);
                }

                else if (selectedOption.equalsIgnoreCase("add another mob")) {
                    spawner.addSpawnerData(spawnerData);
                    spawner.finishSpawnerData();
                    spawnerData = null;
                    creator.closeInventory();

                    openMobTypeSelectionGUI(creator);
                }

                else if (selectedOption.equalsIgnoreCase("accept")) {
                    spawner.addSpawnerData(spawnerData);
                    spawner.finishSpawnerData();
                    spawnerData = null;
                    creator.closeInventory();

                    spawnerArrayList.add(spawner);

                    legacyAddSpawner();

                    // TODO add some final checks and make it store it in the file
                }

                spawner.addSpawnerData(spawnerData);

        }
        }

    }

    /**
     * Used to store the mob spawner data in the new file format
     */
    private static void saveMobSpawnerData() {
        for (Spawner spawner : spawnerArrayList) {

            // TODO update to replace the legacy way of storing spawner data

            String s = spawner.toString();

            try {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("plugins/MonsterMechanics/global_mob_spawns.dat", false));

                dos.writeBytes(s + "\n");
                dos.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Used to store the Spawner objects to the old system
     * */
    @Deprecated
    private static void legacyAddSpawner() {

        for (Spawner spawner : spawnerArrayList) {
            Location location = spawner.getLoc();

            StringBuilder sb = new StringBuilder();

            sb.append("@");

            for (SpawnerData data : spawner.spawnerData) {
                sb.append(data.toString());
            }

            sb.append("@");
            sb.append(spawner.getSpawnInterval());

            sb.append("#");
            sb.append(spawner.getLowerRange());
            sb.append("-");
            sb.append(spawner.getUpperRange());

            sb.append("$");
            sb.append(spawner.getLevelRange());

            sb.append("%");

            MonsterMechanics.mob_spawns.put(location, sb.toString());
        }
    }


    /**
     * Opens te mob type selection menu for the specific player
     * @param player the player for which the GUI needs to open
     */
    private static void openMobTypeSelectionGUI(Player player) {
        Inventory mobSelectionMenu = Bukkit.createInventory(null, 27, "Select a mob type");

        int count = 0;

        for (MobType mobType : MobType.values()) {

            if (mobType.spawnable) {
                mobSelectionMenu.setItem(count, mobType.createItemStack(mobType.name()));
                count++;
            }
        }

        player.openInventory(mobSelectionMenu);
    }

    /**
     * Opens te mob option slection windows for the specified player to let them select the specific spawner
     * related options
     * @param player the player for which the GUI needs to open
     */
    private static void openOptionSelectionGUI(Player player) {
        Inventory optionSelectionGUI = Bukkit.createInventory(null, 18, "Mob options");

        optionSelectionGUI.setItem(0, customMenuItem(Material.NAME_TAG, "Custom name"));
        optionSelectionGUI.setItem(1, customMenuItem(Material.ENCHANTMENT_TABLE, "Elite"));
        optionSelectionGUI.setItem(2, customMenuItem(Material.ARROW, "Spawn range"));
        optionSelectionGUI.setItem(3, customMenuItem(Material.WATCH, "Spawn interval"));
        optionSelectionGUI.setItem(4, customMenuItem(Material.COMPASS, "Mob level difference"));
        optionSelectionGUI.setItem(5, customMenuItem(Material.REDSTONE_COMPARATOR, "Amount"));
        optionSelectionGUI.setItem(9, customMenuItem(Material.LEATHER_CHESTPLATE, "Tier 1"));
        optionSelectionGUI.setItem(10, customMenuItem(Material.CHAINMAIL_CHESTPLATE, "Tier 2"));
        optionSelectionGUI.setItem(11, customMenuItem(Material.IRON_CHESTPLATE, "Tier 3"));
        optionSelectionGUI.setItem(12, customMenuItem(Material.DIAMOND_CHESTPLATE, "Tier 4"));
        optionSelectionGUI.setItem(13, customMenuItem(Material.GOLD_CHESTPLATE, "Tier 5"));
        optionSelectionGUI.setItem(8, customMenuItem(Material.SKULL_ITEM, "add another mob"));

        ItemStack acceptButton = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta itemMeta = acceptButton.getItemMeta();
        itemMeta.setDisplayName("accept");
        acceptButton.setItemMeta(itemMeta);

        optionSelectionGUI.setItem(17, acceptButton);

        player.openInventory(optionSelectionGUI);
    }

    /**
     *
     * @param type the type of mob-head that needs to be created to be placed inside a menu
     * @param displayName the display name of the menu item
     * @return ItemStack
     */
    private static ItemStack createMobHead(MobType type, String displayName) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, type.data);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    /**
     *
     * @param displayName the display name of the menu item
     * @param ownerName the name of the player of which the player-head needs to be created
     * @return ItemStack in the form a player-head with the specific skin
     */
    private static ItemStack createMobHead(String ownerName, String displayName) {
        ItemStack itemStack = createMobHead(MobType.PLAYER, displayName);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(ownerName);

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }


    /**
     *
     * @param material the material used for the menu item
     * @param displayName the display name of the menu item
     * @return ItemStack menu item
     */
    private static ItemStack customMenuItem(Material material, String displayName) {
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
