package com.example.mmo.MMO.World.Portals;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;

public class Portal {

    private final Handler handler;
    private float x, y;
    private final int worldID;

    private Animation animation;

    private RectF bounds;

    private long lastTime, timer;

    private final int enterTime = 3000; // player need to stay in portal for 3s

    private boolean firstTime = false;

    public Portal(Handler handler, float x, float y, int worldID){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.worldID = worldID;

        animation = new Animation(Assets.portal, 50);

        bounds = new RectF(x, y, x + Tile.TILEWIDTH * 2, y + Tile.TILEWIDTH * 2);

        lastTime = System.currentTimeMillis();
    }

    public void tick(){
        animation.tick();

        if(bounds.contains(handler.getEntityManager().getPlayer().getX(), handler.getEntityManager().getPlayer().getY())){
            if(firstTime) {
                lastTime = System.currentTimeMillis();
                firstTime = false;
            }

            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if(timer > enterTime) {
                event();
                timer = 0;
                firstTime = true;
            }
        }else{
            timer = 0;
            firstTime = true;
        }
    }

    public void render(Canvas canvas){
        canvas.drawBitmap(animation.GetCurrentFrame(), null, new RectF(x - handler.getCamera().getxOffset(),
                y - handler.getCamera().getyOffset(),
                x + Tile.TILEWIDTH * 2 - handler.getCamera().getxOffset(),
                y + Tile.TILEWIDTH * 2 - handler.getCamera().getyOffset()), null);
    }

    public void event(){
        handler.getWorldManager().setCurrentWorld(worldID);
    }
}
