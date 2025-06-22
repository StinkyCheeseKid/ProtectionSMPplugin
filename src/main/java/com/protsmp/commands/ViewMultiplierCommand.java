package com.protsmp.commands;

import com.protsmp.ProtSMP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewMultiplierCommand implements CommandExecutor {
    
    private final ProtSMP plugin;
    
    public ViewMultiplierCommand(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("protsmp.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /viewmultiplier <player>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: " + args[0]);
            return true;
        }
        
        double multiplier = plugin.getMultiplierManager().getMultiplier(target.getUniqueId());
        double cap = plugin.getMultiplierManager().getMultiplierCap(target.getUniqueId());
        
        sender.sendMessage("§a" + target.getName() + "'s multiplier: §e" + String.format("%.2fx", multiplier));
        sender.sendMessage("§a" + target.getName() + "'s multiplier cap: §e" + String.format("%.2fx", cap));
        
        return true;
    }
} 