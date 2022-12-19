package com.example.mmo.MMO.Skills;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;
import com.example.mmo.MMO.TimeBonuses.TimeBonusesManager;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.util.ConcurrentModificationException;

public class FireSpin extends Skill{

    private float px, py;

    private int currentIndex = 0, delay = 100;

    private long lastTime, timer;

    private int[] xValues, yValues;

    private int lvl;

    public FireSpin(Handler handler) {
        super(handler, 3);

        cooldown = 1000;
        duration = 10000;

        animation = new Animation(Assets.fireSpin, 20);

        int[] a = {0, 1, 2, 2, 2, 1, 0, 0};
        xValues = a;
        int[] b = {0, 0, 0, 1, 2, 2, 2, 1};
        yValues = b;
    }

    @Override
    public void setupSkill(int lvl) {
        this.lvl = lvl;

        px = handler.getEntityManager().getPlayer().getX();
        py = handler.getEntityManager().getPlayer().getY();
        currentIndex = 0;

        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    @Override
    public void renderEffect(Canvas canvas) {
        int x = xValues[currentIndex];
        int y = yValues[currentIndex];

        canvas.drawBitmap(animation.GetCurrentFrame(),
                null,
                new RectF(px - Tile.TILEWIDTH * 2 + Tile.TILEWIDTH * x - handler.getCamera().getxOffset(),
                        py - Tile.TILEWIDTH * 2 + Tile.TILEWIDTH * y - handler.getCamera().getyOffset(),
                        px + Tile.TILEWIDTH * (x + 1) - handler.getCamera().getxOffset(),
                        py + Tile.TILEWIDTH * (y + 1) - handler.getCamera().getyOffset()),
                null);
    }

    @Override
    public void makeEffect() {
        px = handler.getEntityManager().getPlayer().getX();
        py = handler.getEntityManager().getPlayer().getY();

        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer > delay) {
            currentIndex++;

            if(currentIndex >= xValues.length)
                currentIndex = 0;

            timer = 0;
        }

        try {
            for(Entity e : handler.getEntityManager().getEntities()){
                if(e == handler.getEntityManager().getPlayer())
                    continue;

                if(getBounds().contains(e.getX(), e.getY()))
                    hurtEntity(e, 0.3f, true, lvl);
            }
        }catch (ConcurrentModificationException e){
            return;
        }
    }

    @Override
    public void endEffect() {

    }

    private RectF getBounds(){
        int x = xValues[currentIndex];
        int y = yValues[currentIndex];

        return new RectF(px - Tile.TILEWIDTH * 2 + Tile.TILEWIDTH * x,
                py - Tile.TILEWIDTH * 2 + Tile.TILEWIDTH * y,
                px + Tile.TILEWIDTH * (x + 1),
                py + Tile.TILEWIDTH * (y + 1));
    }
}
