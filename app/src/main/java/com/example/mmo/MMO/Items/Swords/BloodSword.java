package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BloodSword extends Sword{

    public BloodSword(Handler handler) {
        super(7, handler);
    }

    @Override
    public String getName() {
        return "BloodSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
