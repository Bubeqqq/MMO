package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class GoldSwordPlus extends Sword{
    public GoldSwordPlus(Handler handler) {
        super(41, handler);
    }

    @Override
    public String getName() {
        return "GoldSword+";
    }

    @Override
    public void setUpUpgrades() {

    }
}
