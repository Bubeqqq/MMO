package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class OmniSword extends Sword{

    public OmniSword(Handler handler) {
        super(23, handler);
    }

    @Override
    public String getName() {
        return "OmniSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
