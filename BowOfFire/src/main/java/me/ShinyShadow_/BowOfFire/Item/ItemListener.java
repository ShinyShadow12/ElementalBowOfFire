package me.ShinyShadow_.BowOfFire.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.RegionAccessor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import org.bukkit.entity.Player;

public class ItemListener implements Listener{
	  private final JavaPlugin plugin;
	  private Location eyeLoc;
	  
	  private int arrowsShot = 3;
	  
	  private ItemStack item;
	  private ItemMeta meta;
	  
	  private boolean isSpecial = false;
	private final Map<UUID, Integer> arrowTasks = new HashMap<>();
	
	   public ItemListener(JavaPlugin plugin) {
	        this.plugin = plugin;
	    }
	@EventHandler
	public void onArrowShoot(ProjectileLaunchEvent event) {
			

		if (event.getEntity() instanceof Arrow ) {

			Player player = (Player) event.getEntity().getShooter();
			player.setCooldown(Material.BOW, 50);

		if(player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(Arrays.asList("Sex."))) {

			eyeLoc = player.getEyeLocation();
            Entity arrow = event.getEntity();
            arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_BLAZE_HURT , 1F, 0.7F);   
            arrow.setSilent(true);

            //player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 1F, 0.05F);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1F, 0.5F);
                      
            arrowsShot -= 1;
  	        item = player.getInventory().getItemInMainHand();
     	    meta = item.getItemMeta();
            if(arrowsShot > 0 && player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(Arrays.asList("Sex."))) {
                  meta.setDisplayName(ChatColor.GOLD + "Elemental Bow: Fire ("+arrowsShot+")");     
                  isSpecial = false;
            }             
            if(arrowsShot == 0) {                 	
                  meta.setDisplayName(ChatColor.GOLD + "SPECIAL SHOT READY");    
                  
            }
            if(arrowsShot < 0) {
            	isSpecial = true;
                new SpecialAttack(player, plugin);
                arrow.remove();
            	arrowsShot = 3;
            }
                  
            item.setItemMeta(meta);
            if(!isSpecial) {  
        	new ExtraArrows(0.2, player, eyeLoc, plugin);       	
        	new ExtraArrows(-0.2, player, eyeLoc, plugin);
        	
            int taskId = new BukkitRunnable() {
            	 double t = 0;
                @Override
                public void run() {
             	
                if(!isSpecial) {
                    if (arrow.isDead() || arrow.isOnGround()) {
                    	arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation().add(0, 0.5, 0), 200, 0.1, 0.1, 0.1, 0.2);
            		 	arrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, arrow.getLocation().add(0, 0.5, 0), 200, 0.1, 0.1, 0.1, 0.2);
                        arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1.8F);
                                               
                        this.cancel();
                        arrowTasks.remove(arrow.getUniqueId());
                        return;
                    }
                    
                    Location pLocation = arrow.getLocation();
                    for (int i = 0; i < 100; i++) {
                    t += Math.PI / 20;
                    double r = 0.4;
                    double x = r * Math.cos(t);
                    double y = t * 1;
                    double z = r * Math.sin(t);

                    // Get the arrow's direction and compute the orthogonal basis
                    Vector direction = arrow.getVelocity().normalize();
                    Vector ortho1 = new Vector(-direction.getZ(), 0, direction.getX()).normalize();
                    Vector ortho2 = direction.getCrossProduct(ortho1).normalize();
                    // Compute the world position by rotating the local vector
                    Vector worldPos1 = arrow.getLocation().toVector().add(ortho1.clone().multiply(x)).add(ortho2.clone().multiply(z)).add(direction.clone().multiply(y));
                    //Vector worldPos2 = arrow.getLocation().toVector().add(ortho1.clone().multiply(-x)).add(ortho2.clone().multiply(-z)).add(direction.clone().multiply(y));
                    eyeLoc = worldPos1.toLocation(player.getWorld());
                    
                    Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(eyeLoc, 1.5, 1.5, 1.5);
                    
                    for (Entity entity : nearbyEntities) {

                    if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
                    	entity.setFireTicks(60);
                    	((LivingEntity)entity).damage(3);            	
                    }
                   }
                    
                    arrow.getWorld().spawnParticle(Particle.FLAME, worldPos1.toLocation(arrow.getWorld()), 0);
                    arrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, worldPos1.toLocation(arrow.getWorld()), 3, 0.1, 0.1, 0.1, 0.01);
                    arrow.getWorld().getBlockAt(pLocation).setType(Material.FIRE);

                    }

                }
                }
                
            }.runTaskTimer(plugin, 0L, 0L).getTaskId();
            }
		}
      }		
	}	

	}
		
