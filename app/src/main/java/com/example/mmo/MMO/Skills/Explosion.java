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

public class Explosion extends Skill{

    private float Px, Py;

    private int range = 100;

    private int damageRange = 800;

    private int lvl;

    public Explosion(Handler handler) {
        super(handler, 0);
        cooldown = 5000;
        duration = 2500;

        animation = new Animation(Assets.explosion, 10);
    }

    @Override
    public void setupSkill(int lvl) {
        this.lvl = lvl;

        Px = handler.getEntityManager().getPlayer().getX();
        Py = handler.getEntityManager().getPlayer().getY();
        animation.setFrame(0);
    }

    @Override
    public void renderEffect(Canvas canvas) {
        for(int x = (int) ((Px - range) / Tile.TILEWIDTH); x < ((Px + range) / Tile.TILEWIDTH); x++){
            for(int y = (int) ((Py - range) / Tile.TILEWIDTH); y < ((Py + range) / Tile.TILEWIDTH); y++){
                canvas.drawBitmap(animation.GetCurrentFrame(),
                        null,
                        new RectF(x * Tile.TILEWIDTH - handler.getCamera().getxOffset(),
                                y * Tile.TILEWIDTH - handler.getCamera().getyOffset(),
                                (x + 2) * Tile.TILEWIDTH - handler.getCamera().getxOffset(),
                                (y + 2) * Tile.TILEWIDTH - handler.getCamera().getyOffset()),
                        null);
            }
        }

        try{
            for(Entity e : handler.getEntityManager().getEntities()){
                if(e == handler.getEntityManager().getPlayer())
                    continue;

                if(Utils.getDistance((int) Px, (int) Py, (int) e.getX(), (int) e.getY()) > damageRange) {
                    continue;
                }

                canvas.drawBitmap(animation.GetCurrentFrame(),
                        null,
                        new RectF(e.getX() - handler.getCamera().getxOffset(),
                                e.getY() - handler.getCamera().getyOffset(),
                                e.getX() + e.getWidth() - handler.getCamera().getxOffset(),
                                e.getY() + e.getHeight() - handler.getCamera().getyOffset()),
                        null);
                hurtEntity(e, 0.01f, false, lvl);
            }
        }catch (ConcurrentModificationException e){
            return;
        }
    }

    @Override
    public void makeEffect() {

    }

    @Override
    public void endEffect() {
    }
}
