package net.exotia.plugins.exotiabackpack.database.providers;

import com.zaxxer.hikari.HikariConfig;
import net.exotia.plugins.exotiabackpack.backpack.Backpack;
import net.exotia.plugins.exotiabackpack.backpack.BackpackService;
import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.configuration.providers.sections.DatabaseSection;
import net.exotia.plugins.exotiabackpack.database.DatabaseService;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            Backpack backpack = new Backpack(resultSet, this.pluginConfig);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void save(Backpack backpack) {

    }

    @Override
    public void delete(Backpack backpack) {

    }

    @Override
    public void connect() {
        this.hikariDataSource = new HikariDataSource(this.getHikariConfig());
        try {
            Statement statement = this.hikariDataSource.getConnection().createStatement();
            String query = "create table if not exists `exotia_backpacks`(`owner` varchar(64) not null,`items` text not null, primary key (owner));";
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
