package me.ShinyShadow_.BowOfFire.Item;

import java.awt.Color;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemManager {
	
		public static ItemStack Fire_Bow; 
		public static void init() {
			
			createFire_Bow();
			//getCommand("givefirebow").setExecutor(new fireBowCommand());
		}
		private static void createFire_Bow() {
			
			ItemStack item = new ItemStack(Material.BOW, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD +  "Elemental Bow: Fire");
			meta.setLore(Arrays.asList("Normal attack: Hellish Fire.",
					"Shoot a beam of hellish fire that will burn",
					"and damage your enemies in an area.",
				    "Additionally, shoot 2 extra fire",
				    "arrows at each side of the beam",
					"",
					"Special Attack: Fire Rain.",
					"Shoot a fireball that will summon a rain",
					"of fire on impact, burning everything",
				    "and reducing to ashes anything in the area of effect."));
			meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			Fire_Bow = item;
		}
}