package com.dig.iamauth.managers;

import com.dig.iamauth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;

public class AuthManager {

    private Main main;
    private YamlConfiguration file;

    public AuthManager(Main main) {
        this.main = main;
        file = main.getFileManager().getPasswords();
    }

    public void login(Player player) {
        if (!CacheManager.isLogged(player)) {
            if (file.contains(player.getUniqueId() + ".password")) {
                for (String msg : main.getConfig().getStringList("login-auth-message")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
                CacheManager.addToLoginQueue(player);
            } else {
                for (String msg : main.getConfig().getStringList("register-auth-message")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
    }



    public void register(Player player, String password, String confirmpassword) {
        YamlConfiguration modifyFile = main.getFileManager().getPasswords();
        if (modifyFile.contains(player.getUniqueId() + ".password")) {
            for (String msg : main.getConfig().getStringList("already-registered-message")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        } else {
            if (confirmpassword.equals(password)) {
                modifyFile.set(player.getUniqueId() + ".password", password);
                main.getFileManager().saveFile();
                CacheManager.addToLoginQueue(player);

                for (String msg : main.getConfig().getStringList("register-command-message")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            } else {
                for (String msg : main.getConfig().getStringList("different-passwords-message")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
    }


    public void unregisterPlayer(Player sender, String targetName) {
        if (sender.hasPermission("iamauth.unregister")) {
            if (Bukkit.getPlayer(targetName) != null) {
                Player target = Bukkit.getPlayer(targetName);
                if (file.contains(target.getUniqueId() + ".password")) {
                    file.set(target.getUniqueId() + ".password", null);
                    main.getFileManager().saveFile();
                    CacheManager.logout(sender);
                    target.kickPlayer(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("unregister-kick-reason")));
                    for (String msg : main.getConfig().getStringList("unregister-command-message")) {
                        msg = msg.replace("%sender%", sender.getName());
                        msg = msg.replace("%target%", target.getName());
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Player is not registered.");
                }
            } else {
                for (String msg : main.getConfig().getStringList("player-not-found-message"))
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        } else {
            for (String msg : main.getConfig().getStringList("missing-permission-message")) {
                msg = msg.replace("%permission%", "iamauth.unregister");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }

    public void unregisterPlayerConsole(String targetName) {
        File file = new File(main.getDataFolder(), "passwords.yml");
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);

        if (Bukkit.getPlayer(targetName) != null) {
            Player target = Bukkit.getPlayer(targetName);
            modifyFile.set(target.getUniqueId() + ".password", null);
            main.getFileManager().saveFile();
            target.kickPlayer(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("unregister-kick-reason")));
            main.getLogger().warning(target.getName() + " has been unregistered.");
        } else {
            main.getLogger().warning("Player does not exist or is offline");
        }
    }
}
