package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import com.dig.iamauth.managers.CacheManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private Main main;

    public ChatListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!CacheManager.isLogged(player)) {
            e.setCancelled(true);
            for (String msg : main.getConfig().getStringList("chatting-before-login-message"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}
