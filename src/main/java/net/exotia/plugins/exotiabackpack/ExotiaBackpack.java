package net.exotia.plugins.exotiabackpack;

import net.exotia.plugins.exotiabackpack.configuration.ConfigurationManager;
import net.exotia.plugins.exotiabackpack.database.DatabaseService;
import net.exotia.plugins.exotiabackpack.database.providers.MySQLProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExotiaBackpack extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationManager configurationManager = new ConfigurationManager(this.getDataFolder());
        configurationManager.loadConfigs();

        DatabaseService databaseService = new MySQLProvider();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
