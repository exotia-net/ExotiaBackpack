package net.exotia.plugins.exotiabackpack;

import net.exotia.plugins.exotiabackpack.backpack.BackpackService;
import net.exotia.plugins.exotiabackpack.configuration.ConfigurationManager;
import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.database.DatabaseService;
import net.exotia.plugins.exotiabackpack.database.providers.MySQLProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExotiaBackpack extends JavaPlugin {
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        ConfigurationManager configurationManager = new ConfigurationManager(this.getDataFolder());
        configurationManager.loadConfigs();
        this.pluginConfig = configurationManager.getPluginConfig();

        BackpackService backpackService = new BackpackService();

        DatabaseService databaseService = new MySQLProvider(this.pluginConfig, backpackService);
        databaseService.connect();
        databaseService.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
