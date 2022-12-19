package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class AbsoluteSword extends Sword{
    public AbsoluteSword(Handler handler) {
        super(56, handler);
    }

    @Override
    public String getName() {
        return "AbsoluteSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
