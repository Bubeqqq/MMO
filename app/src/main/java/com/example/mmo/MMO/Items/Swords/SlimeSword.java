package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class SlimeSword extends Sword{

    public SlimeSword(Handler handler) {
        super(13, handler);
    }

    @Override
    public String getName() {
        return "SlimeSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
