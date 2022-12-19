package com.example.mmo.MMO.Items.Usable.Skills;

import android.util.Log;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class ExplosionItem extends Usable {

    public ExplosionItem(Handler handler) {
        super(177, handler);
        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nburn every mob\nin radius dealing\nenormous damage";
    }

    @Override
    public String getName() {
        return "Mob Fire";
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[0].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[0].isReady();
    }
}
