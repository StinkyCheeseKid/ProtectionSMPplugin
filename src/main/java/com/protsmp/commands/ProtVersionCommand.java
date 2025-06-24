package com.protsmp.commands;

import com.protsmp.ProtSMP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ProtVersionCommand implements CommandExecutor {
    private final ProtSMP plugin;
    public ProtVersionCommand(ProtSMP plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String version = plugin.getDescription().getVersion();
        sender.sendMessage("§aThis is ProtSMP version §e" + version + "§a!");
        return true;
    }
} 