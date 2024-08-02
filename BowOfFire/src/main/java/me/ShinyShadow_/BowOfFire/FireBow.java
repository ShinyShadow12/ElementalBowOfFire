package me.ShinyShadow_.BowOfFire;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.ShinyShadow_.BowOfFire.Item.ItemListener;
import me.ShinyShadow_.BowOfFire.Item.ItemManager;
import me.ShinyShadow_.BowOfFire.commands.Commands;

public final class FireBow extends JavaPlugin {

	
	public void onEnable() {
		
		ItemManager.init();
		getCommand("givebowoffire").setExecutor(new Commands());
		
		Bukkit.getPluginManager().registerEvents(new ItemListener(this), this);
	}
	
	public void onDisable() {
		
	}
}
