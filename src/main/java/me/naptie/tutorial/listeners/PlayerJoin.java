package me.naptie.tutorial.listeners;

import me.naptie.tutorial.Main;
import me.naptie.tutorial.utils.CU;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoin implements Listener {

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getInstance().getConfig();
        event.setJoinMessage(CU.t(Objects.requireNonNull(config.getString("join-message")).replace("%player%", player.getName())));
        player.sendMessage(CU.t("&aWelcome to our server!"));
        return true;
    }

}
