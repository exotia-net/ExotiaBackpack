package net.exotia.plugins.exotiabackpack.database;

import net.exotia.plugins.exotiabackpack.backpack.Backpack;

public interface DatabaseService {
    void load();
    void save(Backpack backpack);
    void delete(Backpack backpack);
    void connect();
}
