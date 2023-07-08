package net.exotia.plugins.exotiabackpack.configuration.providers;

import net.exotia.plugins.exotiabackpack.configuration.AbstractConfigWithResource;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.DatabaseSection;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.LiteCommandsSection;

import java.io.File;

public class PluginConfig extends AbstractConfigWithResource {
    public PluginConfig(File folder, String child) {
        super(folder, child);
    }

    public String backpackGuiTitle = "Plecak";
    public String backpackCommand = "plecak";
    public DatabaseSection database = new DatabaseSection();
    public LiteCommandsSection liteCommands = new LiteCommandsSection();
}
