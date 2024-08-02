package me.ShinyShadow_.BowOfFire.Item;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

//Amber's ult :3
public class SpecialAttack {

	private Entity specialArrow;
	
	private double areaTimer = 15D;
	private double circleSize = 4D;
	
	private double spawnDelay = 0D;
	
	private Location eyeLoc;
	private Location areaLoc;
	private Location particleLoc;
	private Location randomAreaLoc;
	private Location rainSpawn;
	
		public SpecialAttack(Player player, JavaPlugin plugin) {			
			eyeLoc = player.getEyeLocation();
			specialArrow = player.getWorld().spawn(eyeLoc, Arrow.class);
        	Vector direction = player.getEyeLocation().getDirection();
        	Vector diagonalDirection = direction.add(new Vector(0, 0.1, 0).normalize().multiply(0.5D));
        	specialArrow.setVelocity(diagonalDirection);
        	 new BukkitRunnable() {
                 @Override
                 public void run() {
                     if (specialArrow.isDead() || specialArrow.isOnGround()) {
                    	 specialArrow.getWorld().playSound(specialArrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.6F, 0.8F);   
                    	 specialArrow.getWorld().spawnParticle(Particle.FLAME, specialArrow.getLocation(), 200, 2, 1, 2, 0.5);
                         specialArrow.getWorld().spawnParticle(Particle.SMOKE_LARGE, specialArrow.getLocation(), 200, 2, 1, 2, 0.5);
                    	 areaLoc = specialArrow.getLocation();
                    	 rainSpawn = specialArrow.getLocation();
                    	 circle(player, areaLoc, plugin);
                    	 this.cancel();
                         return;
                     }
                     specialArrow.getWorld().spawnParticle(Particle.FLAME, specialArrow.getLocation(), 20, 0.8, 0.5, 0.5, 0.1);
                     specialArrow.getWorld().spawnParticle(Particle.SMOKE_LARGE, specialArrow.getLocation(), 20, 0.5, 0.5, 0.8, 0.1);
                 }
             }.runTaskTimer(plugin, 0L, 1L);		
}
		
			public void circle(Player player, Location rainLoc, JavaPlugin plugin) {
	        	 new BukkitRunnable() {
	                 @Override
	                 public void run() {
	                	 
	                 areaTimer -= 0.09D;
	                 if(areaTimer <= 0) {
	                	 this.cancel();
	                 }
	                 if(circleSize <= 10) {
	                 circleSize += 0.06D;
	                 }
	                 //player.sendMessage("s: " + circleSize);
				  for (int d = 0; d <= 160; d += 1) {

					   particleLoc = new Location(player.getWorld(), rainLoc.getX(), rainLoc.getY(),rainLoc.getZ());
				       particleLoc.setX(rainLoc.getX() + Math.cos(d) * circleSize);
				       particleLoc.setZ(rainLoc.getZ() + Math.sin(d) * circleSize);
				       player.getWorld().spawnParticle(Particle.FLAME, particleLoc.add(0, 0.3, 0), 0, 0, 0, 0, 0.05);
				       player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, particleLoc.add(0, 0.3, 0), 0, 0, 0, 0, 0.05);
				  }
				  
				  spawnDelay -= 0.05D;
				 if(spawnDelay <= 0) { 
				  int Offset = ThreadLocalRandom.current().nextInt(2, 5 + 1);
				  new RainArrows(Offset, specialArrow, player, plugin);
				  spawnDelay = 0.1D;
				  }
	                 }
	             }.runTaskTimer(plugin, 0L, 1L);		
			}
}
