package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class WaterSword extends Sword{

    public WaterSword(Handler handler) {
        super(10, handler);
    }

    @Override
    public String getName() {
        return "WaterSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
