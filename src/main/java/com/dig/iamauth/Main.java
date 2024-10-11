package com.dig.iamauth;

import com.dig.iamauth.commands.*;
import com.dig.iamauth.listeners.*;
import com.dig.iamauth.commands.tabcompleters.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {
    @Getter
    private static List<UUID> logged;
    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        init();
    }

    private void initPasswordFile() {
        File file = new File(getDataFolder(), "passwords.yml");
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException ex) {
                getLogger().warning("It was not possible to create passwords.yml file");
            }
    }

    private void init() {

        // Arrays
        logged = new ArrayList<>();

        // Logger
        getLogger().warning("<------------------------>");
        getLogger().warning("     IamAuth: Enabled     ");
        getLogger().warning("                          ");
        getLogger().warning("        By IamDig_        ");
        getLogger().warning("<------------------------>");

        // Files
        saveDefaultConfig();
        initPasswordFile();

        // Listeners
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AuthListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(this), this);

        // Commands
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("changepassword").setExecutor(new ChangepasswordCommand(this));
        getCommand("unregister").setExecutor(new UnregisterCommand(this));
        getCommand("auth").setExecutor(new AuthCommand(this));

        // Tab Completers
        getCommand("auth").setTabCompleter(new AuthCompleter());
        getCommand("changepass").setTabCompleter(new ChangepasswordCompleter());
        getCommand("unregister").setTabCompleter(new UnregisterCompleter());
        getCommand("login").setTabCompleter(new LoginCompleter());
        getCommand("register").setTabCompleter(new RegisterCompleter());
    }
}
