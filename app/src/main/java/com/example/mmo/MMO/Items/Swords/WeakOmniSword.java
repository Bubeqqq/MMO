package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class WeakOmniSword extends Sword{

    public WeakOmniSword(Handler handler) {
        super(38, handler);
    }

    @Override
    public String getName() {
        return "WeakOmniSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
