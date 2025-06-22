package com.protsmp.managers;

import com.protsmp.ProtSMP;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EffectManager {
    
    private final ProtSMP plugin;
    private final Map<UUID, Boolean> strengthActive;
    private final Map<UUID, Boolean> heartActive;
    private final Map<UUID, Boolean> powerActive;
    private final Map<UUID, Double> originalMultipliers;
    private final Map<UUID, Long> dashCooldowns;
    
    public EffectManager(ProtSMP plugin) {
        this.plugin = plugin;
        this.strengthActive = new HashMap<>();
        this.heartActive = new HashMap<>();
        this.powerActive = new HashMap<>();
        this.originalMultipliers = new HashMap<>();
        this.dashCooldowns = new HashMap<>();
    }
    
    public boolean isStrengthActive(UUID playerUUID) {
        return strengthActive.getOrDefault(playerUUID, false);
    }
    
    public boolean isHeartActive(UUID playerUUID) {
        return heartActive.getOrDefault(playerUUID, false);
    }
    
    public boolean isPowerActive(UUID playerUUID) {
        return powerActive.getOrDefault(playerUUID, false);
    }
    
    public void setStrengthActive(UUID playerUUID, boolean active) {
        strengthActive.put(playerUUID, active);
    }
    
    public void setHeartActive(UUID playerUUID, boolean active) {
        heartActive.put(playerUUID, active);
        Player player = plugin.getServer().getPlayer(playerUUID);
        if (player != null) {
            if (active) {
                player.setMaxHealth(40.0); // 20 hearts
                player.setHealth(40.0);
            } else {
                player.setMaxHealth(20.0); // 10 hearts
                if (player.getHealth() > 20.0) {
                    player.setHealth(20.0);
                }
            }
        }
    }
    
    public void setPowerActive(UUID playerUUID, boolean active) {
        powerActive.put(playerUUID, active);
        if (active) {
            // Save original multiplier and set to 3.0x
            originalMultipliers.put(playerUUID, plugin.getMultiplierManager().getMultiplier(playerUUID));
            plugin.getMultiplierManager().setMultiplier(playerUUID, 3.0);
            plugin.getMultiplierManager().setMultiplierCap(playerUUID, 3.0);
        } else {
            // Restore original multiplier
            Double original = originalMultipliers.get(playerUUID);
            if (original != null) {
                plugin.getMultiplierManager().setMultiplier(playerUUID, original);
                plugin.getMultiplierManager().setMultiplierCap(playerUUID, 2.0);
                originalMultipliers.remove(playerUUID);
            }
        }
    }
    
    public void clearAllEffects(UUID playerUUID) {
        setStrengthActive(playerUUID, false);
        setHeartActive(playerUUID, false);
        setPowerActive(playerUUID, false);
    }
    
    public boolean canDash(UUID playerUUID) {
        long lastDash = dashCooldowns.getOrDefault(playerUUID, 0L);
        return System.currentTimeMillis() - lastDash >= 10000; // 10 seconds
    }
    
    public void setDashCooldown(UUID playerUUID) {
        dashCooldowns.put(playerUUID, System.currentTimeMillis());
    }
    
    public void removePlayer(UUID playerUUID) {
        strengthActive.remove(playerUUID);
        heartActive.remove(playerUUID);
        powerActive.remove(playerUUID);
        originalMultipliers.remove(playerUUID);
        dashCooldowns.remove(playerUUID);
    }
} 