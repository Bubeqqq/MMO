package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class IceBombItem extends Usable {

    public IceBombItem(Handler handler) {
        super(190, handler);
        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nAttack 3 closest mobs";
    }

    @Override
    public String getName() {
        return "Ice bomb";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[2].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[2].isReady();
    }
}
