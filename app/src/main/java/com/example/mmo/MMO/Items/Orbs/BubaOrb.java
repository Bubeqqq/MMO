package com.example.mmo.MMO.Items.Orbs;

import com.example.mmo.MMO.Handler;

public class BubaOrb extends Orb{

    public BubaOrb(Handler handler) {
        super(71, handler);

        atributes[0] = 7500;
        atributes[1] = 12500;
        atributes[2] = 30;
        atributes[3] = 10000;
    }

    @Override
    public String getName() {
        return "BubaOrb";
    }

    @Override
    public void setUpUpgrades() {

    }
}
