package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class FireSpinItem extends Usable {

    public FireSpinItem(Handler handler) {
        super(185, handler);
        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nSpawn fire ring,\nwhich will be going\naround you, and\ndealing damage";
    }

    @Override
    public String getName() {
        return "Fire ring";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[3].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[3].isReady();
    }
}
