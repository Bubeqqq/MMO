package com.example.mmo.MMO.Skills.Passive;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Skills.Skill;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;

public class DefenceBoost extends Passive{

    public DefenceBoost(Handler handler) {
        super(handler, 6);

        cooldown = 10000;

        animation = new Animation(Assets.protection, 15);
    }

    @Override
    public void setupSkill(int lvl) {
        animation.setFrame(0);
        handler.getTimeBonusesManager().addBonus(TimeBonuses.HEALTH, 10, (int) (handler.getStatistics().getHealth() * (0.2f + lvl * Skill.LVLBOOST)));
        handler.getTimeBonusesManager().addBonus(TimeBonuses.DEFENCE, 10, (int) (handler.getStatistics().getDefense() * (0.2f + lvl * Skill.LVLBOOST)));
    }
}
