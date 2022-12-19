package com.example.mmo.MMO.Items.Chests;

import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Handler;

public class WoodenChest extends Chest{

    public WoodenChest(Handler handler) {
        super(234, handler);

        keyLvl = 0;
        openedChest = 239;
        drops.add(new Drop(94, 0, 1, 0));
        drops.add(new Drop(89, 0, 3, 0));
        drops.add(new Drop(0, 0, 1, 10));
        drops.add(new Drop(64, 0, 3, 0));
        drops.add(new Drop(65, 0, 3, 0));
        drops.add(new Drop(66, 0, 3, 0));
        drops.add(new Drop(67, 0, 3, 0));
        drops.add(new Drop(78, 0, 5, 0));
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
