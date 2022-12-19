package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class DefenceBoostItem extends Usable {

    public DefenceBoostItem(Handler handler) {
        super(183, handler);
        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nIncrease your defense\nby fraction of your \ndefense";
    }

    @Override
    public String getName() {
        return "Defense Boost";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[6].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[6].isReady();
    }
}
