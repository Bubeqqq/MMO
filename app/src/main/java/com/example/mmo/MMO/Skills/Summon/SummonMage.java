package com.example.mmo.MMO.Skills.Summon;

import android.graphics.Canvas;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.PlayerMobs.PlayerMob;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Skills.Skill;

public class SummonMage extends Skill {

    public SummonMage(Handler handler) {
        super(handler, 8);

        cooldown = 30000;
        duration = 1;
    }

    @Override
    public void setupSkill(int lvl) {
        PlayerMob mob = new PlayerMob(handler,
                Creature.DEFAULTWIDTH,
                Creature.DEFAULTHEIGHT,
                (int) handler.getEntityManager().getPlayer().getX(),
                (int) handler.getEntityManager().getPlayer().getY(),
                Assets.mage);

        mob.setAttackDelay((int) (2300 * (1 + Skill.LVLBOOST * lvl)));
        mob.setAwayLimit((int) (4000 * (1 + Skill.LVLBOOST * lvl)));
        mob.setRange((int) (500 * (1 + Skill.LVLBOOST * lvl)));
        mob.setDamagePercent(0.55f * (1 + Skill.LVLBOOST * lvl));
        mob.setHealth((int) (15 * (1 + Skill.LVLBOOST * lvl)));

        handler.getEntityManager().addEntity(mob);
    }

    @Override
    public void renderEffect(Canvas canvas) {

    }

    @Override
    public void makeEffect() {

    }

    @Override
    public void endEffect() {

    }
}
