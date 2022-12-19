package com.example.mmo.MMO.Items.Necklace;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Statistics.Statistics;

public abstract class Necklace extends Armor {

    /*
    0 -> range
    1 -> health
    2 -> luck
     */

    public Necklace(int ID, Handler handler) {
        super(ID, handler);

        stackLimit = 1;

        numberOfAtributes = 3;
        atributes = new float[numberOfAtributes];

        atributesIDs = new int[]{Statistics.RANGE, Statistics.HEALTH, Statistics.LUCK};
    }

    @Override
    public int getType(){
        return Item.NECKLACE;
    }

}
