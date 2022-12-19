package com.example.mmo.MMO.Items.Recipe;

import java.util.ArrayList;

public class Recipe {

    private ArrayList<RecipePattern> recipes;

    private int money = 0, percent = 100;

    public Recipe(){
        recipes = new ArrayList<>();
    }

    public void addPatern(int ID, int amount, int minLvl){
        recipes.add(new RecipePattern(ID, amount, minLvl));
    }

    public ArrayList<RecipePattern> getRecipes() {
        return recipes;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }
}
