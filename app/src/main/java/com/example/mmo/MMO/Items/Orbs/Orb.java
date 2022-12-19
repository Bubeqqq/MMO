package com.example.mmo.MMO.Items.Orbs;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Statistics.Statistics;

public abstract class Orb extends Armor {

    /*
    0 -> damage
    1 -> crit damage
    2 -> crit chance
    3 -> health
     */

    public Orb(int ID, Handler handler) {
        super(ID, handler);

        stackLimit = 1;

        lvlUpgrade = 0.05f;

        numberOfAtributes = 4;
        atributes = new float[numberOfAtributes];

        atributesIDs = new int[]{Statistics.DAMAGE, Statistics.CRITDAMAGE, Statistics.CRITCHANCE, Statistics.HEALTH};
    }

    @Override
    public int getType(){
        return Item.ORB;
    }
}
