package com.example.mmo.MMO.Skills;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Nebula extends Skill{

    private int currentRange = 0;

    private float Px, Py;

    private int maxRange = 40;

    private int lvl;

    public Nebula(Handler handler) {
        super(handler, 4);

        cooldown = 1000;
        duration = 2700;

        animation = new Animation(Assets.nebula, 20);
    }

    @Override
    public void setupSkill(int lvl) {
        this.lvl = lvl;

        animation.setFrame(0);

        Px = handler.getEntityManager().getPlayer().getX();
        Py = handler.getEntityManager().getPlayer().getY();
    }

    @Override
    public void renderEffect(Canvas canvas) {
        for(int x = 0; x < maxRange; x++){
            for(int y = 0; y < maxRange; y++){

                int tx = (int) ((x - maxRange / 2) * Tile.TILEWIDTH + Px);
                int ty = (int) ((y - maxRange / 2) * Tile.TILEWIDTH + Py);

                int distance = Utils.getDistance(tx, ty, (int) Px, (int) Py);
                if(distance > currentRange || distance < currentRange - Tile.TILEWIDTH * 2)
                    continue;

                RectF bounds = new RectF(tx,
                        ty,
                        tx + Tile.TILEWIDTH,
                        ty + Tile.TILEWIDTH);

                canvas.drawBitmap(animation.GetCurrentFrame(),
                        null,
                        new RectF(tx - handler.getCamera().getxOffset(),
                                ty - handler.getCamera().getyOffset(),
                                tx - handler.getCamera().getxOffset() + Tile.TILEWIDTH,
                                ty - handler.getCamera().getyOffset() + Tile.TILEWIDTH),
                        null);

                ArrayList<Entity> entities = new ArrayList<>(handler.getEntityManager().getEntities());
                for(Entity e : entities){
                    if(e == handler.getEntityManager().getPlayer())
                        continue;

                    if(bounds.contains(e.getX(), e.getY())){
                        hurtEntity(e, 1.5f, true, lvl);
                    }
                }
            }
        }
    }

    @Override
    public void makeEffect() {
        currentRange += 20;
    }

    @Override
    public void endEffect() {
        currentRange = 0;
    }
}
