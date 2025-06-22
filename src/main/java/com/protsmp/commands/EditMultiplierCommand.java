package com.protsmp.commands;

import com.protsmp.ProtSMP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditMultiplierCommand implements CommandExecutor {
    
    private final ProtSMP plugin;
    
    public EditMultiplierCommand(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("protsmp.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length != 2) {
            sender.sendMessage("§cUsage: /editmultiplier <player> <value>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: " + args[0]);
            return true;
        }
        
        try {
            double value = Double.parseDouble(args[1]);
            if (value < 0) {
                sender.sendMessage("§cMultiplier cannot be negative!");
                return true;
            }
            
            plugin.getMultiplierManager().setMultiplier(target.getUniqueId(), value);
            sender.sendMessage("§aSet " + target.getName() + "'s multiplier to §e" + String.format("%.2fx", value));
            target.sendMessage("§aYour multiplier has been set to §e" + String.format("%.2fx", value));
            
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number: " + args[1]);
        }
        
        return true;
    }
} 