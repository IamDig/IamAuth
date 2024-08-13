package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class UnregisterCommand implements CommandExecutor {
    private Main main;
    public UnregisterCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            File file = new File(main.getDataFolder(), "passwords.yml");
            YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
            if (sender.hasPermission("iamauth.unregister")) {
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        modifyFile.set(target.getUniqueId() + " password",null);
                        try {
                            modifyFile.save(file);
                        } catch (IOException e) {
                            main.getLogger().warning("[IamAuth] It was not possible to save passwords.yml");
                        }
                        String reason = main.getConfig().getString("unregister-kick-reason");
                        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
                        for (String msg : main.getConfig().getStringList("unregister-command-message")) {
                            msg = msg.replace("%sender%", sender.getName());
                            msg = msg.replace("%target%", target.getName());
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    } else for (String msg : main.getConfig().getStringList("player-not-found-message")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                } else {
                    for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                        msg = msg.replace("%usage%", main.getConfig().getString("unregister-command-usage"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            } else {
                for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                    msg = msg.replace("%permission%", "iamauth.unregister");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        } else {
            File file = new File(main.getDataFolder(), "passwords.yml");
            YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    modifyFile.set(target.getUniqueId() + " password", null);
                    try {
                        modifyFile.save(file);
                    } catch (IOException e) {
                        main.getLogger().warning("[IamAuth] It was not possible to save passwords.yml");
                    }
                    String reason = main.getConfig().getString("unregister-kick-reason");
                    target.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
                    main.getLogger().warning("[IamAuth] " + target.getName() + " has been unregistered.");
                } else main.getLogger().warning("[IamAuth] Player does not exist or is offline");
            } else main.getLogger().warning("[IamAuth] Invalid command usage. Correct one is: /unregister [player]");
        }
        return false;
    }
}
