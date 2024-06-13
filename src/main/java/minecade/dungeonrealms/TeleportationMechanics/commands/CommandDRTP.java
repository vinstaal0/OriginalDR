package minecade.dungeonrealms.TeleportationMechanics.commands;

import minecade.dungeonrealms.TeleportationMechanics.TeleportationMechanics;

import nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemTracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import static nl.vinstaal0.Dungeonrealms.ItemMechanics.ItemStacks.TeleportationScrolls.*;

public class CommandDRTP implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(!(p.isOp())) { return true; }
		
		p.getInventory().addItem(ItemTracker.addSerialNumber(Cyrennica_scroll));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Dark_Oak_Tavern_scroll));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Harrison_scroll));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Deadpeaks_Mountain_Camp_scroll));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Jagged_Rocks_Tavern));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Tripoli_scroll));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Swamp_safezone_scroll));
		p.getInventory().addItem(ItemTracker.addSerialNumber(Crestguard_keep_scroll));
		return true;
	}
	
}