package com.example.mmo.MMO.Items.Shield;

import com.example.mmo.MMO.Handler;

public class BubaShield extends Shield{

    public BubaShield(Handler handler) {
        super(156, handler);

        atributes[0] = 45000;
        atributes[1] = 50000;
    }

    @Override
    public String getName() {
        return "BubaShield";
    }

    @Override
    public void setUpUpgrades() {

    }
}
