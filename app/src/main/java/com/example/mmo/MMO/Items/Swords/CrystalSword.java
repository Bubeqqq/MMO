package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class CrystalSword extends Sword{

    public CrystalSword(Handler handler) {
        super(16, handler);
    }

    @Override
    public String getName() {
        return "CrystalSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
