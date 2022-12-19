package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class NecromancerSword extends Sword{

    public NecromancerSword(Handler handler) {
        super(31, handler);
    }

    @Override
    public String getName() {
        return "NecromancerSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
