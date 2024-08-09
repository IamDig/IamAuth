package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
    private Main main;
    public RegisterCommand(Main main ) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            if (!Main.getRegistered().contains(sender.getUniqueId())) {
                if (args.length == 2) {
                    String password = args[0];
                    String confirmpassword = args[1];
                    if (confirmpassword.equals(password)) {
                        main.getConfig().addDefault(sender.getUniqueId() + " password", password);
                        main.getConfig().options().copyDefaults();
                        main.saveDefaultConfig();
                        Main.getRegistered().add(sender.getUniqueId());
                        Main.getLogged().add(sender.getUniqueId());
                        for (String msg : main.getConfig().getStringList("register-command-message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    } else {

                    }
                }
            } else {

            }
        }
        return false;
    }
}
