package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.utils.InventoryCompressor;
import net.exotia.plugins.exotiabackpack.utils.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class Backpack {
    private UUID uniqueId;
    private Inventory inventory;
    private boolean needUpdate;

    public Backpack(ResultSet resultSet, PluginConfig pluginConfig) {
        try {
            this.uniqueId = UUID.fromString(resultSet.getString("owner"));
            ItemStack[] itemStacks = ItemStackSerializer.fromBase64(resultSet.getString("items"));
            this.inventory = Bukkit.createInventory(null, itemStacks.length, pluginConfig.backpackGuiTitle);
            this.inventory.setContents(itemStacks);
            this.needUpdate = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Backpack(Player player, PluginConfig pluginConfig) {
        this.uniqueId = player.getUniqueId();
        this.checkResize();
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

    public void openInventory(PluginConfig pluginConfig) {
        Player player = Bukkit.getPlayer(this.uniqueId);
        if (player == null) return;
        this.checkResize();
        player.openInventory(this.inventory);
    }

    private int getBackpackMaxSize(Player player) {
        int last = 1;
        for (int i = 1; i <= 6; i++) {
            if (player.hasPermission("exotia.backpacks.size."+i)) last = i;
        }
        return last;
    }

    private void checkResize() {
        Player player = Bukkit.getPlayer(this.uniqueId);
        if (player == null) return;
        int size = this.getBackpackMaxSize(player);
        if (size < this.inventory.getSize()) return;
        setSize(size).stream().filter(Objects::nonNull).toList().forEach(itemStack -> {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        });
    }
    public List<ItemStack> setSize(int newSize) {
        List<ItemStack> removedItems;
        ItemStack[] itemStackArray;
        if(this.inventory.getSize()/9 > newSize) {
            InventoryCompressor compressor = new InventoryCompressor(this.inventory.getContents(), newSize);
            compressor.compress();
            itemStackArray = compressor.getTargetStacks();
            removedItems = compressor.getToMuch();
        } else {
            itemStackArray = this.inventory.getContents();
            removedItems = new ArrayList<>(0);
        }
        inventory = Bukkit.createInventory(null, newSize*9, "Plecak");
        for(int i = 0; i < itemStackArray.length; i++) {
            this.inventory.setItem(i, itemStackArray[i]);
        }
        return removedItems;
    }
}
