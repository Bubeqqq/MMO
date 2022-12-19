package com.example.mmo.MMO.Statistics;

public class ItemBonus {

    private int ID;
    private float value;

    public ItemBonus(int ID, float value){
        this.ID = ID;
        this.value = value;
    }

    public int getID() {
        return ID;
    }

    public float getValue() {
        return value;
    }
}
