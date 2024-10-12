package com.dig.iamauth.commands;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class RegisterCommand implements CommandExecutor {
    Player sender;
    File file;
    YamlConfiguration modifyFile;
    String password;
    String confirmpassword;
    private Main main;

    public RegisterCommand(Main main ) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            file = new File(main.getDataFolder(), "passwords.yml");
            modifyFile = YamlConfiguration.loadConfiguration(file);
            sender = (Player) commandSender;
            if (modifyFile.getString(sender.getUniqueId() + " password") == null)
                if (args.length == 2) {
                    password = args[0];
                    confirmpassword = args[1];
                    if (confirmpassword.equals(password)) {
                        modifyFile.set(sender.getUniqueId() + " password", password);
                        try {
                            modifyFile.save(file);
                        } catch (IOException ex) {
                            main.getLogger().warning("It was not possible to create passwords.yml file");
                        }
                        Main.getLogged().add(sender.getUniqueId());
                        for (String msg : main.getConfig().getStringList("register-command-message"))
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    } else for (String msg : main.getConfig().getStringList("different-passwords-message"))
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                } else for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                    msg = msg.replace("%usage%", main.getConfig().getString("register-command-usage"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            else for (String msg : main.getConfig().getStringList("already-registered-message"))
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
        return false;
    }
}
