package com.example.mmo.MMO.Items.Usable;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public abstract class Usable extends Item {

    protected boolean oneTimeUsable = false;

    public Usable(int ID, Handler handler) {
        super(ID, handler);

        setHaveUpgrades(false);

        stackLimit = 99;
        lvlUpgrade = 0.05f;
    }

    @Override
    public int getType() {
        return Item.USABLE;
    }

    public abstract void use(int lvl);

    public abstract boolean canUse();

    //getters


    public boolean isOneTimeUsable() {
        return oneTimeUsable;
    }
}
