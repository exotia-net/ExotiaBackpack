package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.configuration.providers.PluginConfig;
import net.exotia.plugins.exotiabackpack.utils.InventoryCompressor;
import net.exotia.plugins.exotiabackpack.utils.ItemStackSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
            this.inventory = Bukkit.createInventory(null, itemStacks.length, this.getBackpackTitle(itemStacks.length/9));
            this.inventory.setContents(itemStacks);
            this.needUpdate = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Backpack(Player player) {
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
        setSize(size, player).stream().filter(Objects::nonNull).toList().forEach(itemStack -> {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        });
    }
    public List<ItemStack> setSize(int newSize, Player player) {
        List<ItemStack> removedItems;
        ItemStack[] itemStackArray;
        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(null, newSize*9, this.getBackpackTitle(newSize));
        }
        if(this.inventory.getSize()/9 > newSize) {
            InventoryCompressor compressor = new InventoryCompressor(this.inventory.getContents(), newSize);
            player.sendMessage(MiniMessage.miniMessage().deserialize("鼅 <white>Twój plecak zostal pomniejszony, itemy wypadly na ziemie!"));
            compressor.compress();
            itemStackArray = compressor.getTargetStacks();
            removedItems = compressor.getToMuch();
        } else {
            itemStackArray = this.inventory.getContents();
            removedItems = new ArrayList<>(0);
        }
        this.inventory = Bukkit.createInventory(null, newSize*9, this.getBackpackTitle(newSize));
        for(int i = 0; i < itemStackArray.length; i++) {
            this.inventory.setItem(i, itemStackArray[i]);
        }
        return removedItems;
    }

    private String getBackpackTitle(int size) {
        return LegacyComponentSerializer.legacySection().serialize(
                MiniMessage.miniMessage().deserialize("<white>✟" + List.of("꼀", "꼁", "꼂", "꼃", "꼄", "꼅").get(size-1))
        );
    }
}
