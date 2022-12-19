package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BronzeKnife extends Sword{
    public BronzeKnife(Handler handler) {
        super(55, handler);
    }

    @Override
    public String getName() {
        return "BronzeKnife";
    }

    @Override
    public void setUpUpgrades() {

    }
}
