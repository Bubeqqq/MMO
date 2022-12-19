package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class ThunderSword extends Sword{

    public ThunderSword(Handler handler) {
        super(17, handler);
    }

    @Override
    public String getName() {
        return "ThunderSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
