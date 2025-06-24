package com.protsmp;

import com.protsmp.commands.*;
import com.protsmp.listeners.*;
import com.protsmp.managers.*;
import com.protsmp.tasks.ActionBarTask;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtSMP extends JavaPlugin {
    
    private static ProtSMP instance;
    private MultiplierManager multiplierManager;
    private ItemManager itemManager;
    private RecipeManager recipeManager;
    private EffectManager effectManager;
    private ActionBarTask actionBarTask;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Initialize managers
        multiplierManager = new MultiplierManager(this);
        itemManager = new ItemManager(this);
        recipeManager = new RecipeManager(this);
        effectManager = new EffectManager(this);
        
        // Register commands
        getCommand("giverune").setExecutor(new GiveRuneCommand(this));
        getCommand("viewmultiplier").setExecutor(new ViewMultiplierCommand(this));
        getCommand("editmultiplier").setExecutor(new EditMultiplierCommand(this));
        getCommand("removerunes").setExecutor(new RemoveRunesCommand(this));
        getCommand("extractrune").setExecutor(new ExtractRuneCommand(this));
        getCommand("prot").setExecutor(new ProtVersionCommand(this));
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftingListener(this), this);
        
        // Register recipes
        recipeManager.registerRecipes();
        
        // Start action bar task
        actionBarTask = new ActionBarTask(this);
        actionBarTask.start();
        
        getLogger().info("ProtSMP has been enabled!");
    }
    
    @Override
    public void onDisable() {
        if (actionBarTask != null) {
            actionBarTask.stop();
        }
        
        if (multiplierManager != null) {
            multiplierManager.saveAll();
        }
        
        getLogger().info("ProtSMP has been disabled!");
    }
    
    public static ProtSMP getInstance() {
        return instance;
    }
    
    public MultiplierManager getMultiplierManager() {
        return multiplierManager;
    }
    
    public ItemManager getItemManager() {
        return itemManager;
    }
    
    public RecipeManager getRecipeManager() {
        return recipeManager;
    }
    
    public EffectManager getEffectManager() {
        return effectManager;
    }
} 