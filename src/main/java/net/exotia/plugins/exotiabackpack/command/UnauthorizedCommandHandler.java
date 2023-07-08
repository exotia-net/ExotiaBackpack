package net.exotia.plugins.exotiabackpack.command;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.LiteCommandsSection;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class UnauthorizedCommandHandler implements PermissionHandler<CommandSender> {
    private final LiteCommandsSection liteCommands;
    private final MiniMessage miniMessage = MiniMessage.builder().build();

    public UnauthorizedCommandHandler(LiteCommandsSection liteCommands) {
        this.liteCommands = liteCommands;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        sender.sendMessage(this.miniMessage.deserialize(this.liteCommands.noPermission
                .replace("{permissions}", String.join(", ", requiredPermissions.getPermissions()))
        ));
    }
}
