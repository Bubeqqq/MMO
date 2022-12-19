package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class YellowFlameSword extends Sword{

    public YellowFlameSword(Handler handler) {
        super(21, handler);
    }

    @Override
    public String getName() {
        return "YellowFlameSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
