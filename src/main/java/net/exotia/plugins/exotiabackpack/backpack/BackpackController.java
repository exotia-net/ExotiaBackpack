package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
}
