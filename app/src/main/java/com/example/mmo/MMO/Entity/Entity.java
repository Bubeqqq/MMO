package com.example.mmo.MMO.Entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public abstract class Entity {

    protected float x, y;

    protected int health = 100, width, height;

    protected Handler handler;

    protected boolean event = false, collisions = true;

    protected RectF bounds;

    private ArrayList<Animation> hitAnimations;

    protected int maxHealth;

    protected int lvl = 1;

    protected boolean canDamage = true, loadAlways = false, canAttack = true;

    public Entity(Handler handler, int width, int height, int x, int y){
        this.handler = handler;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        maxHealth = health;

        bounds = new RectF();
        hitAnimations = new ArrayList<>();
    }

    public RectF getBounds(){
        bounds.left = x + width * 0.25f;
        bounds.top = y + height * 0.3f;
        bounds.right = x + width  * 0.75f;
        bounds.bottom = y + height;
        return bounds;
    }

    public void hurt(int amount, boolean defense, boolean animation){
        health -= amount;
        if(health <= 0)
            die();
    }

    public void entityTick(){
        tick();

        try {
            if(!hitAnimations.isEmpty()){
                Iterator<Animation> it = hitAnimations.iterator();
                while(it.hasNext()){
                    Animation a = it.next();
                    a.tick();

                    if(a.getIndex() >= 30)
                        it.remove();
                }
            }
        }catch (ConcurrentModificationException e){
            return;
        }
    }

    public void entityRender(Canvas canvas){
        render(canvas);
        /*Paint p = new Paint(); //render bounds
        p.setColor(handler.getGame().getResources().getColor(android.R.color.holo_red_dark));
        canvas.drawRect(new RectF(getBounds().left - handler.getCamera().getxOffset(),
                getBounds().top - handler.getCamera().getyOffset(),
                getBounds().right - handler.getCamera().getxOffset(),
                getBounds().bottom - handler.getCamera().getyOffset()), p);*/

        try {
            if(!hitAnimations.isEmpty()){
                Iterator<Animation> it = hitAnimations.iterator();
                while(it.hasNext()){
                    Animation a = it.next();

                    canvas.drawBitmap(a.GetCurrentFrame(),
                            null,
                            new RectF(x - width - handler.getCamera().getxOffset(),
                                    y - width - handler.getCamera().getyOffset(),
                                    x + width * 2 - handler.getCamera().getxOffset(),
                                    y + height * 2 - handler.getCamera().getyOffset()),
                            null);
                }
            }
        }catch (ConcurrentModificationException e){
            return;
        }
    }

    protected void addHit(){
        Animation a = new Animation(Assets.hit, 2);
        a.setFrameJump(3);
        hitAnimations.add(a);
    }

    //abstract

    protected abstract void render(Canvas canvas);

    public abstract void postRender(Canvas canvas);

    protected abstract void tick();

    public abstract void animationTick();

    public abstract void Touch(MotionEvent event);

    public abstract void die();

    public abstract void event();

    //getters

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHealth() {
        return health;
    }

    public boolean haveEvent() {
        return event;
    }

    public void setHealth(int health){
        maxHealth = health;
        this.health = health;
    }

    public int getLvl() {
        return lvl;
    }

    public boolean canDamage() {
        return canDamage;
    }

    public boolean isLoadAlways() {
        return loadAlways;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean haveCollisions() {
        return collisions;
    }
}
