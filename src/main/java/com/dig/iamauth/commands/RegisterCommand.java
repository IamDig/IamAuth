package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import com.dig.iamauth.managers.AuthManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
    private Main main;
    private AuthManager authManager;

    public RegisterCommand(Main main) {
        this.main = main;
        this.authManager = new AuthManager(main);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (args.length == 2) {
                String password = args[0];
                String confirmpassword = args[1];
                authManager.register(sender, password, confirmpassword);
            } else {
                for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                    msg = msg.replace("%usage%", main.getConfig().getString("register-command-usage"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
        return false;
    }
}
