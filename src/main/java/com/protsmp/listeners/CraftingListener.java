package com.protsmp.listeners;

import com.protsmp.ProtSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {
    
    private final ProtSMP plugin;
    
    public CraftingListener(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        
        Player player = (Player) event.getWhoClicked();
        ItemStack result = event.getRecipe().getResult();
        
        // Check if the crafted item is a custom item
        if (plugin.getItemManager().isCustomItem(result)) {
            String itemType = plugin.getItemManager().getCustomItemType(result);
            
            // Handle Mace of Calamity crafting
            if ("mace_of_calamity".equals(itemType)) {
                // Broadcast to server
                Bukkit.broadcastMessage("§4§l" + player.getName() + " has crafted the legendary Mace of Calamity!");
                Bukkit.broadcastMessage("§7The ultimate weapon of destruction has been unleashed!");
            }
            
            // Validate recipe ingredients for complex recipes
            if ("strength_rune".equals(itemType) || "heart_rune".equals(itemType) || "power_rune".equals(itemType)) {
                // Check if the center ingredient is the correct rune
                ItemStack centerItem = event.getInventory().getItem(4); // Center slot
                if (centerItem == null || !plugin.getItemManager().isCustomItem(centerItem)) {
                    event.setCancelled(true);
                    player.sendMessage("§cInvalid recipe! You need the correct rune in the center.");
                    return;
                }
                
                String centerType = plugin.getItemManager().getCustomItemType(centerItem);
                if ("strength_rune".equals(itemType) && !"protection_rune".equals(centerType)) {
                    event.setCancelled(true);
                    player.sendMessage("§cYou need a Protection Rune in the center for a Strength Rune!");
                    return;
                }
                if ("heart_rune".equals(itemType) && !"strength_rune".equals(centerType)) {
                    event.setCancelled(true);
                    player.sendMessage("§cYou need a Strength Rune in the center for a Heart Rune!");
                    return;
                }
                if ("power_rune".equals(itemType) && !"heart_rune".equals(centerType)) {
                    event.setCancelled(true);
                    player.sendMessage("§cYou need a Heart Rune in the center for a Power Rune!");
                    return;
                }
            }
            
            // Handle Mace of Calamity recipe validation
            if ("mace_of_calamity".equals(itemType)) {
                // Check if all required items are present
                ItemStack[] matrix = event.getInventory().getMatrix();
                boolean hasStrengthRune = false, hasCoreOfCalamity = false, hasHeartRune = false, hasPowerRune = false;
                
                for (ItemStack item : matrix) {
                    if (item != null && plugin.getItemManager().isCustomItem(item)) {
                        String type = plugin.getItemManager().getCustomItemType(item);
                        switch (type) {
                            case "strength_rune":
                                hasStrengthRune = true;
                                break;
                            case "core_of_calamity":
                                hasCoreOfCalamity = true;
                                break;
                            case "heart_rune":
                                hasHeartRune = true;
                                break;
                            case "power_rune":
                                hasPowerRune = true;
                                break;
                        }
                    }
                }
                
                if (!hasStrengthRune || !hasCoreOfCalamity || !hasHeartRune || !hasPowerRune) {
                    event.setCancelled(true);
                    player.sendMessage("§cInvalid Mace of Calamity recipe! You need all the required runes and items.");
                    return;
                }
            }
        }
    }
} 