package com.protsmp.managers;

import com.protsmp.ProtSMP;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeManager {
    
    private final ProtSMP plugin;
    
    public RecipeManager(ProtSMP plugin) {
        this.plugin = plugin;
    }
    
    public void registerRecipes() {
        // Protection Rune Recipe: DND/CSC/BNB
        // D=diamond block, N=netherstar, C=diamond chestplate, S=shield, B=netherite block
        ShapedRecipe protectionRuneRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "protection_rune"),
            plugin.getItemManager().createProtectionRune()
        );
        protectionRuneRecipe.shape("DND", "CSC", "BNB");
        protectionRuneRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        protectionRuneRecipe.setIngredient('N', Material.NETHER_STAR);
        protectionRuneRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        protectionRuneRecipe.setIngredient('S', Material.SHIELD);
        protectionRuneRecipe.setIngredient('B', Material.NETHERITE_BLOCK);
        plugin.getServer().addRecipe(protectionRuneRecipe);
        
        // Strength Rune Recipe: NPN/CEC/NPN
        // N=netherite block, P=protection rune, C=diamond chestplate, E=dragon egg
        ShapedRecipe strengthRuneRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "strength_rune"),
            plugin.getItemManager().createStrengthRune()
        );
        strengthRuneRecipe.shape("NPN", "CEC", "NPN");
        strengthRuneRecipe.setIngredient('N', Material.NETHERITE_BLOCK);
        strengthRuneRecipe.setIngredient('P', Material.CARROT_ON_A_STICK); // Will be checked in listener
        strengthRuneRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        strengthRuneRecipe.setIngredient('E', Material.DRAGON_EGG);
        plugin.getServer().addRecipe(strengthRuneRecipe);
        
        // Heart Rune Recipe: NPN/CEC/NPN (same as strength but different center)
        ShapedRecipe heartRuneRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "heart_rune"),
            plugin.getItemManager().createHeartRune()
        );
        heartRuneRecipe.shape("NPN", "CEC", "NPN");
        heartRuneRecipe.setIngredient('N', Material.NETHERITE_BLOCK);
        heartRuneRecipe.setIngredient('P', Material.CARROT_ON_A_STICK);
        heartRuneRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        heartRuneRecipe.setIngredient('E', Material.DRAGON_EGG);
        plugin.getServer().addRecipe(heartRuneRecipe);
        
        // Power Rune Recipe: NPN/CEC/NPN (same pattern)
        ShapedRecipe powerRuneRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "power_rune"),
            plugin.getItemManager().createPowerRune()
        );
        powerRuneRecipe.shape("NPN", "CEC", "NPN");
        powerRuneRecipe.setIngredient('N', Material.NETHERITE_BLOCK);
        powerRuneRecipe.setIngredient('P', Material.CARROT_ON_A_STICK);
        powerRuneRecipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
        powerRuneRecipe.setIngredient('E', Material.DRAGON_EGG);
        plugin.getServer().addRecipe(powerRuneRecipe);
        
        // Mace of Calamity Recipe: HMC/SFR/LPB
        // H=netherite helmet, M=mace, C=netherite chestplate, S=strength rune, F=core of calamity
        // R=heart rune, L=netherite leggings, P=power rune, B=netherite boots
        ShapedRecipe maceRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "mace_of_calamity"),
            plugin.getItemManager().createMaceOfCalamity()
        );
        maceRecipe.shape("HMC", "SFR", "LPB");
        maceRecipe.setIngredient('H', Material.NETHERITE_HELMET);
        maceRecipe.setIngredient('M', Material.CARROT_ON_A_STICK); // Will be checked in listener
        maceRecipe.setIngredient('C', Material.NETHERITE_CHESTPLATE);
        maceRecipe.setIngredient('S', Material.CARROT_ON_A_STICK);
        maceRecipe.setIngredient('F', Material.CARROT_ON_A_STICK);
        maceRecipe.setIngredient('R', Material.CARROT_ON_A_STICK);
        maceRecipe.setIngredient('L', Material.NETHERITE_LEGGINGS);
        maceRecipe.setIngredient('P', Material.CARROT_ON_A_STICK);
        maceRecipe.setIngredient('B', Material.NETHERITE_BOOTS);
        plugin.getServer().addRecipe(maceRecipe);
    }
} 