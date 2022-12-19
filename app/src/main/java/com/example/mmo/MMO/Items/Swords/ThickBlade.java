package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class ThickBlade extends Sword{

    public ThickBlade(Handler handler) {
        super(32, handler);
    }

    @Override
    public String getName() {
        return "ThickBlade";
    }

    @Override
    public void setUpUpgrades() {

    }
}
