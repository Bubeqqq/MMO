package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class DarkSword extends Sword{

    public DarkSword(Handler handler) {
        super(3, handler);
    }

    @Override
    public String getName() {
        return "DarkSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
