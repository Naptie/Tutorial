package me.naptie.tutorial.listeners;

import me.naptie.tutorial.Main;
import me.naptie.tutorial.utils.CU;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public boolean onPlayerQuit(PlayerQuitEvent event) {
        FileConfiguration config = Main.getInstance().getConfig();
        event.setQuitMessage(CU.t(config.getString("quit-message").replace("%player%", event.getPlayer().getName())));
        return true;
    }

}
