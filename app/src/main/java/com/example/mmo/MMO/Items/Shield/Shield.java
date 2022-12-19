package com.example.mmo.MMO.Items.Shield;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Statistics.Statistics;

public abstract class Shield extends Armor {

    /*
    0 -> defense
    1 -> health
     */

    public Shield(int ID, Handler handler) {
        super(ID, handler);

        lvlUpgrade = 0.1f;

        stackLimit = 1;

        numberOfAtributes = 2;
        atributes = new float[numberOfAtributes];

        atributesIDs = new int[]{Statistics.DEFENSE, Statistics.HEALTH};

    }

    @Override
    public int getType(){
        return Item.SHIELD;
    }
}
