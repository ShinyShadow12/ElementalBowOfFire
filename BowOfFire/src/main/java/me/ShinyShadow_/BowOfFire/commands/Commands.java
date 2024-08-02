package me.ShinyShadow_.BowOfFire.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ShinyShadow_.BowOfFire.Item.ItemManager;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
		// TODO Auto-generated method stub
		
		if(sender instanceof Player) {	
			Player player = (Player) sender;
			player.getInventory().addItem(ItemManager.Fire_Bow);
			player.sendMessage("HUEVO");
		}
		return true;
	}

}
