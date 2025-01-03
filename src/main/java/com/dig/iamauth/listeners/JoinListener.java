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
    String reason;
    File file;
    YamlConfiguration modifyFile;
    Player player;
    int timer;
    private Main main;

    public JoinListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        player = e.getPlayer();
        login(player);
    }

    private void login(Player player) {
        file = new File(main.getDataFolder(), "passwords.yml");
        modifyFile = YamlConfiguration.loadConfiguration(file);
        if (!Main.getLogged().contains(player.getUniqueId()))
            if (modifyFile.getString(player.getUniqueId() + " password") != null)
                for (String msg : main.getConfig().getStringList("login-auth-message"))
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            else for (String msg : main.getConfig().getStringList("register-auth-message"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        timer = main.getConfig().getInt("login-timer") * 20;
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (!Main.getLogged().contains(player.getUniqueId())) {
                reason = main.getConfig().getString("timer-expired-kick-reason");
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
            }
        }, timer);
    }

}
