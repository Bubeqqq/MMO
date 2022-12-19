package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class SharpBladePlus extends Sword{

    public SharpBladePlus(Handler handler) {
        super(25, handler);
    }

    @Override
    public String getName() {
        return "SharpBlade+";
    }

    @Override
    public void setUpUpgrades() {

    }
}
