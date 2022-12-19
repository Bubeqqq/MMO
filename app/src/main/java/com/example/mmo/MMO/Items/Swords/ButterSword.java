package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class ButterSword extends Sword{

    public ButterSword(Handler handler) {
        super(18, handler);
    }

    @Override
    public String getName() {
        return "ButterSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
