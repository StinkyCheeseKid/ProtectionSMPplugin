package com.protsmp.managers;

import com.protsmp.ProtSMP;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiplierManager {
    
    private final ProtSMP plugin;
    private final Map<UUID, Double> multipliers;
    private final Map<UUID, Double> multiplierCaps;
    private final File multipliersFile;
    private final FileConfiguration multipliersConfig;
    
    public MultiplierManager(ProtSMP plugin) {
        this.plugin = plugin;
        this.multipliers = new HashMap<>();
        this.multiplierCaps = new HashMap<>();
        
        // Load multipliers file
        this.multipliersFile = new File(plugin.getDataFolder(), "multipliers.yml");
        if (!multipliersFile.exists()) {
            plugin.saveResource("multipliers.yml", false);
        }
        this.multipliersConfig = YamlConfiguration.loadConfiguration(multipliersFile);
        
        loadMultipliers();
    }
    
    public double getMultiplier(UUID playerUUID) {
        return multipliers.getOrDefault(playerUUID, 1.0);
    }
    
    public double getMultiplierCap(UUID playerUUID) {
        return multiplierCaps.getOrDefault(playerUUID, 2.0);
    }
    
    public void setMultiplier(UUID playerUUID, double multiplier) {
        multipliers.put(playerUUID, Math.max(0.0, multiplier));
        saveMultiplier(playerUUID);
        updatePlayerDisplay(playerUUID);
    }
    
    public void setMultiplierCap(UUID playerUUID, double cap) {
        multiplierCaps.put(playerUUID, cap);
        saveMultiplier(playerUUID);
    }
    
    public void addMultiplier(UUID playerUUID, double amount) {
        double current = getMultiplier(playerUUID);
        double cap = getMultiplierCap(playerUUID);
        double newMultiplier = Math.min(current + amount, cap);
        
        setMultiplier(playerUUID, newMultiplier);
        
        // If at cap, give protection rune instead
        if (current + amount > cap) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                player.getInventory().addItem(plugin.getItemManager().createProtectionRune());
                player.sendMessage("§eYou received a Protection Rune instead of a multiplier increase!");
            }
        }
    }
    
    public void removeMultiplier(UUID playerUUID, double amount) {
        double current = getMultiplier(playerUUID);
        double newMultiplier = Math.max(0.0, current - amount);
        
        setMultiplier(playerUUID, newMultiplier);
        
        // Check for ban condition
        if (newMultiplier <= 0.0) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                player.kickPlayer("§cYou have been banned for reaching 0.0x multiplier!");
                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
                    "Reached 0.0x multiplier", null, "ProtSMP");
            }
        }
    }
    
    public void updatePlayerDisplay(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            double multiplier = getMultiplier(playerUUID);
            String multiplierText = String.format("§e%.2fx", multiplier);
            
            // Update display name
            player.setDisplayName(player.getName() + " §7[§e" + String.format("%.2fx", multiplier) + "§7]");
            player.setPlayerListName(player.getName() + " §7[§e" + String.format("%.2fx", multiplier) + "§7]");
        }
    }
    
    private void loadMultipliers() {
        if (multipliersConfig.contains("multipliers")) {
            for (String uuidString : multipliersConfig.getConfigurationSection("multipliers").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidString);
                double multiplier = multipliersConfig.getDouble("multipliers." + uuidString + ".value", 1.0);
                double cap = multipliersConfig.getDouble("multipliers." + uuidString + ".cap", 2.0);
                
                multipliers.put(uuid, multiplier);
                multiplierCaps.put(uuid, cap);
            }
        }
    }
    
    private void saveMultiplier(UUID playerUUID) {
        multipliersConfig.set("multipliers." + playerUUID.toString() + ".value", getMultiplier(playerUUID));
        multipliersConfig.set("multipliers." + playerUUID.toString() + ".cap", getMultiplierCap(playerUUID));
        
        try {
            multipliersConfig.save(multipliersFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save multipliers.yml: " + e.getMessage());
        }
    }
    
    public void saveAll() {
        for (UUID uuid : multipliers.keySet()) {
            saveMultiplier(uuid);
        }
    }
} 