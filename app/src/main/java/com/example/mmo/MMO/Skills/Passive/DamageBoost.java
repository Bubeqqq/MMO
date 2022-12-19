package com.example.mmo.MMO.Skills.Passive;

import android.util.Log;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Skills.Skill;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;

public class DamageBoost extends Passive{

    public DamageBoost(Handler handler) {
        super(handler, 5);

        cooldown = 10000;

        animation = new Animation(Assets.boost, 15);
    }

    @Override
    public void setupSkill(int lvl) {
        animation.setFrame(0);
        Log.println(Log.ASSERT, "skill", lvl + "");
        handler.getTimeBonusesManager().addBonus(TimeBonuses.DAMAGE, 10, (int) (handler.getStatistics().getDamage() * (0.2f + lvl * Skill.LVLBOOST)));
        handler.getTimeBonusesManager().addBonus(TimeBonuses.CRITDAMAGE, 10, (int) (handler.getStatistics().getDamage() * (0.2f + lvl * Skill.LVLBOOST)));
    }
}
