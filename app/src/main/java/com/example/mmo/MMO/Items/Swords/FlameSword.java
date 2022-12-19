package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class FlameSword extends Sword{

    public FlameSword(Handler handler) {
        super(20, handler);
    }

    @Override
    public String getName() {
        return "FlameSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
