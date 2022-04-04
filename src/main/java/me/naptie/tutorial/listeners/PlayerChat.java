package me.naptie.tutorial.listeners;

import me.naptie.tutorial.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

	@EventHandler
	public boolean onPlayerChat(AsyncPlayerChatEvent e) {
		FileConfiguration config = Main.getInstance().getConfig();
		Player sender = e.getPlayer();
		e.getRecipients().clear();
		for (Player online : Bukkit.getOnlinePlayers()) {
			if (online.getLocation().distance(sender.getLocation()) < config.getDouble("max-chat-distance")) {
				e.getRecipients().add(online);
			}
		}
		return true;
	}

}
