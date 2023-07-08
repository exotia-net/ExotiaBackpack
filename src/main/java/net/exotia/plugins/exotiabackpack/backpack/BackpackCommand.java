package net.exotia.plugins.exotiabackpack.backpack;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "backpack")
public class BackpackCommand {
    private final BackpackService backpackService;

    public BackpackCommand(BackpackService backpackService) {
        this.backpackService = backpackService;
    }

    @Execute
    public void execute(Player player) {
        Backpack backpack = this.backpackService.findBackpack(player);
        if (backpack == null) {
            this.backpackService.createUser(player);
        }
        backpack.openInventory();
    }
}
