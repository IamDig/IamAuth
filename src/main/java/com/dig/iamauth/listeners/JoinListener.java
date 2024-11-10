package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import com.dig.iamauth.managers.AuthManager;
import com.dig.iamauth.managers.CacheManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private Main main;
    private AuthManager authManager;

    public JoinListener(Main main) {
        this.main = main;
        this.authManager = new AuthManager(main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (main.getFileManager().getPasswords().contains(player.getUniqueId() + ".password")) {
            if (!CacheManager.isLogged(player)) {
                for (String msg : main.getConfig().getStringList("login-auth-message")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        } else {
            for (String msg : main.getConfig().getStringList("register-command-message")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
}
