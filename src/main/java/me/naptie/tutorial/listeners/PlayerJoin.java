package me.naptie.tutorial.listeners;

import me.naptie.tutorial.Main;
import me.naptie.tutorial.utils.CU;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayerJoin implements Listener {

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getInstance().getConfig();
        event.setJoinMessage(CU.t(Objects.requireNonNull(config.getString("join-message")).replace("%player%", player.getName())));
        player.sendMessage(CU.t("&aWelcome to our server!"));
        giveItems(player);
        return true;
    }

    private boolean hasGivenTo(Player player, ItemStack stack) {
        PlayerInventory inv = player.getInventory();
        return inv.contains(stack);
    }

    private void giveItems(Player player) {
        Map<Integer, Map.Entry<ItemStack, Integer>> stacks = loadItems(true);
        for (int i : stacks.keySet()) {
            ItemStack stack = stacks.get(i).getKey();
            if (hasGivenTo(player, stack)) continue;
            player.getInventory().setItem(stacks.get(i).getValue(), stack);
        }
    }

    public static Map<Integer, Map.Entry<ItemStack, Integer>> loadItems(boolean spawn) {
        Map<Integer, Map.Entry<ItemStack, Integer>> stacks = new HashMap<>();
        Configuration config = Main.getInstance().getItemConfig();
        for (String id : config.getConfigurationSection("items").getKeys(false)) {
            if (!spawn || config.getBoolean("items." + id + ".spawn")) {
                Material material = Material.valueOf(config.getString("items." + id + ".type"));
                String name = CU.t(config.getString("items." + id + ".name"));
                int amount = config.getInt("items." + id + ".amount");
                int slot = config.getInt("items." + id + ".slot");
                List<String> lore = CU.tl(config.getStringList("items." + id + ".lore"));
                ItemStack stack = new ItemStack(material, amount);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(name);
                meta.setLore(lore);
                stack.setItemMeta(meta);
                Map.Entry<ItemStack, Integer> entry = new Map.Entry<ItemStack, Integer>() {
                    @Override
                    public ItemStack getKey() {
                        return stack;
                    }

                    @Override
                    public Integer getValue() {
                        return slot;
                    }

                    @Override
                    public Integer setValue(Integer value) {
                        return 0;
                    }
                };
                stacks.put(Integer.parseInt(id), entry);
            }
        }
        return stacks;
    }

}
