package com.protsmp.commands;

import com.protsmp.ProtSMP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExtractRuneCommand implements CommandExecutor {
    
    private final ProtSMP plugin;
    private final Map<UUID, Long> cooldowns;
    
    public ExtractRuneCommand(ProtSMP plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("protsmp.extractrune")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length != 1) {
            player.sendMessage("§cUsage: /extractrune <strength|heart|power>");
            return true;
        }
        
        // Check cooldown
        long lastUse = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        if (System.currentTimeMillis() - lastUse < 5000) { // 5 seconds
            long remaining = (5000 - (System.currentTimeMillis() - lastUse)) / 1000;
            player.sendMessage("§cYou must wait " + remaining + " more seconds before using this command again!");
            return true;
        }
        
        String type = args[0].toLowerCase();
        boolean extracted = false;
        
        switch (type) {
            case "strength":
                if (plugin.getEffectManager().isStrengthActive(player.getUniqueId())) {
                    plugin.getEffectManager().setStrengthActive(player.getUniqueId(), false);
                    player.getInventory().addItem(plugin.getItemManager().createStrengthRune());
                    player.sendMessage("§aYou extracted your Strength Rune effect and received the item back!");
                    extracted = true;
                } else {
                    player.sendMessage("§cYou don't have an active Strength Rune effect!");
                }
                break;
            case "heart":
                if (plugin.getEffectManager().isHeartActive(player.getUniqueId())) {
                    plugin.getEffectManager().setHeartActive(player.getUniqueId(), false);
                    player.getInventory().addItem(plugin.getItemManager().createHeartRune());
                    player.sendMessage("§aYou extracted your Heart Rune effect and received the item back!");
                    extracted = true;
                } else {
                    player.sendMessage("§cYou don't have an active Heart Rune effect!");
                }
                break;
            case "power":
                if (plugin.getEffectManager().isPowerActive(player.getUniqueId())) {
                    plugin.getEffectManager().setPowerActive(player.getUniqueId(), false);
                    player.getInventory().addItem(plugin.getItemManager().createPowerRune());
                    player.sendMessage("§aYou extracted your Power Rune effect and received the item back!");
                    extracted = true;
                } else {
                    player.sendMessage("§cYou don't have an active Power Rune effect!");
                }
                break;
            default:
                player.sendMessage("§cUnknown type: " + type);
                player.sendMessage("§7Types: strength, heart, power");
                break;
        }
        
        if (extracted) {
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        }
        
        return true;
    }
} 