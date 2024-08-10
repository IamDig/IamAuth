package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.jvm.hotspot.opto.MachIfNode;

import javax.swing.text.PlainDocument;

public class LoginCommand implements CommandExecutor {
    private Main main;
    public LoginCommand(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (Main.getRegistered().contains(sender.getUniqueId())) {
                if (!Main.getLogged().contains(sender.getUniqueId())) {
                    if (args.length == 1) {
                        if (args[0].equals(main.getConfig().getString(sender.getUniqueId() + " password"))) {
                            Main.getLogged().add(sender.getUniqueId());
                            for (String msg : main.getConfig().getStringList("login-command-message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        } else {
                            for (String msg : main.getConfig().getStringList("wrong-password-message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
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
            } else {
                for (String msg : main.getConfig().getStringList("register-auth-message")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
        return false;
    }
}
