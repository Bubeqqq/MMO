package com.example.mmo.MMO.Items.NoUse;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public class SackOfGold extends Item {

    public static final int SACKVALUE = 200;

    public SackOfGold(Handler handler) {
        super(94, handler);
        stackLimit = 99;

        buyValue = 200;
        sellValue = 200;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\n" + "Worth " + SACKVALUE * amount + " coins";
    }

    @Override
    public String getName() {
        return "Sack of Gold";
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void setUpUpgrades() {

    }
}
