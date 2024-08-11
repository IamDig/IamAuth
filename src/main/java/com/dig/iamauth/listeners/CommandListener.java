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
    private Main main;

    public CommandListener(Main main) {
        this.main = main;
    }

    private List<String> okCommands = main.getConfig().getStringList("accepted-commands-before-login");

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage().toLowerCase();
        if (Main.getLogged().contains(p.getUniqueId())) return;
        for (String okCmds : okCommands) {
            if (cmd.startsWith(okCmds.toLowerCase())) {
                return;
            } else {
                e.setCancelled(true);
                for (String msg : main.getConfig().getStringList("command-before-login-message")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
    }
}
