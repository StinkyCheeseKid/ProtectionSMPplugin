package com.protsmp.listeners;

import com.protsmp.ProtSMP;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    
    private final ProtSMP plugin;
    
    public PlayerListener(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Update display name with multiplier
        plugin.getMultiplierManager().updatePlayerDisplay(player.getUniqueId());
        
        // Check if player was unbanned and set multiplier to 0.50x
        if (player.hasPermission("protsmp.unbanned")) {
            plugin.getMultiplierManager().setMultiplier(player.getUniqueId(), 0.50);
            player.sendMessage("§aWelcome back! Your multiplier has been set to 0.50x");
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Clean up effects
        plugin.getEffectManager().removePlayer(player.getUniqueId());
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        
        // Remove multiplier on death
        plugin.getMultiplierManager().removeMultiplier(player.getUniqueId(), 0.10);
        
        // Handle active rune effects
        if (plugin.getEffectManager().isStrengthActive(player.getUniqueId())) {
            plugin.getEffectManager().setStrengthActive(player.getUniqueId(), false);
            // Drop strength rune at death location
            player.getWorld().dropItemNaturally(player.getLocation(), 
                plugin.getItemManager().createStrengthRune());
        }
        
        if (plugin.getEffectManager().isHeartActive(player.getUniqueId())) {
            plugin.getEffectManager().setHeartActive(player.getUniqueId(), false);
            // Drop heart rune at death location
            player.getWorld().dropItemNaturally(player.getLocation(), 
                plugin.getItemManager().createHeartRune());
        }
        
        if (plugin.getEffectManager().isPowerActive(player.getUniqueId())) {
            plugin.getEffectManager().setPowerActive(player.getUniqueId(), false);
            // Drop power rune at death location
            player.getWorld().dropItemNaturally(player.getLocation(), 
                plugin.getItemManager().createPowerRune());
        }
        
        // Check for Mace of Calamity in inventory and apply effects
        boolean hadMace = false;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && plugin.getItemManager().isCustomItem(item) &&
                "mace_of_calamity".equals(plugin.getItemManager().getCustomItemType(item))) {
                hadMace = true;
                break;
            }
        }
        
        if (hadMace) {
            plugin.getMultiplierManager().setMultiplier(player.getUniqueId(), 2.0);
            plugin.getMultiplierManager().setMultiplierCap(player.getUniqueId(), 2.0);
        }
    }
} 