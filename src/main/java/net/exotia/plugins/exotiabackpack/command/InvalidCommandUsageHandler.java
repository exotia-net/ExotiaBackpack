package net.exotia.plugins.exotiabackpack.command;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.LiteCommandsSection;
import net.kyori.adventure.text.Component;
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

        sender.sendMessage(this.miniMessage.deserialize(this.liteCommands.invalidCommand.replace("{command}", schematics.get(0))));
//        if (schematics.size() == 1) {
//            this.messageService.send(sender, new Variables(this.messages.getInvalidCommand()).add("command", schematics.get(0)).process());
//            return;
//        }
//
//        this.messageService.send(sender, this.messages.getInvalidCommandHeader());
//        for (String sch : schematics) {
//            this.messageService.send(sender, new Variables(this.messages.getHelperLine()).add("schema", sch).process());
//        }
    }
}