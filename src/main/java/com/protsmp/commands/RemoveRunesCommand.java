package com.protsmp.commands;

import com.protsmp.ProtSMP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RemoveRunesCommand implements CommandExecutor {
    
    private final ProtSMP plugin;
    
    public RemoveRunesCommand(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("protsmp.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /removerunes <player>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: " + args[0]);
            return true;
        }
        
        // Remove all active effects
        plugin.getEffectManager().clearAllEffects(target.getUniqueId());
        
        // Remove all rune items from inventory
        ItemStack[] contents = target.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null && plugin.getItemManager().isCustomItem(contents[i])) {
                target.getInventory().setItem(i, null);
            }
        }
        
        sender.sendMessage("§aRemoved all rune effects and items from " + target.getName());
        target.sendMessage("§cAll your rune effects and items have been removed by an admin.");
        
        return true;
    }
} 