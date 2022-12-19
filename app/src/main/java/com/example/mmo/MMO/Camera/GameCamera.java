package com.example.mmo.MMO.Camera;

import android.graphics.Point;
import android.util.DisplayMetrics;

import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;

public class GameCamera {

    private Handler handler;

    public GameCamera(Handler handler){
        this.handler = handler;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    private float xOffset, yOffset;

    public void centerOnEntity(Entity e){
        Point size = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);

        xOffset = e.getX() - size.x / 2 + e.getWidth() / 2;
        yOffset = e.getY() - size.y / 2 + e.getHeight() / 2;
        checkBlankSpace();
    }

    public void checkBlankSpace() {
        if(xOffset < 0) {
            xOffset = 0;
        }else if(xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getGame().getWidth()) {
            xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getGame().getWidth();
        }

        if(yOffset < 0) {
            yOffset = 0;
        }else if(yOffset > handler.getWorld().getHeight() * Tile.TILEWIDTH - handler.getGame().getHeight()) {
            yOffset = handler.getWorld().getHeight() * Tile.TILEWIDTH - handler.getGame().getHeight();
        }
    }
}
