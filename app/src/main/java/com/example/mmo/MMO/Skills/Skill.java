package com.example.mmo.MMO.Skills;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.Log;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

public abstract class Skill {

    protected Handler handler;

    protected Animation animation;

    protected float cooldown = 1, duration = 1;

    protected boolean isActive, ready = true;

    private long lastTime, timer;

    public static final float LVLBOOST = 0.05f;

    protected int ID;

    public Skill(Handler handler, int ID){
        handler.getSkillManager().getAllSkills()[ID] = this;

        this.handler = handler;
        this.ID = ID;
    }

    public void use(int lvl){
        if(ready){
            isActive = true;
            ready = false;
            lastTime = System.currentTimeMillis();



            setupSkill(lvl);
        }
    }

    public void tick(){
        if(animation != null)
            animation.tick();
        if(ready)
            return;

        if(isActive){
            makeEffect();

            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if(timer > duration) {
                timer = 0;
                isActive = false;
                ready = false;
                endEffect();
                Log.println(Log.ASSERT, "Skill", "end Effect");
            }
        }else {
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if(timer > cooldown * handler.getStatistics().getSkillCooldown()) {
                timer = 0;
                ready = true;
                Log.println(Log.ASSERT, "Skill", "Ready");
            }
        }


    }

    public void render(Canvas canvas){
        if(isActive){
            renderEffect(canvas);
        }
    }

    public abstract void setupSkill(int lvl);

    public abstract void renderEffect(Canvas canvas);

    public abstract void makeEffect();

    public abstract void endEffect();

    public void hurtEntity(Entity e, float percent, boolean defense, int lvl){
        e.hurt((int) (handler.getStatistics().getDamage() * percent * (handler.getStatistics().getSkillDamage() + LVLBOOST * lvl)), defense, false);
    }

    //getters


    public boolean isReady() {
        return ready;
    }
}
