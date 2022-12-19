package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class EmeraldSword extends Sword{

    public EmeraldSword(Handler handler) {
        super(11, handler);
    }

    @Override
    public String getName() {
        return "EmeraldSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
