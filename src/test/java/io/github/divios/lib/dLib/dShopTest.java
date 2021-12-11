package io.github.divios.lib.dLib;

import io.github.divios.dailyShop.DailyShop;
import io.github.divios.lib.managers.shopsManager;
import io.github.divios.lib.storage.databaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DailyShop.class, Bukkit.class})
public class dShopTest {

    @Mock
    private DailyShop plugin;

    private databaseManager dManager;

    private shopsManager sManager;

    private dShop shop;

    @Mock
    private Inventory inv;
    @Mock
    private Player player;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        Whitebox.setInternalState(DailyShop.class, "INSTANCE", plugin);

        PowerMockito.mockStatic(Bukkit.class);

        Server server = mock(Server.class);
        when(server.getVersion()).thenReturn("git-Paper-386 (MC: 1.17.1)");
        when(Bukkit.getServer()).thenReturn(server);
        when(Bukkit.getVersion()).thenReturn("git-Paper-386 (MC: 1.17.1)");

        dManager = mock(databaseManager.class);
        Whitebox.setInternalState(databaseManager.class, "instance", dManager);

        sManager = mock(shopsManager.class);
        Whitebox.setInternalState(shopsManager.class, "instance", sManager);

        shop = mock(dShop.class);
        when(sManager.getShop("asdf")).thenReturn(Optional.of(shop));

        ItemFactory itemFac = mock(ItemFactory.class);
        when(Bukkit.getItemFactory()).thenReturn(itemFac);
        // Panel inventory
        when(Bukkit.createInventory(any(), Mockito.anyInt(), any())).thenReturn(inv);

        player = mock(Player.class);
        UUID uuid = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);
        when(player.getDisplayName()).thenReturn("Divios");
        when(player.getName()).thenReturn("Divios");

    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    public void test() {

    }

}