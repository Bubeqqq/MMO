package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class GoldDragonSword extends Sword{

    public GoldDragonSword(Handler handler) {
        super(4, handler);
    }

    @Override
    public String getName() {
        return "GoldDragonSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
