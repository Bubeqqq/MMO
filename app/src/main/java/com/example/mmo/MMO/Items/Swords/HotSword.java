package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class HotSword extends Sword{

    public HotSword(Handler handler) {
        super(9, handler);
    }

    @Override
    public String getName() {
        return"HotSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
