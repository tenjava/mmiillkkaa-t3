package com.mmiillkkaa.randoms.listener;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity instanceof Chicken && entity.getPassenger() != null
                && entity.getPassenger().getType() == EntityType.SILVERFISH) {
            //When the chicken dies, we want to get rid of the silverfish.
            Silverfish silverfish = (Silverfish) entity.getPassenger();
            silverfish.setHealth(0D);
        } else if(entity instanceof Pig && entity.getVehicle() != null
                && entity.getVehicle().getType() == EntityType.OCELOT) {
            Ocelot ocelot = (Ocelot) entity.getVehicle();
            ocelot.setMaxHealth(10D);
            ocelot.setHealth(10D);
            ocelot.removePotionEffect(PotionEffectType.INVISIBILITY);
            ((Player)ocelot.getOwner()).sendMessage("Congratulations, you have a cat now!");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityExplode(EntityExplodeEvent event) {
        if(event.isCancelled()) {
            return;
        }
        if(event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            if(creeper.getEquipment().getHelmet().getType() == Material.DIRT) {

            }
        }
    }
}
