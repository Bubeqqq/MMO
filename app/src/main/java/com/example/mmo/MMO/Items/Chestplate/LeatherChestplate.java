package com.example.mmo.MMO.Items.Chestplate;

import com.example.mmo.MMO.Handler;

public class LeatherChestplate extends Chestplate{

    public LeatherChestplate(Handler handler) {
        super(122, handler);

        atributes[0] = 150;
        atributes[1] = 20;
        atributes[2] = 5;
        atributes[3] = -0.03f;
    }

    @Override
    public String getName() {
        return "LeatherChestplate";
    }

    @Override
    public void setUpUpgrades() {

    }
}
