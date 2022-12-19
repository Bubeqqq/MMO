package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Recipe.Recipe;

public class BasicSword extends Sword{

    public BasicSword(Handler handler) {
        super(0, handler);
        atributes[0] = 1;
        atributes[1] = 2;
        atributes[2] = 50;
        atributes[3] = 20;

        setHaveUpgrades(true);
    }

    @Override
    public String getName() {
        return "BasicSword";
    }

    @Override
    public void setUpUpgrades() {
        upgrades[0].setMoney(1);
        upgrades[0].setPercent(100);
        upgrades[1].setMoney(2);
        upgrades[1].setPercent(100);
        upgrades[2].setMoney(5);
        upgrades[2].setPercent(100);
        upgrades[3].setMoney(10);
        upgrades[3].setPercent(90);
        upgrades[4].setMoney(25);
        upgrades[4].setPercent(85);
        upgrades[5].setMoney(50);
        upgrades[5].setPercent(80);
        upgrades[6].setMoney(60);
        upgrades[6].setPercent(75);
        upgrades[7].setMoney(75);
        upgrades[7].setPercent(70);
        upgrades[8].setMoney(80);
        upgrades[8].setPercent(65);
    }
}
