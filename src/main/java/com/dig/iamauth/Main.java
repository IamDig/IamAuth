package com.dig.iamauth;

import com.dig.iamauth.commands.*;
import com.dig.iamauth.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private static List<UUID> logged;
    public static List<UUID> getLogged() {
        return logged;
    }

    public static Main getPlugin() {
        return getPlugin(Main.class);
    }

    @Override
    public void onEnable() {

        // passwords.yml
        File file = new File(getDataFolder(), "passwords.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                getLogger().warning("[IamAuth] It was not possible to create passwords.yml file");
            }
        }

        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        try {
            modifyFile.save(file);
        } catch (IOException e) {
            getLogger().warning("[IamAuth] It was not possible to save passwords.yml");
        }

        // Logger
        getLogger().warning("IamAuth Enabled! - by IamDig_");

        // ArrayLists
        logged = new ArrayList<>();

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Listeners
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new AuthListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);

        // Commands
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("changepassword").setExecutor(new ChangepasswordCommand(this));
        getCommand("unregister").setExecutor(new UnregisterCommand(this));
        getCommand("auth").setExecutor(new AuthCommand(this));

        // Tab Completers
    }

    @Override
    public void onDisable() {
        getLogger().warning("IamAuth Disabled! - by IamDig_");
    }
}
