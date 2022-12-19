package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BigGoldSword extends Sword{
    public BigGoldSword(Handler handler) {
        super(61, handler);
    }

    @Override
    public String getName() {
        return "BigGoldSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
