package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import com.dig.iamauth.managers.AuthManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnregisterCommand implements CommandExecutor {
    private Main main;
    private AuthManager authManager;

    public UnregisterCommand(Main main) {
        this.main = main;
        this.authManager = new AuthManager(main);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (sender.hasPermission("iamauth.unregister")) {
                if (args.length == 1) {
                    String targetName = args[0];
                    if (Bukkit.getPlayer(targetName) != null) {
                        authManager.unregisterPlayer(sender, targetName);
                    } else {
                        for (String msg : main.getConfig().getStringList("player-not-found-message"))
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
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
            if (args.length == 1) {
                String targetName = args[0];
                authManager.unregisterPlayerConsole(targetName);
            } else {
                main.getLogger().warning("Invalid command usage. Correct one is: /unregister [player]");
            }
        }
        return false;
    }
}
