package net.exotia.plugins.exotiabackpack;

import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import net.exotia.plugins.exotiabackpack.backpack.BackpackCommand;
import net.exotia.plugins.exotiabackpack.backpack.BackpackController;
import net.exotia.plugins.exotiabackpack.backpack.BackpackService;
import net.exotia.plugins.exotiabackpack.backpack.BackpackUpdateTask;
import net.exotia.plugins.exotiabackpack.configuration.ConfigurationManager;
import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.database.DatabaseService;
import net.exotia.plugins.exotiabackpack.database.providers.MySQLProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

        this.getServer().getPluginManager().registerEvents(new BackpackController(backpackService), this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new BackpackUpdateTask(backpackService, databaseService), 60, 60);

        LiteBukkitFactory.builder(this.getServer(), this.getName())
                // Arguments
                .argument(Player.class, new BukkitPlayerArgument<>(this.getServer(), "&cNie ma takiego gracza!"))
                // Contextual Bind
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("&cKomenda tylko dla gracza!"))

                .commandInstance(new BackpackCommand(backpackService))
                .commandEditor(BackpackCommand.class, editor -> editor.name(this.pluginConfig.backpackCommand))

//                .invalidUsageHandler()

                .register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
