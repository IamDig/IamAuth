package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthCommand implements CommandExecutor {
    private Main main;
    public AuthCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            switch (args[0]) {
                case "reload":
                    if (commandSender instanceof Player) {
                        Player sender = (Player) commandSender;
                        if (sender.hasPermission("iamauth.reloadconfig")) {
                            main.reloadConfig();
                            main.getLogger().warning("[IamAuth] Config Reloaded");
                            for (String msg : main.getConfig().getStringList("config-reload-message")) {
                                msg = msg.replace("Server", Bukkit.getServerName());
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        } else {
                            for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                                msg = msg.replace("%permission%", "iamauth.reloadconfig");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        }
                    } else {
                        main.reloadConfig();
                        main.getLogger().warning("[IamAuth] Config Reloaded");
                    }
                    break;

                case "help":
                    if (commandSender instanceof Player) {
                        Player sender = (Player) commandSender;
                        if (sender.hasPermission("iamauth.help")) {
                            for (String msg : main.getConfig().getStringList("help-message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        } else {
                            for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                                msg = msg.replace("%permission%", "iamauth.help");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
