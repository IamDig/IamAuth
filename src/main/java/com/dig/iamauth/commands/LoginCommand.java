package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import com.dig.iamauth.managers.AuthManager;
import com.dig.iamauth.managers.CacheManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {
    private Main main;
    private AuthManager authManager;

    public LoginCommand(Main main) {
        this.main = main;
        this.authManager = new AuthManager(main);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (!CacheManager.isLogged(sender)) {
                if (args.length == 1) {
                    if (main.getFileManager().getPasswords().contains(sender.getUniqueId() + ".password")) {
                        if (args[0].equals(main.getFileManager().getPasswords().getString(sender.getUniqueId().toString() + ".password"))) {
                            CacheManager.addToLoginQueue(sender);
                            for (String msg : main.getConfig().getStringList("login-command-message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        } else {
                            if (main.getConfig().getBoolean("kick-if-password-wrong")) {
                                String reason = main.getConfig().getString("wrong-password-kick-reason");
                                sender.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
                            } else {
                                for (String msg : main.getConfig().getStringList("wrong-password-message")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                                }
                            }
                        }
                    } else {
                        authManager.login(sender);
                    }
                } else {
                    for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                        msg = msg.replace("%usage%", main.getConfig().getString("login-command-usage"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            } else {
                for (String msg : main.getConfig().getStringList("already-logged-message")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
        return false;
    }
}
