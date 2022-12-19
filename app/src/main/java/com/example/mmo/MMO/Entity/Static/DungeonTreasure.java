package com.example.mmo.MMO.Entity.Static;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Dungeons.TreasurePattern;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;

import java.util.regex.Pattern;

public class DungeonTreasure extends Entity {

    private Bitmap[] texture;

    private TreasurePattern pattern;

    private Boolean anim = false, opened = false;

    private Animation animation;

    public DungeonTreasure(Handler handler, int x, int y, Bitmap[] texture, TreasurePattern pattern) {
        super(handler, Creature.DEFAULTWIDTH * 2, Creature.DEFAULTHEIGHT * 2, x, y);

        this.texture = texture;
        this.pattern = pattern;

        event = true;

        animation = new Animation(texture, 50);
    }

    @Override
    protected void render(Canvas canvas) {
        canvas.drawBitmap(animation.GetCurrentFrame(),
                null,
                new RectF(x - handler.getCamera().getxOffset(),
                        y - handler.getCamera().getyOffset(),
                        x + width - handler.getCamera().getxOffset(),
                        y + height - handler.getCamera().getyOffset()),
                null);
    }

    @Override
    public void postRender(Canvas canvas) {

    }

    @Override
    protected void tick() {
        if(anim){
            animation.tick();

            if(animation.getIndex() == 3) //if last frame
                anim = false; //stop animation
        }
    }

    @Override
    public void animationTick() {

    }

    @Override
    public void Touch(MotionEvent event) {

    }

    @Override
    public void die() {

    }

    @Override
    public void event() {
        if(opened)
            return;

        canDamage = false;
        collisions = false;

        if(pattern != null)
            pattern.open(handler);

        opened = true;
        anim = true;
    }
}
