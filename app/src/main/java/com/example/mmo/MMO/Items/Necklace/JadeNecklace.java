package com.example.mmo.MMO.Items.Necklace;

import com.example.mmo.MMO.Handler;

public class JadeNecklace extends Necklace{

    public JadeNecklace(Handler handler) {
        super(99, handler);

        atributes[0] = 15;
        atributes[1] = 50;
        atributes[2] = 1;
    }

    @Override
    public String getName() {
        return "JadeNecklace";
    }

    @Override
    public void setUpUpgrades() {

    }
}
