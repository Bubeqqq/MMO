package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class PinkSword extends Sword{

    public PinkSword(Handler handler) {
        super(12, handler);
    }

    @Override
    public String getName() {
        return"PinkSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
