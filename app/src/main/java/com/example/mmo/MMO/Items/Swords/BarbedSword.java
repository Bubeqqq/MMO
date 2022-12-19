package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BarbedSword extends Sword{

    public BarbedSword(Handler handler) {
        super(6, handler);
    }

    @Override
    public String getName() {
        return "BarbedSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
