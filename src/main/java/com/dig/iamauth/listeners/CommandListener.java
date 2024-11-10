package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import com.dig.iamauth.managers.CacheManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    boolean allowedCommandFound;
    Player player;
    String command;
    private Main main;

    public CommandListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        command = e.getMessage().toLowerCase();
        player = e.getPlayer();
        if (!CacheManager.isLogged(player)) {
            allowedCommandFound = false;
            for (String str : main.getConfig().getStringList("accepted-commands-before-login"))
                if (command.contains(str))
                    allowedCommandFound = true;
            if (!allowedCommandFound) {
                for (String msg : main.getConfig().getStringList("command-before-login-message"))
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                e.setCancelled(true);
            }
        }
    }
}
