package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthCommand implements CommandExecutor {
    Player sender;
    private Main main;
    public AuthCommand(Main main) { this.main = main; }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            switch (args[0]) {
                case "reload":
                    if (commandSender instanceof Player) {
                        sender = (Player) commandSender;
                        if (sender.hasPermission("iamauth.reloadconfig")) {
                            reloadConfig();
                            for (String msg : main.getConfig().getStringList("config-reload-message"))
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else {
                            for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                                msg = msg.replace("%permission%", "iamauth.reloadconfig");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        }
                    } else reloadConfig();
                    break;
                case "help":
                    if (commandSender instanceof Player) {
                        sender = (Player) commandSender;
                        if (sender.hasPermission("iamauth.help"))
                            for (String msg : main.getConfig().getStringList("help-message"))
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        else for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                            msg = msg.replace("%permission%", "iamauth.help");
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    }
                    break;
                default:
                    if (commandSender instanceof Player) {
                        sender = (Player) commandSender;
                        for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                            msg = msg.replace("%usage%", main.getConfig().getString("auth-command-usage"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    }
            }
        } else {
            if (commandSender instanceof Player) {
                sender = (Player) commandSender;
                for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                    msg = msg.replace("%usage%", main.getConfig().getString("auth-command-usage"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
        return false;
    }

    void reloadConfig() {
        main.reloadConfig();
        main.getLogger().warning("Config Reloaded");
    }
}
