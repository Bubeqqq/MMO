package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class Katana extends Sword{
    public Katana(Handler handler) {
        super(50, handler);
    }

    @Override
    public String getName() {
        return "Katana";
    }

    @Override
    public void setUpUpgrades() {

    }
}
