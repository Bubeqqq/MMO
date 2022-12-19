package com.example.mmo.MMO.Items.Swords;

import com.example.mmo.MMO.Handler;

public class EnchantedGoldSword extends Sword{

    public EnchantedGoldSword(Handler handler) {
        super(42, handler);
    }

    @Override
    public String getName() {
        return "EnchantedGoldSword";
    }

    @Override
    public void setUpUpgrades() {

    }
}
