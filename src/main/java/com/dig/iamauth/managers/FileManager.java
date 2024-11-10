package com.dig.iamauth.managers;

import com.dig.iamauth.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private Main main;
    private File passwordsfile;
    private YamlConfiguration passwords;

    public FileManager(Main main) {
        this.main = main;
        passwordsfile = new File(main.getDataFolder(), "passwords.yml");
        passwords = YamlConfiguration.loadConfiguration(passwordsfile);
    }

    public void initFiles() {
        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        if (!passwordsfile.exists()) {
            try {
                passwordsfile.createNewFile();
            } catch (IOException e) {
                main.getLogger().severe("Impossible to create passwords file, contact an administrator");
                e.printStackTrace();
            }
        }

        try {
            passwords.load(passwordsfile);
        } catch (InvalidConfigurationException | IOException e) {
            main.getLogger().severe("Error loading passwords file");
            e.printStackTrace();
        }
    }

    public void reloadFiles() {
        try {
            passwords.load(passwordsfile);
            main.getLogger().info("Passwords file reloaded successfully.");
        } catch (IOException | InvalidConfigurationException e) {
            main.getLogger().severe("Error reloading passwords file.");
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            passwords.save(passwordsfile);
        }catch (IOException e) {
            main.getLogger().severe("Error saving passwords file");
            e.printStackTrace();
        }
    }

    public YamlConfiguration getPasswords() { return passwords; }
}
