package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class LightSword extends Sword{
    public LightSword(Handler handler) {
        super(49, handler);
    }

    @Override
    public String getName() {
        return "LightSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
