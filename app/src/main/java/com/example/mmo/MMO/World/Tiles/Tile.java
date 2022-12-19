package com.example.mmo.MMO.World.Tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

public class Tile {

    public static int TILEWIDTH;

    protected boolean isSolid = true, antyBounds;

    private final int tilesPerScreenHeight = 10;

    protected Bitmap texture;

    protected RectF bounds;

    protected Handler handler;

    //
    public static boolean debug = false;

    public Tile(int ID, Bitmap texture, Handler handler, boolean firstLayer){
        TILEWIDTH = handler.getWindowSize().y / tilesPerScreenHeight;

        if(firstLayer) {
            handler.getTileManager().getTiles()[ID] = this;
        }
        else {
            handler.getTileManager().getSecondLayer()[ID] = this;
        }


        this.handler = handler;
        this.texture = texture;

        bounds = new RectF(0, 0 , TILEWIDTH, TILEWIDTH);
    }

    public void render(Canvas canvas, int x, int y){
        canvas.drawBitmap(texture,
                        null,
                            new RectF(x * TILEWIDTH - handler.getCamera().getxOffset(),
                                    y * TILEWIDTH - handler.getCamera().getyOffset(),
                                    (x + 1) * TILEWIDTH - handler.getCamera().getxOffset(),
                                    (y + 1) * TILEWIDTH - handler.getCamera().getyOffset()),
                                        null);

        //debug

        if(isSolid && debug){
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            if(antyBounds)
                p.setColor(handler.getGame().getResources().getColor(R.color.yellow));
            else
                p.setColor(handler.getGame().getResources().getColor(R.color.red));
            canvas.drawRect(getBounds(x * TILEWIDTH - handler.getCamera().getxOffset(), y * TILEWIDTH - handler.getCamera().getyOffset()), p);
        }
    }

    public RectF getBounds(float x, float y){
        return new RectF(x + bounds.left, y + bounds.top, x + bounds.right, y + bounds.bottom);
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public void setBounds(RectF bounds, boolean anty) {
        this.bounds = bounds;
        this.antyBounds = anty;
    }

    public boolean isAntyBounds() {
        return antyBounds;
    }
}
