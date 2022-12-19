package com.example.mmo.MMO.Skills;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class IceBomb extends Skill{

    private float Px, Py;

    private final int range = 500;

    private final int mobsToHit = 3;

    private int mobsHittet;

    private int lvl;

    private ArrayList<Entity> mobs;

    public IceBomb(Handler handler) {
        super(handler, 2);

        cooldown = 1000;
        duration = 1500;

        mobs = new ArrayList<>();

        animation = new Animation(Assets.ice, 40);
    }

    @Override
    public void setupSkill(int lvl) {
        this.lvl = lvl;

        Px = handler.getEntityManager().getPlayer().getX();
        Py = handler.getEntityManager().getPlayer().getY();

        animation.setFrame(0);

        Log.println(Log.ASSERT, "IceBomb", "Skill Activated");
    }

    @Override
    public void renderEffect(Canvas canvas) {
        try{
            for (Entity e : mobs){
                canvas.drawBitmap(animation.GetCurrentFrame(),
                        null,
                        new RectF(e.getX() - handler.getCamera().getxOffset() - Tile.TILEWIDTH,
                                e.getY() - handler.getCamera().getyOffset() - Tile.TILEWIDTH,
                                e.getX() + e.getWidth() - handler.getCamera().getxOffset() + Tile.TILEWIDTH,
                                e.getY() + e.getHeight() - handler.getCamera().getyOffset() + Tile.TILEWIDTH),
                        null);
            }
        }catch (ConcurrentModificationException e){
            return;
        }
    }

    @Override
    public void makeEffect() {
        if(mobsHittet >= mobsToHit)
            return;

        for (Entity e : handler.getEntityManager().getEntities()) {
            if (e == handler.getEntityManager().getPlayer())
                continue;

            boolean alreadyHit = false;
            for(Entity mob : mobs){
                if(e == mob) {
                    alreadyHit = true;
                    break;
                }
            }

            if(alreadyHit)
                continue;

            if (Utils.getDistance((int) e.getX(), (int) e.getY(), (int) Px, (int) Py) <= range) {
                hurtEntity(e, 2, true, lvl);
                mobsHittet++;
                mobs.add(e);
                return;
            }
        }
    }

    @Override
    public void endEffect() {
        mobs.clear();
        mobsHittet = 0;
    }
}
