package com.example.mmo.MMO.Items.Helmet;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Statistics.Statistics;

public abstract class Helmet extends Armor {

    /*
    0 -> defense
    1 -> health regen
    2 -> luck
     */

    public Helmet(int ID, Handler handler) {
        super(ID, handler);

        stackLimit = 1;

        lvlUpgrade = 0.05f;

        numberOfAtributes = 3;
        atributes = new float[numberOfAtributes];

        atributesIDs = new int[]{Statistics.DEFENSE, Statistics.HEALTHREGEN, Statistics.LUCK};
    }

    @Override
    public int getType(){
        return Item.HELMET;
    }

}
