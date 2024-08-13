package com.dig.iamauth.listeners;

import com.dig.iamauth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinListener implements Listener {

    private Main main;

    public JoinListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (isPremiumPlayer(player))
            if (isPremiumSession(player)) spLogin(player);
            else premiumLogin(player);
            else spLogin(player);
    }

    private boolean isPremiumPlayer(Player player) {
        try {
            String playerName = player.getName();
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isPremiumSession(Player player) {
        try {
            String playerName = player.getName();
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();


                String response = content.toString();
                return response.contains(player.getUniqueId().toString().replace("-", ""));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void spLogin(Player player) {
        File file = new File(main.getDataFolder(), "passwords.yml");
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        if (!Main.getLogged().contains(player.getUniqueId())) {
            if (modifyFile.getString(player.getUniqueId() + " password") != null)
                for (String msg : main.getConfig().getStringList("login-auth-message")) player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            else for (String msg : main.getConfig().getStringList("register-auth-message")) player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
        int timer = main.getConfig().getInt("login-timer") * 20;
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (!Main.getLogged().contains(player.getUniqueId())) {
                String reason = main.getConfig().getString("timer-expired-kick-reason");
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', reason));
            }
        }, timer);
    }
    private void premiumLogin(Player player) {
        Main.getLogged().add(player.getUniqueId());
        for (String msg : main.getConfig().getStringList("premium-welcome-message")) player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
