package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BronzeSwordPlus extends Sword{

    public BronzeSwordPlus(Handler handler) {
        super(8, handler);
    }

    @Override
    public String getName() {
        return "BronzeSword+";
    }

    @Override
    public void setUpUpgrades() {

    }
}
