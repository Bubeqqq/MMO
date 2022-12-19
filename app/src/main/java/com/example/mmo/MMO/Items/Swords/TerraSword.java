package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class TerraSword extends Sword{

    public TerraSword(Handler handler) {
        super(39, handler);
    }

    @Override
    public String getName() {
        return "TerraSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
