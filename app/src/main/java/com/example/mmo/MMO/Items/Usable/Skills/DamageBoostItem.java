package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class DamageBoostItem extends Usable {

    public DamageBoostItem(Handler handler) {
        super(186, handler);
        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nIncrease your damage\nby fraction of your \nmax damage";
    }

    @Override
    public String getName() {
        return "Damage Increase";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[5].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[5].isReady();
    }
}
