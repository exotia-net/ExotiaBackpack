package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BackpackService {
    private final List<Backpack> backpacks = new ArrayList<>();

    public void registerBackpack(Backpack backpack) {
        this.backpacks.add(backpack);
    }
    public void createUser(Player player, PluginConfig pluginConfig) {
        this.registerBackpack(new Backpack(player, pluginConfig));
    }

    public Backpack findBackpack(Player player) {
        return this.backpacks.stream().filter(backpack -> backpack.getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }
    public List<Backpack> findBackpacks(boolean needUpdate) {
        return this.backpacks.stream().filter(backpack -> backpack.isNeedUpdate() == needUpdate).toList();
    }
}
