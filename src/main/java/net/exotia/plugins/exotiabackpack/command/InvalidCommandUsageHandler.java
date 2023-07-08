package net.exotia.plugins.exotiabackpack.command;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.LiteCommandsSection;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {
    private final LiteCommandsSection liteCommands;
    private final MiniMessage miniMessage = MiniMessage.builder().build();

    public InvalidCommandUsageHandler(LiteCommandsSection liteCommands) {
        this.liteCommands = liteCommands;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        if (schematics.size() == 1) {
            sender.sendMessage(this.miniMessage.deserialize(this.liteCommands.invalidCommand.replace("{command}", schematics.get(0))));
            return;
        }
        sender.sendMessage(this.miniMessage.deserialize(this.liteCommands.invalidCommandHeader));
        for (String sch : schematics) {
            sender.sendMessage(this.miniMessage.deserialize(this.liteCommands.helperLine.replace("{schema}", sch)));
        }
    }
}