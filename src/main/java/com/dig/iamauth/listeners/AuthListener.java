package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AuthListener implements Listener {
    private Main main;
    public AuthListener(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location f = e.getFrom();
        Location t = e.getTo();
        if (!Main.getLogged().contains(p.getUniqueId())) {
            if (f != t) {
                p.teleport(f);
            }
        }
    }
}
