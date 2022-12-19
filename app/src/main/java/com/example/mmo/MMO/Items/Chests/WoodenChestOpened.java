package com.example.mmo.MMO.Items.Chests;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public class WoodenChestOpened extends Item {

    public WoodenChestOpened(Handler handler) {
        super(239, handler);
        stackLimit = 1;
        sellValue = 5;
        buyValue = 1;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getType() {
        return Item.NON;
    }
}
