package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BronzeSword extends Sword{

    public BronzeSword(Handler handler) {
        super(1, handler);
    }

    @Override
    public String getName() {
        return "BronzeSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
