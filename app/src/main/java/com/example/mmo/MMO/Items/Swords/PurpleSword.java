package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class PurpleSword extends Sword{

    public PurpleSword(Handler handler) {
        super(2, handler);
    }

    @Override
    public String getName() {
        return "PurpleSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
