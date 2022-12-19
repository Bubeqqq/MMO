package com.example.mmo.MMO.Skills.Passive;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Skills.Skill;
import com.example.mmo.MMO.World.Tiles.Tile;

public abstract class Passive extends Skill {

    private float Px, Py;

    public Passive(Handler handler, int ID) {
        super(handler, ID);

        duration = 1000;
    }

    @Override
    public void renderEffect(Canvas canvas) {
        Px = handler.getEntityManager().getPlayer().getX();
        Py = handler.getEntityManager().getPlayer().getY();

        canvas.drawBitmap(animation.GetCurrentFrame(),
                null,
                new RectF(Px - Tile.TILEWIDTH - handler.getCamera().getxOffset(),
                        Py - Tile.TILEWIDTH - handler.getCamera().getyOffset(),
                        Px + handler.getEntityManager().getPlayer().getWidth() + Tile.TILEWIDTH - handler.getCamera().getxOffset(),
                        Py + Tile.TILEWIDTH * 2 - handler.getCamera().getyOffset()),
                null);
    }

    @Override
    public void makeEffect() {

    }

    @Override
    public void endEffect() {

    }
}
