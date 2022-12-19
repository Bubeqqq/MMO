package com.example.mmo.MMO.Items.Swords;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Statistics.Statistics;

import java.util.Random;

public abstract class Sword extends Armor {

    /*
    0 -> damage
    1 -> crit damage
    2 -> crit chance
    3 -> range
     */

    protected Random critChance;

    public Sword(int ID, Handler handler) {
        super(ID, handler);

        lvlUpgrade = 0.07f;

        critChance = new Random();

        stackLimit = 1;

        numberOfAtributes = 4;
        atributes = new float[numberOfAtributes];

        atributesIDs = new int[]{Statistics.DAMAGE, Statistics.CRITDAMAGE, Statistics.CRITCHANCE, Statistics.RANGE};
    }

    @Override
    public int getType(){
        return Item.WEAPON;
    }
}
