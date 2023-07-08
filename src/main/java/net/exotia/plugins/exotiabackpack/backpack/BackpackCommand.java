package net.exotia.plugins.exotiabackpack.backpack;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import org.bukkit.entity.Player;

@Route(name = "backpack")
public class BackpackCommand {
    private final BackpackService backpackService;
    private final PluginConfig pluginConfig;

    public BackpackCommand(BackpackService backpackService, PluginConfig pluginConfig) {
        this.backpackService = backpackService;
        this.pluginConfig = pluginConfig;
    }

    @Execute
    public void execute(Player player) {
        Backpack backpack = this.backpackService.findBackpack(player);
        if (backpack == null) {
            this.backpackService.createUser(player, this.pluginConfig);
        }
        backpack.openInventory(this.pluginConfig);
    }

//    @Execute(route = "upgrade")
//    @Permission("exotia.backpacks.admin")
//    public void upgrade(P)
}
