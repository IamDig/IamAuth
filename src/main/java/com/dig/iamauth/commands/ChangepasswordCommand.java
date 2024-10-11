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

public class ChangepasswordCommand implements CommandExecutor {
    Player sender;
    private Main main;
    public ChangepasswordCommand(Main main) { this.main = main; }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            File file = new File(main.getDataFolder(), "passwords.yml");
            YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
            sender = (Player) commandSender;
            if (args.length == 2)
                if (args[0].equals(modifyFile.getString(sender.getUniqueId() + " password"))) {
                    String oldpass = args[0];
                    String newpass = args[1];
                    if (!newpass.equals(oldpass)) {
                        modifyFile.set(sender.getUniqueId() + " password", newpass);
                        try {
                            modifyFile.save(file);
                        } catch (IOException ex) {
                            main.getLogger().warning("It was not possible to save passwords.yml file");
                        }
                        String reason = main.getConfig().getString("changepassword-kick-reason");
                        sender.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
                    } else for (String msg : main.getConfig().getStringList("newpassword-equal-to-oldpassword-message"))
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            else for (String msg : main.getConfig().getStringList("invalid-command-usage")) {
                msg = msg.replace("%usage%", main.getConfig().getString("changepassword-command-usage"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
        return false;
    }
}
