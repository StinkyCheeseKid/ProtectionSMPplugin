package com.protsmp.listeners;

import com.protsmp.ProtSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemListener implements Listener {
    
    private final ProtSMP plugin;
    
    public ItemListener(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (item == null || !plugin.getItemManager().isCustomItem(item)) {
            return;
        }
        
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        String itemType = plugin.getItemManager().getCustomItemType(item);
        
        switch (itemType) {
            case "protection_rune":
                event.setCancelled(true);
                useProtectionRune(player, item);
                break;
            case "strength_rune":
                event.setCancelled(true);
                useStrengthRune(player, item);
                break;
            case "heart_rune":
                event.setCancelled(true);
                useHeartRune(player, item);
                break;
            case "power_rune":
                event.setCancelled(true);
                usePowerRune(player, item);
                break;
            case "mace_of_calamity":
                event.setCancelled(true);
                useMaceOfCalamity(player);
                break;
            case "respawn_beacon":
                event.setCancelled(true);
                useRespawnBeacon(player);
                break;
        }
    }
    
    private void useProtectionRune(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getMultiplierManager().addMultiplier(player.getUniqueId(), 0.10);
        player.sendMessage("§aYou used a Protection Rune! Your multiplier increased by 0.10x");
    }
    
    private void useStrengthRune(Player player, ItemStack item) {
        if (plugin.getEffectManager().isStrengthActive(player.getUniqueId())) {
            player.sendMessage("§cYou already have an active Strength Rune effect!");
            return;
        }
        
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getEffectManager().setStrengthActive(player.getUniqueId(), true);
        player.sendMessage("§aYou used a Strength Rune! Your damage is now doubled until death!");
    }
    
    private void useHeartRune(Player player, ItemStack item) {
        if (plugin.getEffectManager().isHeartActive(player.getUniqueId())) {
            player.sendMessage("§cYou already have an active Heart Rune effect!");
            return;
        }
        
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getEffectManager().setHeartActive(player.getUniqueId(), true);
        player.sendMessage("§aYou used a Heart Rune! Your max health is now doubled until death!");
    }
    
    private void usePowerRune(Player player, ItemStack item) {
        if (plugin.getEffectManager().isPowerActive(player.getUniqueId())) {
            player.sendMessage("§cYou already have an active Power Rune effect!");
            return;
        }
        
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getEffectManager().setPowerActive(player.getUniqueId(), true);
        player.sendMessage("§aYou used a Power Rune! Your multiplier is now 3.00x until death!");
    }
    
    private void useMaceOfCalamity(Player player) {
        if (!plugin.getEffectManager().canDash(player.getUniqueId())) {
            player.sendMessage("§cDash ability is on cooldown!");
            return;
        }
        
        // Dash ability
        Vector direction = player.getLocation().getDirection();
        player.setVelocity(direction.multiply(2.0)); // Launch player forward
        
        plugin.getEffectManager().setDashCooldown(player.getUniqueId());
        player.sendMessage("§aYou used the Mace of Calamity's dash ability!");
        
        // Negate fall damage for a short time
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.setFallDistance(0);
        }, 20L); // 1 second later
    }
    
    private void useRespawnBeacon(Player player) {
        // TODO: Implement GUI for unbanning players
        player.sendMessage("§aRespawn Beacon GUI will be implemented soon!");
    }
} 