package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class SilverSword extends Sword{

    public SilverSword(Handler handler) {
        super(14, handler);
    }

    @Override
    public String getName() {
        return"SilverSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
