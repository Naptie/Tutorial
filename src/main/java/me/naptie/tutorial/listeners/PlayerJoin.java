package me.naptie.tutorial.listeners;

import me.naptie.tutorial.utils.CU;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(CU.t("&bWhoosh! " + player.getName() + " has just appeared!"));
        player.sendMessage(CU.t("&aWelcome to our server!"));
        return true;
    }

}
