package me.naptie.tutorial.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CU { // ChatUtils

    public static String t(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> tl(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String string : list) {
            result.add(t(string));
        }
        return result;
    }

}
