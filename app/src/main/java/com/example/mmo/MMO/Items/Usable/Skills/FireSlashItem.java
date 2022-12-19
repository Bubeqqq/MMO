package com.example.mmo.MMO.Items.Usable.Skills;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class FireSlashItem extends Usable {

    public FireSlashItem(Handler handler) {
        super(188, handler);

        stackLimit = 1;

        setHaveUpgrades(true);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void use(int lvl) {
        handler.getSkillManager().getAllSkills()[7].use(lvl);
    }

    @Override
    public boolean canUse() {
        return handler.getSkillManager().getAllSkills()[7].isReady();
    }
}
