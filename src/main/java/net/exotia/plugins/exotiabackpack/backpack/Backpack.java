package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.utils.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
            this.uniqueId = UUID.fromString(resultSet.getString("owner"));
            this.inventory = Bukkit.createInventory(null, 9 * 2, pluginConfig.backpackGuiTitle);
            this.inventory.setContents(ItemStackSerializer.fromBase64(resultSet.getString("items")));
            this.needUpdate = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Backpack(Player player, PluginConfig pluginConfig) {
        this.uniqueId = player.getUniqueId();
        this.inventory = Bukkit.createInventory(null, 9 * 2, pluginConfig.backpackGuiTitle);
        this.needUpdate = true;
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

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void openInventory() {
        Player player = Bukkit.getPlayer(this.uniqueId);
        if (player == null) return;
        player.openInventory(this.inventory);
    }
}
