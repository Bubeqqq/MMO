package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class DragonSword extends Sword{

    public DragonSword(Handler handler) {
        super(5, handler);

        atributes[0] = 1200;
        atributes[1] = 2500;
    }

    @Override
    public String getName() {
        return "DragonSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
