package me.vilsol.betanpc.items.teleportmenu;

import java.util.Arrays;

import me.vilsol.menuengine.engine.MenuItem;
import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemTracker;
import org.bukkit.event.inventory.ClickType;
import me.vilsol.menuengine.utils.Builder;
import minecade.dungeonrealms.TeleportationMechanics.TeleportationMechanics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks.TeleportationScrolls.Tripoli_scroll;

public class TeleportToTripoli implements MenuItem {

	@Override
	public void registerItem() {
		MenuItem.items.put(this.getClass(), this);
	}

	@Override
	public void execute(Player plr, ClickType click) {
		ItemStack tp = ItemTracker.addSerialNumber(Tripoli_scroll);
		plr.getInventory().addItem(tp);
	}

	@Override
	public ItemStack getItem() {
		return new Builder(Material.BOOK).setName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Teleport: " + ChatColor.WHITE + "Tripoli").setLore(Arrays.asList(ChatColor.GRAY + "Spawn a teleportation book to " + ChatColor.BOLD + "Tripoli")).getItem();
	}

}