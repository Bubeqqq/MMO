package com.example.mmo.MMO.Items.NoUse;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public class Gold extends Item {

    public static final int GOLDVALUE = 20;

    public Gold( Handler handler) {
        super(89, handler);

        stackLimit = 99;

        buyValue = 20;
        sellValue = 20;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\n" + "Worth " + GOLDVALUE * amount + " coins";
    }

    @Override
    public String getName() {
        return "Gold";
    }

    @Override
    public int getType() {
        return Item.ITEM;
    }

    @Override
    public void setUpUpgrades() {

    }
}
