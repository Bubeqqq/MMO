package com.example.mmo.MMO.Items.Chestplate;

import com.example.mmo.MMO.Handler;

public class BubaChestplatePlus extends Chestplate{

    public BubaChestplatePlus(Handler handler) {
        super(132, handler);

        atributes[0] = 75000;
        atributes[1] = 35000;
        atributes[2] = 1500;
        atributes[3] = -1f;

        minLvl = 10;
    }

    @Override
    public String getName() {
        return "BubaChestplate+";
    }

    @Override
    public void setUpUpgrades() {

    }
}
