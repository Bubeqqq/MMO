package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class BubaSword extends Sword{
    public BubaSword(Handler handler) {
        super(63, handler);

        atributes[0] = 25000;
        atributes[1] = 65000;
        atributes[2] = 95;
        atributes[3] = 100;

        sellValue = 350;
        buyValue = 500;

        setHaveUpgrades(true);
    }

    @Override
    public String getName() {
        return "BubaSword";
    }

    @Override
    public void setUpUpgrades() {
        upgrades[0].addPatern(0, 1, 1);
        upgrades[0].setMoney(10);
        upgrades[0].setPercent(95);
        upgrades[1].addPatern(0, 1, 2);
        upgrades[1].setMoney(30);
        upgrades[1].setPercent(90);
        upgrades[2].addPatern(0, 1, 3);
        upgrades[2].setMoney(75);
        upgrades[2].setPercent(80);
        upgrades[3].addPatern(0, 1, 4);
        upgrades[3].setMoney(100);
        upgrades[3].setPercent(75);
        upgrades[4].addPatern(0, 1, 5);
        upgrades[4].setMoney(200);
        upgrades[4].setPercent(65);
        upgrades[5].addPatern(0, 1, 6);
        upgrades[5].setMoney(300);
        upgrades[5].setPercent(60);
        upgrades[6].addPatern(0, 1, 7);
        upgrades[6].setMoney(500);
        upgrades[6].setPercent(50);
        upgrades[7].addPatern(0, 1, 8);
        upgrades[7].setMoney(750);
        upgrades[7].setPercent(40);
        upgrades[8].addPatern(0, 1, 9);
        upgrades[8].setMoney(1000);
        upgrades[8].setPercent(25);
    }
}
