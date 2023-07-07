package net.exotia.plugins.exotiabackpack.configuration.providers;

import net.exotia.plugins.exotiabackpack.configuration.AbstractConfigWithResource;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.DatabaseSection;

import java.io.File;

public class PluginConfig extends AbstractConfigWithResource {
    public PluginConfig(File folder, String child) {
        super(folder, child);
    }

    public String backpackGuiTitle = "Plecak";
    public DatabaseSection database = new DatabaseSection();
}
