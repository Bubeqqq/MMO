package com.example.mmo.MMO.Items.Recipe;

public class RecipePattern {

     int ID, amount, minLvl;

    public RecipePattern(int ID, int amount, int minLvl){
        this.ID = ID;
        this.minLvl = minLvl;
        this.amount = amount;
    }

    public int getID() {
        return ID;
    }

    public int getAmount() {
        return amount;
    }

    public int getMinLvl() {
        return minLvl;
    }
}
