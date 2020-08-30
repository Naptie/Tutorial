package me.naptie.tutorial;

import me.naptie.tutorial.commands.Addition;
import me.naptie.tutorial.commands.Subtraction;
import me.naptie.tutorial.listeners.PlayerJoin;
import me.naptie.tutorial.listeners.PlayerQuit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private Logger logger;
    private static Main instance;
    private PluginDescriptionFile descriptionFile;

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
            logger.info("[" + user + "] Level: " + getConfig().getInt("users." + user + ".level") + "; Age: " + getConfig().getInt("users." + user + ".age") + "; Gender: " + getConfig().getString("users." + user + ".gender"));
        }
    }

    private void registerConfig() {
        saveDefaultConfig();
    }

    public static Main getInstance() {
        return instance;
    }
}
