package net.exotia.plugins.exotiabackpack.backpack;

import java.util.ArrayList;
import java.util.List;

public class BackpackService {
    private final List<Backpack> backpacks = new ArrayList<>();

    public void registerBackpack(Backpack backpack) {
        this.backpacks.add(backpack);
    }
}
