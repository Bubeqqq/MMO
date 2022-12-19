package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class SharpBlade extends Sword{

    public SharpBlade(Handler handler) {
        super(24, handler);
    }

    @Override
    public String getName() {
        return "SharpBlade";
    }

    @Override
    public void setUpUpgrades() {

    }
}
