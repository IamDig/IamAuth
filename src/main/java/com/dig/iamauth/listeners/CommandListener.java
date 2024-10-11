package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {
    Player player;
    String command;
    private Main main;
    public CommandListener(Main main) { this.main = main; }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        command = e.getMessage().toLowerCase();
        player = e.getPlayer();
        if (!Main.getLogged().contains(player.getUniqueId())) {
            boolean allowedCommandFound = false;
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
