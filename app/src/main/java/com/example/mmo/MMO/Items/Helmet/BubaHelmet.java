package com.example.mmo.MMO.Items.Helmet;

import com.example.mmo.MMO.Handler;

public class BubaHelmet extends Helmet{

    public BubaHelmet(Handler handler) {
        super(175, handler);

        atributes[0] = 15000;
        atributes[1] = 1000;
        atributes[2] = 7;
    }

    @Override
    public String getName() {
        return "BubaHelmet";
    }

    @Override
    public void setUpUpgrades() {

    }
}
