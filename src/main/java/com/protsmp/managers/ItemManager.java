package com.protsmp.managers;

import com.protsmp.ProtSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ItemManager {
    
    private final ProtSMP plugin;
    
    public ItemManager(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    public ItemStack createProtectionRune() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§6Protection Rune");
        meta.setLore(Arrays.asList(
            "§7Increases your multiplier by §e0.10x",
            "",
            "§7Right-click to use"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "protection_rune"
        );
        
        meta.setCustomModelData(1);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemStack createStrengthRune() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§cStrength Rune");
        meta.setLore(Arrays.asList(
            "§7Doubles your damage until death",
            "",
            "§7Right-click to use"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "strength_rune"
        );
        
        meta.setCustomModelData(2);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemStack createHeartRune() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§dHeart Rune");
        meta.setLore(Arrays.asList(
            "§7Doubles your maximum health until death",
            "",
            "§7Right-click to use"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "heart_rune"
        );
        
        meta.setCustomModelData(3);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemStack createPowerRune() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§bPower Rune");
        meta.setLore(Arrays.asList(
            "§7Sets your protection to §e3.00x §7until death",
            "",
            "§7Right-click to use"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "power_rune"
        );
        
        meta.setCustomModelData(4);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemStack createAbyssalFragment() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§8Abyssal Fragment");
        meta.setLore(Arrays.asList(
            "§7A fragment of pure darkness",
            "§7Used in crafting legendary items"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "abyssal_fragment"
        );
        
        meta.setCustomModelData(5);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemStack createCoreOfCalamity() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§4Core of Calamity");
        meta.setLore(Arrays.asList(
            "§7The heart of destruction itself",
            "§7Used in crafting the Mace of Calamity"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "core_of_calamity"
        );
        
        meta.setCustomModelData(6);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public ItemStack createMaceOfCalamity() {
        // Use Material.MACE if available, otherwise fallback to CARROT_ON_A_STICK for custom model data
        Material maceMaterial;
        try {
            maceMaterial = Material.valueOf("MACE");
        } catch (IllegalArgumentException e) {
            maceMaterial = Material.CARROT_ON_A_STICK; // Fallback for older Spigot versions
        }
        ItemStack item = new ItemStack(maceMaterial);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§4§lMace of Calamity");
        meta.setLore(Arrays.asList(
            "§7The ultimate weapon of destruction",
            "",
            "§7Passive Effects:",
            "§8• Sets max health to 20 hearts",
            "§8• Sets multiplier to 3.5x",
            "§8• Doubles all damage dealt",
            "",
            "§7Right-click to dash forward",
            "§7Kills with this weapon result in permanent ban"
        ));
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "mace_of_calamity"
        );
        meta.setCustomModelData(7);
        meta.setUnbreakable(true);
        // Add Density X and Wind Burst II (using DURABILITY and KNOCKBACK as placeholders)
        meta.addEnchant(Enchantment.DURABILITY, 10, true); // Placeholder for Density X
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true); // Placeholder for Wind Burst II
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
    
    public ItemStack createRespawnBeacon() {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName("§aRespawn Beacon");
        meta.setLore(Arrays.asList(
            "§7Right-click to open unban menu",
            "§7Shows all players banned by the plugin"
        ));
        
        meta.getPersistentDataContainer().set(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING,
            "respawn_beacon"
        );
        
        meta.setCustomModelData(8);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public boolean isCustomItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING
        );
    }
    
    public String getCustomItemType(ItemStack item) {
        if (!isCustomItem(item)) return null;
        
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(
            new NamespacedKey(plugin, "custom_item"),
            PersistentDataType.STRING
        );
    }
} 