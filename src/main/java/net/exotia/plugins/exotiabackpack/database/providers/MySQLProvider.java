package net.exotia.plugins.exotiabackpack.database.providers;

import com.zaxxer.hikari.HikariConfig;
import net.exotia.plugins.exotiabackpack.backpack.Backpack;
import net.exotia.plugins.exotiabackpack.backpack.BackpackService;
import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.DatabaseSection;
import net.exotia.plugins.exotiabackpack.database.DatabaseService;
import com.zaxxer.hikari.HikariDataSource;
import net.exotia.plugins.exotiabackpack.utils.ItemStackSerializer;

import java.sql.*;

public class MySQLProvider implements DatabaseService {
    private final DatabaseSection database;
    private final PluginConfig pluginConfig;
    private final BackpackService backpackService;
    private HikariDataSource hikariDataSource;
    private Connection connection;


    public MySQLProvider(PluginConfig pluginConfig, BackpackService backpackService) {
        this.database = pluginConfig.database;
        this.pluginConfig = pluginConfig;
        this.backpackService = backpackService;
    }

    @Override
    public void load() {
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `exotia_backpacks`;");
            while (resultSet.next()) {
                this.backpackService.registerBackpack(new Backpack(resultSet, this.pluginConfig));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void save(Backpack backpack) {
        try {
            PreparedStatement preparedStatement = this.getConnection()
                    .prepareStatement("INSERT INTO `exotia_backpacks` (`owner`, `items`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `items` = VALUES(items);");
            preparedStatement.setString(1, backpack.getUniqueId().toString());
            preparedStatement.setString(2, ItemStackSerializer.toBase64(backpack.getInventory().getContents()));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(Backpack backpack) {
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("DELETE FROM `exotia_backpacks` WHERE `owner` = ?;");
            preparedStatement.setString(1, backpack.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void connect() {
        this.hikariDataSource = new HikariDataSource(this.getHikariConfig());
        try {
            Statement statement = this.hikariDataSource.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS `exotia_backpacks`(`owner` VARCHAR(64) NOT NULL,`items` TEXT NOT NULL, PRIMARY KEY (owner));";
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", this.database.hostname, this.database.port, this.database.database));
        hikariConfig.setUsername(this.database.username);
        hikariConfig.setPassword(this.database.password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
        return hikariConfig;
    }
    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = this.hikariDataSource.getConnection();
        }
        return this.connection;
    }
}
