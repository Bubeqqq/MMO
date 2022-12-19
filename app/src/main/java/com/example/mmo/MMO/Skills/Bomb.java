package com.example.mmo.MMO.Skills;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Bomb extends Skill{

    private boolean exploded = false, dealtDamage = false;

    private long lastTime, timer;

    private final int delay = 1500;

    private float Px, Py;

    private final int range = 300;

    private int lvl;

    public Bomb(Handler handler) {
        super(handler, 1);

        cooldown = 600;
        duration = delay + 2000;

        animation = new Animation(Assets.boom, 60);
    }

    @Override
    public void setupSkill(int lvl) {
        this.lvl = lvl;

        Px = handler.getEntityManager().getPlayer().getX();
        Py = handler.getEntityManager().getPlayer().getY();
        animation.setFrame(0);

        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void renderEffect(Canvas canvas) {
        if(exploded){
            canvas.drawBitmap(animation.GetCurrentFrame(),
                    null,
                    new RectF(Px - handler.getCamera().getxOffset() - Tile.TILEWIDTH,
                            Py - handler.getCamera().getyOffset() - Tile.TILEWIDTH,
                            Px + Tile.TILEWIDTH * 2 - handler.getCamera().getxOffset(),
                            Py + Tile.TILEWIDTH * 2 - handler.getCamera().getyOffset()),
                    null);
        }
    }

    @Override
    public void makeEffect() {

        if(!exploded){
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if(timer > delay) {
                exploded = true;
            }
        }

        if(exploded && !dealtDamage){
            try {
                for (Entity e : handler.getEntityManager().getEntities()) {
                    if (e == handler.getEntityManager().getPlayer())
                        continue;

                    if (Utils.getDistance((int) e.getX(), (int) e.getY(), (int) Px, (int) Py) <= range) {
                        hurtEntity(e, 3, true, lvl);
                    }

                    dealtDamage = true;
                }
            }catch (ConcurrentModificationException e){
                dealtDamage = false;
            }
        }
    }

    @Override
    public void endEffect() {
        exploded = false;
        dealtDamage = false;
    }
}
