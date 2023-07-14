package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class BackpackController implements Listener {
    private final BackpackService backpackService;
    private final PluginConfig pluginConfig;

    public BackpackController(BackpackService backpackService, PluginConfig pluginConfig) {
        this.backpackService = backpackService;
        this.pluginConfig = pluginConfig;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Backpack backpack = this.backpackService.findBackpack(player);
        if (backpack == null) {
            this.backpackService.registerBackpack(new Backpack(player, this.pluginConfig));
        }
    }

    @EventHandler
    public void onBackpackClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Backpack backpack = this.backpackService.findBackpack(player);
        if (backpack == null) return;
        if (event.getInventory() != backpack.getInventory()) return;
        backpack.setNeedUpdate(true);
    }

    @EventHandler
    public void onSwapHandItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        Backpack backpack = this.backpackService.findBackpack(player);
        if (backpack == null) return;
        if (!player.isSneaking()) return;
        backpack.openInventory(this.pluginConfig);
        event.setCancelled(true);
    }
}
