package com.dig.iamauth.listeners;

import com.dig.iamauth.managers.CacheManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AuthListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!CacheManager.isLogged(player)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!CacheManager.isLogged(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (!CacheManager.isLogged(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (!CacheManager.isLogged(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!CacheManager.isLogged(player)) {
            e.setCancelled(true);
        }
    }
}
