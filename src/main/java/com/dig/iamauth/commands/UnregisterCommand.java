package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnregisterCommand implements CommandExecutor {
    private Main main;
    public UnregisterCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (sender.hasPermission("iamauth.unregister")) {
                if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Main.getRegistered().remove(target.getUniqueId());
                        String reason = main.getConfig().getString("unregister-kick-reason");
                        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
                        for (String msg : main.getConfig().getStringList("unregister-command-message")) {
                            msg = msg.replace("%sender%", sender.getName());
                            msg = msg.replace("%target%", target.getName());
                        }
                    } else {
                        for (String msg : main.getConfig().getStringList("player-not-found-message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
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
        }
        return false;
    }
}
