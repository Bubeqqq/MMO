package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class FireSword extends Sword{
    public FireSword(Handler handler) {
        super(57, handler);
    }

    @Override
    public String getName() {
        return "FireSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
