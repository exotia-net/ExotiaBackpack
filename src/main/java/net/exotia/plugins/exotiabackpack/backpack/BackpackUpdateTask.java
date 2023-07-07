package net.exotia.plugins.exotiabackpack.backpack;

import net.exotia.plugins.exotiabackpack.database.DatabaseService;

public class BackpackUpdateTask implements Runnable {
    private final BackpackService backpackService;
    private final DatabaseService databaseService;

    public BackpackUpdateTask(BackpackService backpackService, DatabaseService databaseService) {
        this.backpackService = backpackService;
        this.databaseService = databaseService;
    }

    @Override
    public void run() {
        this.backpackService.findBackpacks(true).forEach(this.databaseService::save);
    }
}
