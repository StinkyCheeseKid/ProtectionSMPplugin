package com.protsmp.listeners;

import com.protsmp.ProtSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.*;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemListener implements Listener {
    
    private final ProtSMP plugin;
    private final Map<UUID, String> pluginBannedPlayers = new HashMap<>(); // uuid -> reason
    private final Map<UUID, String> pendingUnbans = new HashMap<>(); // uuid -> reason
    
    public ItemListener(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (item == null || !plugin.getItemManager().isCustomItem(item)) {
            return;
        }
        
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        String itemType = plugin.getItemManager().getCustomItemType(item);
        
        switch (itemType) {
            case "protection_rune":
                event.setCancelled(true);
                useProtectionRune(player, item);
                break;
            case "strength_rune":
                event.setCancelled(true);
                useStrengthRune(player, item);
                break;
            case "heart_rune":
                event.setCancelled(true);
                useHeartRune(player, item);
                break;
            case "power_rune":
                event.setCancelled(true);
                usePowerRune(player, item);
                break;
            case "mace_of_calamity":
                event.setCancelled(true);
                useMaceOfCalamity(player);
                break;
            case "respawn_beacon":
                event.setCancelled(true);
                useRespawnBeacon(player);
                break;
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();
        if (title == null || !title.startsWith("§aRespawn Beacon")) return;
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() != Material.PLAYER_HEAD) return;
        if (!(clicked.getItemMeta() instanceof SkullMeta)) return;
        SkullMeta meta = (SkullMeta) clicked.getItemMeta();
        if (meta == null || meta.getOwningPlayer() == null) return;
        OfflinePlayer banned = meta.getOwningPlayer();
        if (banned == null) return;
        UUID bannedUUID = banned.getUniqueId();
        // Unban logic
        Bukkit.getBanList(BanList.Type.NAME).pardon(banned.getName());
        // Mark for multiplier reset on next join
        plugin.getMultiplierManager().setMultiplier(bannedUUID, 0.50);
        player.sendMessage("§aUnbanned " + banned.getName() + ". Their multiplier will be set to 0.50x on next join.");
        player.closeInventory();
    }
    
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();
        if (plugin.getItemManager().isCustomItem(item) && "mace_of_calamity".equals(plugin.getItemManager().getCustomItemType(item))) {
            plugin.getMultiplierManager().setMultiplier(player.getUniqueId(), 3.5);
            plugin.getMultiplierManager().setMultiplierCap(player.getUniqueId(), 3.5);
        }
    }
    
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        if (plugin.getItemManager().isCustomItem(item) && "mace_of_calamity".equals(plugin.getItemManager().getCustomItemType(item))) {
            plugin.getMultiplierManager().setMultiplier(player.getUniqueId(), 2.0);
            plugin.getMultiplierManager().setMultiplierCap(player.getUniqueId(), 2.0);
        }
    }
    
    private void useProtectionRune(Player player, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getMultiplierManager().addMultiplier(player.getUniqueId(), 0.10);
        player.sendMessage("§aYou used a Protection Rune! Your multiplier increased by 0.10x");
    }
    
    private void useStrengthRune(Player player, ItemStack item) {
        if (plugin.getEffectManager().isStrengthActive(player.getUniqueId())) {
            player.sendMessage("§cYou already have an active Strength Rune effect!");
            return;
        }
        
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getEffectManager().setStrengthActive(player.getUniqueId(), true);
        player.sendMessage("§aYou used a Strength Rune! Your damage is now doubled until death!");
    }
    
    private void useHeartRune(Player player, ItemStack item) {
        if (plugin.getEffectManager().isHeartActive(player.getUniqueId())) {
            player.sendMessage("§cYou already have an active Heart Rune effect!");
            return;
        }
        
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getEffectManager().setHeartActive(player.getUniqueId(), true);
        player.sendMessage("§aYou used a Heart Rune! Your max health is now doubled until death!");
    }
    
    private void usePowerRune(Player player, ItemStack item) {
        if (plugin.getEffectManager().isPowerActive(player.getUniqueId())) {
            player.sendMessage("§cYou already have an active Power Rune effect!");
            return;
        }
        
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
        
        plugin.getEffectManager().setPowerActive(player.getUniqueId(), true);
        player.sendMessage("§aYou used a Power Rune! Your multiplier is now 3.00x until death!");
    }
    
    private void useMaceOfCalamity(Player player) {
        if (!plugin.getEffectManager().canDash(player.getUniqueId())) {
            player.sendMessage("§cDash ability is on cooldown!");
            return;
        }
        
        // Dash ability
        Vector direction = player.getLocation().getDirection();
        player.setVelocity(direction.multiply(2.0)); // Launch player forward
        
        plugin.getEffectManager().setDashCooldown(player.getUniqueId());
        player.sendMessage("§aYou used the Mace of Calamity's dash ability!");
        
        // Negate fall damage for a short time
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.setFallDistance(0);
        }, 20L); // 1 second later
    }
    
    private void useRespawnBeacon(Player player) {
        // Find all plugin-banned players (by ban reason)
        List<OfflinePlayer> bannedPlayers = new ArrayList<>();
        for (BanEntry entry : Bukkit.getBanList(BanList.Type.NAME).getBanEntries()) {
            String reason = entry.getReason();
            if (reason != null && (reason.contains("0.0x multiplier") || reason.contains("Mace of Calamity"))) {
                OfflinePlayer banned = Bukkit.getOfflinePlayer(entry.getTarget());
                bannedPlayers.add(banned);
            }
        }
        int size = Math.max(9, ((bannedPlayers.size() + 8) / 9) * 9);
        Inventory gui = Bukkit.createInventory(null, size, "§aRespawn Beacon - Unban Menu");
        for (OfflinePlayer banned : bannedPlayers) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(banned);
            meta.setDisplayName("§c" + banned.getName());
            meta.setLore(Collections.singletonList("§7Click to unban and set to 0.50x"));
            head.setItemMeta(meta);
            gui.addItem(head);
        }
        player.openInventory(gui);
    }
} 