package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.utils.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Backpack {
    private UUID uniqueId;
    private Inventory inventory;
    private boolean needUpdate;

    public Backpack(ResultSet resultSet, PluginConfig pluginConfig) {
        try {
            this.uniqueId = UUID.fromString(resultSet.getString(""));
            this.inventory = Bukkit.createInventory(null, 9 * 2, "Plecak");
            this.inventory.setContents(ItemStackSerializer.fromBase64("items"));
            this.needUpdate = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}
