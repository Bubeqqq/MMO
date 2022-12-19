package com.example.mmo.MMO.Items.NoUse;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public class Silver extends Item {

    public static final int SILVERVALUE = 1;

    public Silver( Handler handler) {
        super(96, handler);
        stackLimit = 99;

        buyValue = 1;
        sellValue = 1;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\n" + "Worth " + SILVERVALUE * amount + " coins";
    }

    @Override
    public String getName() {
        return "Silver";
    }

    @Override
    public int getType() {
        return Item.ITEM;
    }

    @Override
    public void setUpUpgrades() {

    }
}
