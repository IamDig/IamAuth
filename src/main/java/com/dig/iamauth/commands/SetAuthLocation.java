package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetAuthLocation implements CommandExecutor {
    private Main main;
    public SetAuthLocation(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (sender.hasPermission("iamauth.setauthlocation")) {
                Main.getAuthLocation().setX(sender.getLocation().getBlockX());
                Main.getAuthLocation().setY(sender.getLocation().getBlockY());
                Main.getAuthLocation().setZ(sender.getLocation().getBlockZ());
                for (String msg : main.getConfig().getStringList("set-auth-location-message")) {
                    msg = msg.replace("%x%", "" + Main.getAuthLocation().getBlockX());
                    msg = msg.replace("%y%", "" + Main.getAuthLocation().getBlockY());
                    msg = msg.replace("%z%", "" + Main.getAuthLocation().getBlockZ());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            } else for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                msg = msg.replace("%permission%", "iamauth.setauthlocation");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
        return false;
    }
}
