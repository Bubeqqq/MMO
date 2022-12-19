package com.example.mmo.MMO.Items.Necklace;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public class BubaNecklace extends Necklace{
    public BubaNecklace(Handler handler) {
        super(101, handler);

        atributes[0] = 130;
        atributes[1] = 15000;
        atributes[2] = 15;
    }

    @Override
    public String getName() {
        return "BubaNecklace";
    }

    @Override
    public void setUpUpgrades() {

    }
}
