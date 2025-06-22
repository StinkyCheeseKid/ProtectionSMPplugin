package com.protsmp.listeners;

import com.protsmp.ProtSMP;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CombatListener implements Listener {
    
    private final ProtSMP plugin;
    
    public CombatListener(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }
        
        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();
        
        // Apply multiplier-based damage scaling
        double multiplier = plugin.getMultiplierManager().getMultiplier(attacker.getUniqueId());
        
        // Check for Strength Rune effect
        if (plugin.getEffectManager().isStrengthActive(attacker.getUniqueId())) {
            event.setDamage(event.getDamage() * 2.0); // Double damage
        }
        
        // Check for Mace of Calamity effect
        boolean hasMace = false;
        for (ItemStack item : attacker.getInventory().getContents()) {
            if (item != null && plugin.getItemManager().isCustomItem(item) &&
                "mace_of_calamity".equals(plugin.getItemManager().getCustomItemType(item))) {
                hasMace = true;
                break;
            }
        }
        
        if (hasMace) {
            event.setDamage(event.getDamage() * 2.0); // Double damage from Mace
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = (Player) event.getEntity();
        Player killer = victim.getKiller();
        
        if (killer != null) {
            // Add multiplier for kill
            plugin.getMultiplierManager().addMultiplier(killer.getUniqueId(), 0.10);
            killer.sendMessage("§aYou gained +0.10x multiplier for killing " + victim.getName() + "!");
            
            // Check if killer used Mace of Calamity
            boolean usedMace = false;
            for (ItemStack item : killer.getInventory().getContents()) {
                if (item != null && plugin.getItemManager().isCustomItem(item) &&
                    "mace_of_calamity".equals(plugin.getItemManager().getCustomItemType(item))) {
                    usedMace = true;
                    break;
                }
            }
            
            if (usedMace) {
                // Ban victim permanently
                victim.kickPlayer("§4You have been permanently banned by the Mace of Calamity!");
                Bukkit.getBanList(BanList.Type.NAME).addBan(victim.getName(), 
                    "Killed by Mace of Calamity", null, "ProtSMP");
                
                // Broadcast to server
                Bukkit.broadcastMessage("§4§l" + victim.getName() + " has been permanently banned by the Mace of Calamity!");
            }
        }
    }
} 