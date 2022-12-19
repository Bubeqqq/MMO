package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class IceSword extends Sword{

    public IceSword(Handler handler) {
        super(19, handler);
    }

    @Override
    public String getName() {
        return "IceSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
