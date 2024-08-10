package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class JoinListener implements Listener {
    private Main main;
    public JoinListener(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
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
        int timer = main.getConfig().getInt("login-timer") * 20;
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (!Main.getLogged().contains(player.getUniqueId())) {
                String reason = main.getConfig().getString("timer-expired-kick-reason");
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
            }
        }, timer);
    }
}
