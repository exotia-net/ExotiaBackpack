package net.exotia.plugins.exotiabackpack.configuration;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnException;
import net.dzikoysk.cdn.CdnFactory;
import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import panda.std.Blank;
import panda.std.Result;

import java.io.File;

public class ConfigurationManager {
    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
//            .withComposer(Component.class, new ComponentComposer())
//            .withComposer(Material.class, new MaterialComposer())
//            .withComposer(SimpleDateFormat.class, new DateFormatComposer())
            .build();

    private final PluginConfig pluginConfig;

    public ConfigurationManager(File dataFolder) {
        this.pluginConfig = new PluginConfig(dataFolder, "config.yml");
    }

    public Result<Blank, CdnException> loadConfigs() {
        return Result.<CdnException>ok()
                .flatMap(blank -> this.load(this.pluginConfig))
                .mapToBlank();
    }

    public <T extends ConfigWithResource> Result<T, CdnException> load(T config) {
        return cdn.load(config.getResource(), config)
                .flatMap(load -> cdn.render(config, config.getResource()))
                .map(render -> config);
    }

    public <T extends ConfigWithResource> void save(T config) {
        cdn.render(config, config.getResource());
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }
}
