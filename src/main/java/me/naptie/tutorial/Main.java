package me.naptie.tutorial;

import me.naptie.tutorial.commands.Addition;
import me.naptie.tutorial.commands.Subtraction;
import me.naptie.tutorial.listeners.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private Logger logger;

    public void onEnable() {
        logger = getLogger();
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        Objects.requireNonNull(getCommand("addition")).setExecutor(new Addition());
        Objects.requireNonNull(getCommand("subtraction")).setExecutor(new Subtraction());
        logger.info("Enabled Tutorial v1.0.0-SNAPSHOT");
    }

    public void onDisable() {
        logger.info("Disabled Tutorial v1.0.0-SNAPSHOT");
        logger = null;
    }
}
