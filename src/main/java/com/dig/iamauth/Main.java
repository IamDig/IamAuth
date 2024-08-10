package com.dig.iamauth;

import com.dig.iamauth.commands.ChangepasswordCommand;
import com.dig.iamauth.commands.LoginCommand;
import com.dig.iamauth.commands.RegisterCommand;
import com.dig.iamauth.commands.UnregisterCommand;
import com.dig.iamauth.listeners.AuthListener;
import com.dig.iamauth.listeners.ChatListener;
import com.dig.iamauth.listeners.JoinListener;
import com.dig.iamauth.listeners.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private static List<UUID> registered;
    public static List<UUID> getRegistered() {
        return registered;
    }
    private static List<UUID> logged;
    public static List<UUID> getLogged() {
        return logged;
    }

    public static Main getPlugin() {
        return getPlugin(Main.class);
    }

    @Override
    public void onEnable() {
        getLogger().warning("IamAuth Enabled! - by IamDig_");
        registered = new ArrayList<>();
        logged = new ArrayList<>();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new AuthListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("changepassword").setExecutor(new ChangepasswordCommand(this));
        getCommand("unregister").setExecutor(new UnregisterCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().warning("IamAuth Disabled! - by IamDig_");
    }
}
