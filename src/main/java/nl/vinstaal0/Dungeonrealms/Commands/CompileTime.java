package nl.vinstaal0.Dungeonrealms.Commands;

import minecade.dungeonrealms.Main;
import minecade.dungeonrealms.ProfessionMechanics.ProfessionMechanics;
import minecade.dungeonrealms.database.ConnectionPool;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemSerialization;
import nl.vinstaal0.Dungeonrealms.RepairMechanics;
import nl.vinstaal0.Dungeonrealms.TierMechanics.TierMechanics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class CompileTime implements CommandExecutor {

    private static HashMap<UUID, String> savedInventory = new HashMap<UUID, String>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.isOp()) {
            if (!commandSender.getName().equalsIgnoreCase("vinstaal0")) {
                commandSender.sendMessage(ChatColor.RED + "You do not have the permission to use this command");
                return true;
            }
        }

        commandSender.sendMessage("Compile date and time: " + Main.compileTimedate);

        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        UUID uuid = player.getUniqueId();

        if (strings.length == 1) {

            if (strings[0].equalsIgnoreCase("level")) {

                ItemStack tool = player.getItemInHand();

                ProfessionMechanics.addEXP(player, tool, 900, "pickaxe");

            } else if (strings[0].equalsIgnoreCase("repair")) {

                Random r = new Random();
                int newDura = r.nextInt(1490-10) + 10;

                ItemStack returnStack = RepairMechanics.updateCustomDurability(player.getItemInHand(), newDura, RepairMechanics.getMaxDurability(TierMechanics.getTier(player.getItemInHand())));

//                player.getInventory().addItem(returnStack);

                player.sendMessage("Current durability: " + RepairMechanics.getCurrentDurability(player.getItemInHand()));

            } else if (strings[0].equalsIgnoreCase("decode")) {

                ArrayList<ItemStack> inventory = new ArrayList<>();

                String corrupt = null;
                String p_name = "vinstaal0";

                PreparedStatement pst = null;
                ResultSet rs = null;
                try {
                    pst = ConnectionPool
                            .getConnection()
                            .prepareStatement(
                                    "SELECT server_num, location, inventory, hp, food_level, level, first_login, combat_log, last_login_time, rank, align_status, align_time, toggles, pets, buddy_list, ignore_list, realm_tier, realm_title, realm_loaded, noob_player, ecash, ip, sdays_left, portal_shards, saved_gear, lost_gear, mule_inventory, achievments, online_today, ecash_storage FROM player_database WHERE p_name = '"
                                            + p_name + "'");
                    ;
                    pst.execute();
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        corrupt = rs.getString("inventory");
                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (pst != null) {
                        try {
                            pst.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }



                try {
                    inventory = ItemSerialization.deserializeInventoryAsArray(corrupt, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ArrayList<ItemStack> new_inventory = new ArrayList<>();

                for (ItemStack item : inventory) {
                    if (item.getType() != Material.GOLD_SWORD) {
                        new_inventory.add(item);
                        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
                    }
                }

            }


        }

        return true;
    }
}
