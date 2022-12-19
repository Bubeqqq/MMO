package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class NebulaItem extends Usable {

    public NebulaItem(Handler handler) {
        super(187, handler);
        stackLimit = 1;


        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nCreate magnifying\nsphere around\nyou killing\neverything";
    }

    @Override
    public String getName() {
        return "Nebula";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[4].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[4].isReady();
    }
}
