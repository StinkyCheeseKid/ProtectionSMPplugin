package com.protsmp.tasks;

import com.protsmp.ProtSMP;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {
    
    private final ProtSMP plugin;
    private int taskId = -1;
    
    public ActionBarTask(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    public void start() {
        if (taskId == -1) {
            taskId = runTaskTimer(plugin, 0L, 20L).getTaskId(); // Every second
        }
    }
    
    public void stop() {
        if (taskId != -1) {
            cancel();
            taskId = -1;
        }
    }
    
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double multiplier = plugin.getMultiplierManager().getMultiplier(player.getUniqueId());
            String multiplierText = String.format("§e%.2fx", multiplier);
            
            // Send action bar message
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(multiplierText));
        }
    }
} 