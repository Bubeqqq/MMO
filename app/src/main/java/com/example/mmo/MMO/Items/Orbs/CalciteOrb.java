package com.example.mmo.MMO.Items.Orbs;

import com.example.mmo.MMO.Handler;

public class CalciteOrb extends Orb{

    public CalciteOrb(Handler handler) {
        super(68, handler);

        atributes[0] = 25;
        atributes[1] = 30;
        atributes[2] = 2;
        atributes[3] = 125;
    }

    @Override
    public String getName() {
        return "CalciteOrb";
    }

    @Override
    public void setUpUpgrades() {

    }
}
