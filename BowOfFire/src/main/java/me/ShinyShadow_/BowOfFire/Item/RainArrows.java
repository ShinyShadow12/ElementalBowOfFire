package me.ShinyShadow_.BowOfFire.Item;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RainArrows {
	private Location test;
	private double radius = 10;
		public RainArrows(double Offset, Entity specialArrow, Player player, JavaPlugin plugin) {
			

			Random r = new Random();
		    double randomRadius = r.nextDouble() * radius;
		    double theta =  Math.toRadians(r.nextDouble() * 360);
		    double phi = Math.toRadians(r.nextDouble() * 180);
		 
		    double x = randomRadius * Math.cos(theta) * Math.sin(phi);
		    double y = randomRadius * Math.sin(theta) * Math.cos(phi);
		    double z = randomRadius * Math.cos(phi);
		    Location newLoc = specialArrow.getLocation().add(x, 15, z);
		    Entity arrow = player.getWorld().spawn(newLoc, Arrow.class);

	        new BukkitRunnable() {
	                 @Override
	                 public void run() {                	 
	                	 arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation(), 2, 0, 0, 0, 0);
	                	 arrow.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 2, 0.1, 0.1, 0.1, 1, new DustOptions(Color.ORANGE, 1));
	                	 arrow.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 2, 0.1, 0.1, 0.1, 1, new DustOptions(Color.RED, 1));
	                
	                	 if(arrow.isOnGround() || arrow.isDead()) {
	                		 arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation().add(0, 0.5, 0), 50, 0.1, 0.1, 0.1, 0.2);
	                		 arrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, arrow.getLocation().add(0, 0.5, 0), 60, 0.1, 0.1, 0.1, 0.2);
	                		 arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.4F, 1.8F);   
	                		 arrow.getWorld().getBlockAt(arrow.getLocation()).setType(Material.FIRE);
	                		 arrow.remove();
	                		 this.cancel();
	                	 }
	                 }
	        	 }.runTaskTimer(plugin, 0L, 1L);
	         }
		}

