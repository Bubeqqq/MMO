package com.example.mmo.MMO.Items.Armor;

import android.util.Log;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public abstract class Armor extends Item {

    protected int[] atributesIDs;

    protected float[] atributes;

    public Armor(int ID, Handler handler) {
        super(ID, handler);
        minLvl = 1;
    }

    public float getAtribute(int lvl, int ID){
        return getValueFromLvl(lvl, atributes[ID]);
    }

    public int[] getAtributesIDs() {
        return atributesIDs;
    }

    protected float getValueFromLvl(int lvl, float value){
        float v = value;
        for(int i = 1; i < lvl; i++){
            v *= 1 + lvlUpgrade;
        }
        return v;
    }

    @Override
    public String getDescription(int lvl, int amount){
        String s = new String();

        s += getName() + "\n";

        s += "Min. Lvl:" + minLvl + "\n";

        int index = 0;
        for(float i : atributes){
            s += handler.getStatistics().getName(atributesIDs[index]) + ":" + String.format("%.1f", getValueFromLvl(lvl, i)) + "\n";
            index++;
        }

        return s;
    }
}
