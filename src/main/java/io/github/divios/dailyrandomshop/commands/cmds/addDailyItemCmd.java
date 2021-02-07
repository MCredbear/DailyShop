package io.github.divios.dailyrandomshop.commands.cmds;

import io.github.divios.dailyrandomshop.guis.settings.addDailyItemGuiIH;
import io.github.divios.dailyrandomshop.utils.utils;
import org.bukkit.entity.Player;

import java.util.List;

public class addDailyItemCmd implements dailyCommand{
    @Override
    public void run(Player p) {
        if (!p.hasPermission("DailyRandomShop.addDailyItem")) {
            utils.noPerms(p);
            return;
        }
        addDailyItemGuiIH.openInventory(p);
    }

    @Override
    public void help(Player p) {
        if (p.hasPermission("DailyRandomShop.addDailyItem")) {
            p.sendMessage(utils.formatString("&6&l>> &6/rdshop addDailyItems &8 " +
                    "- &7Opens the menu to add an item"));
        }
    }

    @Override
    public void command(Player p, List<String> s) {
        if (p.hasPermission("DailyRandomShop.addDailyItem")) {
            s.add("addDailyItem");
        }
    }
}