package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private Main main;
    public JoinListener(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (Main.getPremium().contains(player.getUniqueId())) {
            Main.getLogged().add(player.getUniqueId());
            for (String msg : main.getConfig().getStringList("premium-auth-message")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        } else {
            if (!Main.getLogged().contains(player.getUniqueId())) {
                if (Main.getRegistered().contains(player.getUniqueId())) {
                    for (String msg : main.getConfig().getStringList("login-auth-message")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                } else {
                    for (String msg : main.getConfig().getStringList("register-auth-message")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            }
        }
    }
}
