package net.exotia.plugins.exotiabackpack.configuration.providers;

import net.exotia.plugins.exotiabackpack.configuration.AbstractConfigWithResource;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.DatabaseSection;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.LiteCommandsSection;

import java.io.File;
import java.util.Map;

public class PluginConfig extends AbstractConfigWithResource {
    public PluginConfig(File folder, String child) {
        super(folder, child);
    }

    public String backpackGuiTitle = "Plecak";
    public String backpackCommand = "plecak";
    public DatabaseSection database = new DatabaseSection();
    public LiteCommandsSection liteCommands = new LiteCommandsSection();
    public String backpackDowngrade = "鼅 <white>Twój plecak zostal pomniejszony, itemy wypadly na ziemie!";
    public String titlePrefix = "<white>✟";
    public Map<Integer, String> guiTitles = Map.of(1, "꼀", 2, "꼁", 3, "꼂", 4, "꼃", 5, "꼄", 6, "꼅");
}
