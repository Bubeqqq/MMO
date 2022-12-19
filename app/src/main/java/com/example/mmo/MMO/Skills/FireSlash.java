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

public class FireSlash extends Skill{

    private float Px, Py;

    private int range = 7;

    private int lvl;

    public FireSlash(Handler handler) {
        super(handler, 7);

        cooldown = 1000;
        duration = 1300;

        animation = new Animation(Assets.fireSlash, 3);
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
        for(int x = 0; x < range; x++){
            for(int y = 0; y < range; y++){

                int tx = (int) ((x - range / 2) * Tile.TILEWIDTH + Px);
                int ty = (int) ((y - range / 2) * Tile.TILEWIDTH + Py);

                int distance = Utils.getDistance(tx, ty, (int) Px, (int) Py);
                if(distance > range / 2 * Tile.TILEWIDTH)
                    continue;

                RectF bounds = new RectF(tx - Tile.TILEWIDTH / 2,
                        ty - Tile.TILEWIDTH / 2,
                        tx + Tile.TILEWIDTH * 1.5f,
                        ty + Tile.TILEWIDTH * 1.5f);

                canvas.drawBitmap(animation.GetCurrentFrame(),
                        null,
                        new RectF(tx - handler.getCamera().getxOffset() - Tile.TILEWIDTH / 2,
                                ty - handler.getCamera().getyOffset() - Tile.TILEWIDTH / 2,
                                tx - handler.getCamera().getxOffset() + Tile.TILEWIDTH * 1.5f,
                                ty - handler.getCamera().getyOffset() + Tile.TILEWIDTH * 1.5f),
                        null);

                ArrayList<Entity> entities = new ArrayList<>(handler.getEntityManager().getEntities());
                for(Entity e : entities){
                    if(e == handler.getEntityManager().getPlayer())
                        continue;

                    if(bounds.contains(e.getX(), e.getY())){
                       hurtEntity(e, 0.4f, true, lvl);
                    }
                }
            }
        }
    }

    @Override
    public void makeEffect() {

    }

    @Override
    public void endEffect() {

    }
}
