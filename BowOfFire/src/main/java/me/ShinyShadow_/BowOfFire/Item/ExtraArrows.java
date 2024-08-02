package me.ShinyShadow_.BowOfFire.Item;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ExtraArrows {
	private final Map<UUID, Integer> arrowTasks = new HashMap<>();
	
	public ExtraArrows(double Offset, Player player, Location eyeLoc, JavaPlugin plugin) {
	    Entity specialArrow = player.getWorld().spawn(eyeLoc, Arrow.class);
    	Vector direction = player.getEyeLocation().getDirection();
    	Vector right = direction.clone().crossProduct(new Vector(0, 0.1, 0)).normalize().multiply(Offset);
        Vector up = new Vector(0, 0.05, 0).normalize().multiply(0.1);
        Vector diagonalDirection = direction.clone().add(right).add(up).normalize().multiply(1.8);
    	specialArrow.setVelocity(diagonalDirection);
	
	int taskId = new BukkitRunnable() {
   	 double t = 0;
       @Override
       public void run() {
    	        
           if (specialArrow.isDead() || specialArrow.isOnGround()) {
             	specialArrow.getWorld().spawnParticle(Particle.FLAME, specialArrow.getLocation().add(0, 0.5, 0), 200, 0.1, 0.1, 0.1, 0.2);
   		    	specialArrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, specialArrow.getLocation().add(0, 0.5, 0), 200, 0.1, 0.1, 0.1, 0.2);
                specialArrow.getWorld().playSound(specialArrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1.8F);          
              
                Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(specialArrow.getLocation(), 5, 3, 5);
                
                for (Entity entity : nearbyEntities) {

                if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
                	entity.setFireTicks(30);
                	((LivingEntity)entity).damage(3);            	
                }
               }
                
                
                this.cancel();
               arrowTasks.remove(specialArrow.getUniqueId());
               return;
           }
           
           specialArrow.getWorld().spawnParticle(Particle.FLAME, specialArrow.getLocation(), 1, 0, 0, 0, 0);


       }
   }.runTaskTimer(plugin, 0L, 0L).getTaskId();


}

}
