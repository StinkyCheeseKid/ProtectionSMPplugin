package com.protsmp.commands;

import com.protsmp.ProtSMP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveRuneCommand implements CommandExecutor {
    
    private final ProtSMP plugin;
    
    public GiveRuneCommand(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("protsmp.giverune")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length != 1) {
            player.sendMessage("§cUsage: /giverune <type>");
            player.sendMessage("§7Types: protection, strength, heart, power, abyssal, core, mace, respawnbeacon");
            return true;
        }
        
        String type = args[0].toLowerCase();
        switch (type) {
            case "protection":
                player.getInventory().addItem(plugin.getItemManager().createProtectionRune());
                player.sendMessage("§aYou received a Protection Rune!");
                break;
            case "strength":
                player.getInventory().addItem(plugin.getItemManager().createStrengthRune());
                player.sendMessage("§aYou received a Strength Rune!");
                break;
            case "heart":
                player.getInventory().addItem(plugin.getItemManager().createHeartRune());
                player.sendMessage("§aYou received a Heart Rune!");
                break;
            case "power":
                player.getInventory().addItem(plugin.getItemManager().createPowerRune());
                player.sendMessage("§aYou received a Power Rune!");
                break;
            case "abyssal":
                player.getInventory().addItem(plugin.getItemManager().createAbyssalFragment());
                player.sendMessage("§aYou received an Abyssal Fragment!");
                break;
            case "core":
                player.getInventory().addItem(plugin.getItemManager().createCoreOfCalamity());
                player.sendMessage("§aYou received a Core of Calamity!");
                break;
            case "mace":
                player.getInventory().addItem(plugin.getItemManager().createMaceOfCalamity());
                player.sendMessage("§aYou received the Mace of Calamity!");
                break;
            case "respawnbeacon":
                player.getInventory().addItem(plugin.getItemManager().createRespawnBeacon());
                player.sendMessage("§aYou received a Respawn Beacon!");
                break;
            default:
                player.sendMessage("§cUnknown type: " + type);
                player.sendMessage("§7Types: protection, strength, heart, power, abyssal, core, mace, respawnbeacon");
                break;
        }
        
        return true;
    }
} 