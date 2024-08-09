package com.dig.iamauth;

import com.dig.iamauth.commands.RegisterCommand;
import com.dig.iamauth.listeners.JoinListener;
import com.dig.iamauth.listeners.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private static List<UUID> registered;
    public static List<UUID> getRegistered() {
        return registered;
    }
    private static List<UUID> premium;
    public static List<UUID> getPremium() {
        return registered;
    }
    private static List<UUID> logged;
    public static List<UUID> getLogged() {
        return registered;
    }

    @Override
    public void onEnable() {
        getLogger().warning("IamAuth Enabled! - by IamDig_");
        registered = new ArrayList<>();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(this), this);
        getCommand("register").setExecutor(new RegisterCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().warning("IamAuth Disabled! - by IamDig_");
    }
}
