package net.exotia.plugins.exotiabackpack.backpack;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BackpackController implements Listener {
    private final BackpackService backpackService;

    public BackpackController(BackpackService backpackService) {
        this.backpackService = backpackService;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Backpack backpack = this.backpackService.findBackpack(player);
        if (backpack == null) {
            this.backpackService.registerBackpack(new Backpack(player));
        }
    }
}
