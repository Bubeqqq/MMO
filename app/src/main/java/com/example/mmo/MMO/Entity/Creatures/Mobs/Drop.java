package com.example.mmo.MMO.Entity.Creatures.Mobs;

import com.example.mmo.R;

import java.util.Random;

public class Drop {

    private int ID, percentage, amount, maxLvl;

    private boolean alwaysMaxLvl = false;

    public Drop(int ID, int percentage, int amount, int maxLvl){
        this.ID = ID;
        this.amount = amount;
        this.percentage = percentage;
        this.maxLvl = maxLvl;
    }

    public int getID() {
        return ID;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getAmount() {
        return amount;
    }

    public int getLvl(){
        if(alwaysMaxLvl)
            return maxLvl;

        if(maxLvl == 0)
            return 0;

        Random r = new Random();
        return r.nextInt(maxLvl) + 1;
    }
}
