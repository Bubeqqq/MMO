package com.example.mmo.MMO.Items.Chestplate;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Statistics.Statistics;

public abstract class Chestplate extends Armor {

    /*
    0 -> health
    1 -> defense
    2 -> health regen
    3 -> health regen time
     */

    public Chestplate(int ID, Handler handler) {
        super(ID, handler);

        stackLimit = 1;

        lvlUpgrade = 0.05f;

        numberOfAtributes = 4;
        atributes = new float[numberOfAtributes];

        atributesIDs = new int[]{Statistics.HEALTH, Statistics.DEFENSE, Statistics.HEALTHREGEN, Statistics.HEALTHREGENTIME};
    }

    @Override
    public int getType(){
        return Item.CHESTPLATE;
    }
}
