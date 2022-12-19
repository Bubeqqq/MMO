package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class GoldSword extends Sword{

    public GoldSword(Handler handler) {
        super(15, handler);
    }

    @Override
    public String getName() {
        return "GoldSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
