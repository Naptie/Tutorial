package me.naptie.tutorial;

import me.naptie.tutorial.commands.Addition;
import me.naptie.tutorial.commands.Inv;
import me.naptie.tutorial.commands.Subtraction;
import me.naptie.tutorial.listeners.PlayerJoin;
import me.naptie.tutorial.listeners.PlayerQuit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private Logger logger;
    private static Main instance;
    private PluginDescriptionFile descriptionFile;
    private FileConfiguration menuConfig, itemConfig;

    @Override
    public void onEnable() {
        logger = getLogger();
        instance = this;
        descriptionFile = getDescription();
        registerConfig();
        test();
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        Objects.requireNonNull(getCommand("addition")).setExecutor(new Addition());
        Objects.requireNonNull(getCommand("subtraction")).setExecutor(new Subtraction());
        Objects.requireNonNull(getCommand("inventory")).setExecutor(new Inv());
        logger.info("Enabled " + descriptionFile.getName() + " v" + descriptionFile.getVersion());
    }

    @Override
    public void onDisable() {
        logger.info("Disabled " + descriptionFile.getName() + " v" + descriptionFile.getVersion());
        logger = null;
    }

    private void test() {
        List<String> mobs = getConfig().getStringList("mobs");
        for (String mob : mobs) {
            logger.info(mob);
        }
        ConfigurationSection users = getConfig().getConfigurationSection("users");
        for (String user : users.getKeys(false)) {
            logger.info(user);
            logger.info("[" + user + "] Level: " + getConfig().getInt("users." + user + ".level") + "; Age: " + getConfig().getInt("users." + user + ".age") + "; Gender: " + getConfig().getString("users." + user + ".gender"));
        }
        for (String user : users.getKeys(true)) {
            logger.info(user);
        }
    }

    private void registerConfig() {
        saveDefaultConfig();
        File menuFile = new File(getDataFolder(), "menus.yml");
        File itemFile = new File(getDataFolder(), "items.yml");
        if (!menuFile.exists()) saveResource("menus.yml", true);
        if (!itemFile.exists()) saveResource("items.yml", true);
        menuConfig = YamlConfiguration.loadConfiguration(menuFile);
        itemConfig = YamlConfiguration.loadConfiguration(itemFile);
    }

    public static Main getInstance() {
        return instance;
    }

    public FileConfiguration getMenuConfig() {
        return menuConfig;
    }

    public FileConfiguration getItemConfig() {
        return itemConfig;
    }
}
