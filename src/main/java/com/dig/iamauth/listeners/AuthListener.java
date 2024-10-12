package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
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
    Player player;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        player = e.getPlayer();
        Location f = e.getFrom();
        Location t = e.getTo();
        if (!Main.getLogged().contains(player.getUniqueId()))
            if (f.getBlockX() != t.getBlockX() || f.getBlockY() != t.getBlockY() || f.getBlockZ() != t.getBlockZ())
                player.teleport(f);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        player = e.getPlayer();
        if (!Main.getLogged().contains(player.getUniqueId()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        player = e.getPlayer();
        if (!Main.getLogged().contains(player.getUniqueId()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        player = e.getPlayer();
        if (!Main.getLogged().contains(player.getUniqueId()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onInv(InventoryClickEvent e) {
        player = (Player) e.getWhoClicked();
        if (!Main.getLogged().contains(player.getUniqueId()))
            e.setCancelled(true);
    }
}
