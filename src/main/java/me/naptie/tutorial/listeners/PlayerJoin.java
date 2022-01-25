package me.naptie.tutorial.listeners;

import me.naptie.tutorial.Main;
import me.naptie.tutorial.utils.CU;
import org.bukkit.Bukkit;
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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerJoin implements Listener {

    private List<String> lastStr = new ArrayList<>();

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getInstance().getConfig();
        event.setJoinMessage(CU.t(Objects.requireNonNull(config.getString("join-message")).replace("%player%", player.getName())));
        player.sendMessage(CU.t("&aWelcome to our server!"));
        giveItems(player);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("test", "dummy");
        obj.setDisplayName(CU.t("&dWELCOME"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore(CU.t("You: &a" + player.getName())).setScore(6);
        obj.getScore(CU.t("&ewww.phi.zone")).setScore(1);
        player.setScoreboard(scoreboard);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            for (String str : lastStr) {
                scoreboard.resetScores(str);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = CU.t("Time: &a" + sdf.format(new Date()));
            String health = CU.t("Health: &a" + player.getHealth());
            String foodLevel = CU.t("Food: &a" + player.getFoodLevel());
            String saturation = CU.t("Saturation: &a" + player.getSaturation());
            lastStr.add(time);
            lastStr.add(health);
            lastStr.add(foodLevel);
            lastStr.add(saturation);
            obj.getScore(time).setScore(5);
            obj.getScore(health).setScore(4);
            obj.getScore(foodLevel).setScore(3);
            obj.getScore(saturation).setScore(2);
        }, 0, 20);
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
