package io.github.divios.lib.managers;

import io.github.divios.dailyrandomshop.events.deletedShop;
import io.github.divios.lib.itemHolder.dShop;
import io.github.divios.lib.storage.dataManager;
import org.bukkit.Bukkit;

import java.sql.Timestamp;
import java.util.*;

public class shopsManager {

    private static shopsManager instance = null;
    private HashSet<dShop> shops = new LinkedHashSet<>();

    private shopsManager() {}

    public static shopsManager getInstance() {
        if (instance == null) {
            instance = new shopsManager();
            dataManager.getInstance()
                    .getShops(dShops -> instance.setShops(dShops));
        }
        return instance;
    }

    /**
     * Gets a list of all the shops
     *
     * @return a list of all the shops. Note that the returned list
     * is a copy of the original.
     */
    public synchronized Set<dShop> getShops() {
        return Collections.unmodifiableSet(shops);
    }

    /**
     * Sets the shops. Private
     * @param shops
     */
    private synchronized void setShops(HashSet<dShop> shops) {
        this.shops = shops;
    }

    /**
     * Creates a new shop
     *
     * @param name the name of the shop
     * @param type the type of the shop
     */

    public synchronized void createShop(String name, dShop.dShopT type) {
        dShop newShop = new dShop(name, type);
        shops.add(newShop);
        dataManager.getInstance().createShop(newShop);
    }

    /**
     * Gets a shop by name
     *
     * @param name name of the shop
     * @return shop with the name. Null if it does not exist
     */
    public synchronized Optional<dShop> getShop(String name) {
        for (dShop dS : shops) {
            if (dS.getName().equals(name))
                return Optional.of(dS);
        }
        return Optional.empty();
    }

    /**
     * Deletes a shop by name
     * @param name name of the shop to be deleted
     * @return true if succeeded. False if it does not exist
     */
    public synchronized boolean deleteShop(String name) {

        Optional<dShop> result = getShop(name);

        if (!result.isPresent()) return false;

        deletedShop event = new deletedShop(result.get());

        Bukkit.getPluginManager().callEvent(event);     // throw new event
        if (event.isCancelled()) return false;

        shops.remove(result.get());

        return true;

    }



}