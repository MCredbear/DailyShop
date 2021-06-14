package io.github.divios.dailyrandomshop.commands;

import io.github.divios.core_lib.commands.abstractCommand;
import io.github.divios.core_lib.commands.cmdTypes;
import io.github.divios.core_lib.misc.FormatUtils;
import io.github.divios.dailyrandomshop.hooks.hooksManager;
import io.github.divios.dailyrandomshop.utils.utils;
import io.github.divios.lib.itemHolder.dItem;
import io.github.divios.lib.itemHolder.dShop;
import io.github.divios.lib.managers.shopsManager;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.api.BossShopAPI;
import org.black_ixx.bossshop.core.BSShop;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class importShops extends abstractCommand {

    public importShops() { super(cmdTypes.BOTH); }

    @Override
    public String getName() {
        return "import";
    }

    @Override
    public boolean validArgs(List<String> args) {

        if (args.size() < 3) return false;

        if (args.get(0).equalsIgnoreCase("shopGui+"))
            if (!utils.isOperative("ShopGUIPlus")) return false;

        if (args.get(0).equalsIgnoreCase("BossShopPro"))
            if (!utils.isOperative("BossShopPro")) return false;

        return (args.get(0).equalsIgnoreCase("shopGui+")
                || args.get(0).equalsIgnoreCase("BossShop")) &&
                shopsManager.getInstance().getShop(args.get(1)).isPresent();

    }

    @Override
    public String getHelp() {
        return FormatUtils.color("&6&l>> &6/rdshop import {plugin} {shop} {action} {flags} &8 " +
                "- &7Imports the given shop to sell/buy shops");
    }

    @Override
    public List<String> getPerms() {
        return Collections.singletonList("DailyRandomShop.import");
    }

    @Override
    public List<String> getTabCompletition(List<String> args) {
        if (args.size() == 1)
            return Arrays.asList("shopGui+", "BossShop");
        else if (args.size() == 2)
            return shopsManager.getInstance().getShops().stream()
                    .map(dShop::getName).collect(Collectors.toList());
        else if (args.size() == 3)
            if (args.get(0).equalsIgnoreCase("shopGui+") && utils.isOperative("ShopGUIPlus"))
                return List.copyOf(ShopGuiPlusApi.getPlugin().getShopManager().shops.keySet());
            else if (utils.isOperative("BossShopPro"))
                return ((BossShop) Bukkit.getPluginManager().getPlugin("BossShopPro")).getAPI()
                .getAllShopItems().keySet().stream().map(BSShop::getShopName).collect(Collectors.toList());

        return Collections.emptyList();
    }

    @Override
    public void run(CommandSender sender, List<String> args) {

        shopsManager.getInstance().getShop(args.get(1))
                .ifPresent(shop -> {
                    if (args.get(0).equalsIgnoreCase("shopGui+")) {
                        ShopGuiPlusApi.getShop(args.get(2)).getShopItems()
                                .forEach(shopItem -> shop.addItem(dItem.of(shopItem.getItem())));
                    } else
                        ((BossShop) Bukkit.getPluginManager().getPlugin("BossShopPro")).getAPI()
                                .getShop(args.get(2)).getItems()
                                .forEach(bsBuy -> shop.addItem(dItem.of(bsBuy.getItem())));
                });
    }
}