package minecade.dungeonrealms.TeleportationMechanics.commands;

import minecade.dungeonrealms.TeleportationMechanics.TeleportationMechanics;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import static nl.vinstaal0.Dungeonrealms.ItemMechanics.TeleportationScrolls.*;

public class CommandDRTP implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(!(p.isOp())) { return true; }
		
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Cyrennica_scroll)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Dark_Oak_Tavern_scroll)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Harrison_scroll)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Deadpeaks_Mountain_Camp_scroll)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Jagged_Rocks_Tavern)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Tripoli_scroll)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Swamp_safezone_scroll)));
		p.getInventory().addItem(CraftItemStack.asCraftCopy(TeleportationMechanics.makeUnstackable(Crestguard_keep_scroll)));
		return true;
	}
	
}