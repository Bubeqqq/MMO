package com.example.mmo.MMO.Items;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Recipe.Recipe;
import com.example.mmo.MMO.Items.Recipe.RecipePattern;

public abstract class Item {

    //TYPES

    public static final int WEAPON = 0,
                            USABLE = 1,
                            ORB = 2,
                            NECKLACE = 3,
                            CHESTPLATE = 4,
                            SHIELD = 5,
                            HELMET = 6,
                            ITEM = 7,
                            DRAGGABLE = 8,
                            CHEST = 9,
                            NON = 10;

    protected int stackLimit = 64;

    protected int sellValue = 1, buyValue = 3;

    protected int ID;

    protected Handler handler;

    protected boolean haveUpgrades, haveTransformation = false, haveDraggableEvent = false;

    protected Recipe[] upgrades;

    protected float lvlUpgrade = 0.03f;

    protected int numberOfAtributes = 0;

    protected int minLvl = -1;

    //transformation

    protected int newId = 0;

    protected Recipe transformation;

    public Item(int ID, Handler handler){
        handler.getItemManager().getItems()[ID] = this;
        this.handler = handler;

        this.ID = ID;
    }

    public abstract String getDescription(int lvl, int amount);

    public abstract String getName();

    public abstract int getType();

    public void setUpUpgrades() {
        upgrades[0].setMoney(10);
        upgrades[1].setMoney(30);
        upgrades[2].setMoney(75);
        upgrades[3].setMoney(100);
        upgrades[4].setMoney(200);
        upgrades[5].setMoney(300);
        upgrades[6].setMoney(500);
        upgrades[7].setMoney(750);
        upgrades[8].setMoney(1000);
    }

    //getters

    public int getStackLimit() {
        return stackLimit;
    }

    public boolean canUpgrade(int lvl){
        if(!haveUpgrades)
            return false;

        if(lvl > 10){
            return false;
        }

        boolean can = true;

        for(RecipePattern r : upgrades[lvl - 1].getRecipes()){
            if(!handler.getEq().getEq().haveItem(r.getID(), r.getAmount(), r.getMinLvl()))
                can = false;
        }

        if(handler.getEq().getMoney() < upgrades[lvl - 1].getMoney())
            can = false;

        return can;
    }

    public void takeItems(int lvl){
        for(RecipePattern r : upgrades[lvl - 1].getRecipes()){
            handler.getEq().getEq().takeItem(r.getID(), r.getAmount(), r.getMinLvl());
        }

        handler.getEq().takeMoney(upgrades[lvl - 1].getMoney());
    }
    protected void setHaveUpgrades(boolean set){
        haveUpgrades = set;

        if(haveUpgrades){
            upgrades = new Recipe[9];

            for (int i = 0; i < upgrades.length; i++)
                upgrades[i] = new Recipe();

            setUpUpgrades();
        }
    }

    public String getSellDescription(int amount){
        String s = "";
        s += "Buy price : " + getBuyValue(amount) + "\n";
        s += "Sell price : " + getSellValue(amount) + "\n";

        return s;
    }

    public int getSellValue(int amount){
        return sellValue * amount;
    }

    public int getBuyValue(int amount) {
        return buyValue * amount;
    }

    public boolean haveDraggableEvent() {
        return haveDraggableEvent;
    }

    public Recipe[] getUpgrades() {
        return upgrades;
    }

    public boolean haveUpgrades() {
        return haveUpgrades;
    }

    public int getNumberOfAtributes() {
        return numberOfAtributes;
    }

    public float getLvlUpgrade() {
        return lvlUpgrade;
    }

    public int getMinLvl() {
        return minLvl;
    }

    public boolean haveTransformation() {
        return haveTransformation;
    }

    public int getNewId() {
        return newId;
    }

    public boolean upgrade(){
        for(RecipePattern r : transformation.getRecipes()){
            if(!handler.getEq().getEq().haveItem(r.getID(), r.getAmount(), r.getMinLvl()))
                return false;
        }

        if(handler.getEq().getMoney() < transformation.getMoney())
            return false;

        for(RecipePattern r : transformation.getRecipes()){
            handler.getEq().getEq().takeItem(r.getID(), r.getAmount(), r.getMinLvl());
        }

        handler.getEq().takeMoney(transformation.getMoney());

        return true;
    }

    public Recipe getTransformation() {
        return transformation;
    }
}
