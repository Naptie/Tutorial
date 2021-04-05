package me.naptie.tutorial.commands;

import me.naptie.tutorial.Main;
import me.naptie.tutorial.listeners.PlayerJoin;
import me.naptie.tutorial.utils.CU;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Inv implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			Inventory inv = loadInventory((Player) commandSender);
			((Player) commandSender).openInventory(inv);
		} else {
			commandSender.sendMessage(CU.t("You're not a player!"));
		}
		return true;
	}

	private Inventory loadInventory(Player player) {
		Inventory inv = Bukkit.createInventory(player, 45, CU.t(Main.getInstance().getMenuConfig().getString("menu.title").replace("%player%", player.getName())));
		Map<Integer, Map.Entry<ItemStack, Integer>> stacks = PlayerJoin.loadItems(false);
		for (String id : Main.getInstance().getMenuConfig().getStringList("menu.content")) {
			int i = Integer.parseInt(id);
			inv.setItem(stacks.get(i).getValue(), stacks.get(i).getKey());
		}
		return inv;
	}
}
