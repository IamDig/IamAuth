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
    Player p;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        p = e.getPlayer();
        Location f = e.getFrom();
        Location t = e.getTo();
        if (!Main.getLogged().contains(p.getUniqueId()))
            if (f.getBlockX() != t.getBlockX() || f.getBlockY() != t.getBlockY() || f.getBlockZ() != t.getBlockZ())
                p.teleport(f);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        p = e.getPlayer();
        if (!Main.getLogged().contains(p.getUniqueId()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        p = e.getPlayer();
        if (!Main.getLogged().contains(p.getUniqueId()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        p = e.getPlayer();
        if (!Main.getLogged().contains(p.getUniqueId()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onInv(InventoryClickEvent e) {
        p = (Player) e.getWhoClicked();
        if (!Main.getLogged().contains(p.getUniqueId()))
            e.setCancelled(true);
    }
}
