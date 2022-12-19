package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class SummonMageItem extends Usable {

    public SummonMageItem(Handler handler) {
        super(202, handler);
        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nSpawn powerful mage\nfor 15 seconds\nthat will follow you\nand attack your\nopponents";
    }

    @Override
    public String getName() {
        return "Spawn Mage";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[8].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[8].isReady();
    }
}
