package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    Player player;

    @EventHandler
    public  void onQuit(PlayerQuitEvent e) {
        player = e.getPlayer();
        if (Main.getLogged().contains(player.getUniqueId()))
            Main.getLogged().remove(player.getUniqueId());
    }
}
