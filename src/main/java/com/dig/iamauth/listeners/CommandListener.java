package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    private Main main;
    public CommandListener(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!Main.getLogged().contains(e.getPlayer().getUniqueId())) {
            if (!e.getPlayer().performCommand("login") || !e.getPlayer().performCommand("register")) {
                e.setCancelled(true);
                for (String msg : main.getConfig().getStringList("command-before-login")) {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
    }
}
