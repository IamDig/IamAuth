package com.dig.iamauth.listeners;

import com.dig.iamauth.managers.CacheManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (CacheManager.isLogged(player)) {
            CacheManager.logout(player);
        }
    }
}
