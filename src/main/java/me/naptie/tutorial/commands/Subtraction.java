package me.naptie.tutorial.commands;

import me.naptie.tutorial.utils.CU;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Subtraction implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(CU.t("&cUsage: /subtraction <number1> <number2> ..."));
            return true;
        }
        if (strings.length < 2) {
            commandSender.sendMessage(CU.t("&cYou must provide at least two integers!"));
            return true;
        }
        try {
            List<Integer> ints = new ArrayList<>();
            for (String string : strings) {
                ints.add(Integer.parseInt(string));
            }
            int result = 0;
            StringBuilder numbers = new StringBuilder(); // 1 - 2 - 3 - 4 - 5
            for (int i : ints) {
                if (numbers.length() == 0) {
                    result = i;
                    numbers = new StringBuilder("&a" + i + "");
                } else {
                    result -= i;
                    if (i >= 0) {
                        numbers.append(" &e- ").append("&a").append(i);
                    } else {
                        numbers.append(" &e- ").append("&a(").append(i).append(")");
                    }
                }
            }
            commandSender.sendMessage(CU.t(numbers + " &b= " + result));
        } catch (NumberFormatException e) {
            commandSender.sendMessage(CU.t("&cPlease enter at least two valid integers!"));
        }
        return true;
    }
}
