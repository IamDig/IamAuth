package com.dig.iamauth.managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CacheManager {
    private static Set<UUID> logged = new HashSet<>();

    public static void addToLoginQueue(Player player) {
        logged.add(player.getUniqueId());
    }

    public static void logout(Player player) {
        logged.remove(player.getUniqueId());
    }

    public static boolean isLogged(Player player) {
        return logged.contains(player.getUniqueId());
    }

}
