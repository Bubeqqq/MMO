package com.example.mmo.MMO.Entity.Creatures.Bosses;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.GUI.HealthBar;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Portals.Portal;

import java.util.ArrayList;
import java.util.Random;

public abstract class Boss extends Mob {
    protected int phase = 0;

    protected int healthRegen = 0, regenDelay = 200;
    protected int specialAttack = 5, attackCount = 0; //after n normal attacks make special attack

    protected float phaseBonus = 0.5f;


    //timer
    private long lastTime, attackTimer, regenTimer;

    public Boss(Handler handler, int width, int height, int x, int y, Bitmap[] textures, int ID) {
        super(handler, width, height, x, y, textures, null, ID);
    }

    @Override
    public void postRender(Canvas canvas) {

    }

    @Override
    protected void tick() {
        //set phase of the boss

        if(health < maxHealth * 0.1f){
            phase = 3;
        }else if(health < maxHealth * 0.3f){
            phase = 2;
        }else if(health < maxHealth * 0.6f){
            phase = 1;
        }

        //

        attackTimer += System.currentTimeMillis() - lastTime;
        regenTimer += System.currentTimeMillis() - lastTime;

        //regen health
        if(attackTimer > regenDelay * (1 - phaseBonus * phase)){ //regen HP
           health += healthRegen * (1 + phaseBonus * phase);
           healthBar.Refresh(health, maxHealth);

           regenTimer = 0;
        }


        //walk

        if(freeWalk) {
            if (Utils.getDistance((int) x, (int) y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range) {
                freeWalk = false;
            }
        }

        if(freeWalk){
            freeWalk();
        }else {
            attackWalk();

            //attack
            if(Utils.getDistance((int)x, (int)y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range){
                attackTimer += System.currentTimeMillis() - lastTime;

                if(attackTimer >= attackDelay){
                    attackTimer = 0;

                    if(attackCount < specialAttack){
                        defaultAttack();
                    }else{
                        switch (phase){
                            case 0:
                                attackOne();
                                break;
                            case 1:
                                attackTwo();
                                break;
                            case 2:
                                attackThree();
                                break;
                        }

                        attackCount = 0;
                    }

                    attackCount++;
                }
            }
        }
        //keep timer updated
        lastTime = System.currentTimeMillis();

        if(health <= 0)
            die();
    }

    @Override
    public void die() {
        Random random = new Random();
        if(!drops.isEmpty()){
            for(Drop d : drops){
                if(random.nextInt(100) < d.getPercentage() + handler.getStatistics().getLuck() / 100 * d.getPercentage()){
                    handler.getItemManager().addDroppedItem(x, y, d.getAmount(), d.getID(), d.getLvl(), null);
                }
            }
        }
        handler.getEntityManager().removeEntity(this);

        handler.getEntityManager().getPlayer().addXP(exp);

        handler.getQuestManager().event(new QuestEvent(QuestEvent.KILLBOSS, ID, 1));

        if(spawnPortalTo != -1){
            handler.getWorld().getPortals().addPortal(new Portal(handler, x, y, spawnPortalTo));
        }
    }

    //abstract attacks

    protected abstract void attackOne();
    protected abstract void attackTwo();
    protected abstract void attackThree();
    protected abstract void defaultAttack();

}
