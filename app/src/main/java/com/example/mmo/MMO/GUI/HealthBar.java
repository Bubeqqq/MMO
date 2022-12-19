package com.example.mmo.MMO.GUI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Handler;

public class HealthBar {

    private final int width, height;
    private float x, y;
    private float percentage;

    private Bitmap healthBar;

    private Handler handler;

    private Bitmap fill;

    private RectF bounds;

    public HealthBar(Handler handler, int x, int y, int width, int height){
        this.handler = handler;

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        bounds = new RectF(x, y, x + width, y + height);

        fill = Assets.health;
        healthBar = fill;
    }

    public void Render(Canvas canvas){
        canvas.drawBitmap(Assets.bar, null, new RectF(x, y, x + width, y + height), null);
        canvas.drawBitmap(healthBar, null, new RectF(x, y, x + width * percentage, y + height), null);
    }

    public void RenderAtPoint(Canvas canvas, float x, float y, float width, float height){
        canvas.drawBitmap(Assets.bar, null, new RectF(x, y, x + width, y + height), null);
        canvas.drawBitmap(healthBar, null, new RectF(x, y, x + width * percentage, y + height), null);
    }

    public void Refresh(float health, float maxHealth){
        percentage = health / maxHealth;

        if(percentage > 1)
            percentage = 1;

        if(percentage < 0.01)
            percentage = 0.01f;

        healthBar = Bitmap.createBitmap(fill, 0, 0, (int) (fill.getWidth() * percentage), fill.getHeight());
    }

    public void centerOnEntity(Entity e){
        y = e.getY() - height - 20 - handler.getCamera().getyOffset();
        x = e.getX() - handler.getCamera().getxOffset();
    }

    public void touch(MotionEvent event){
       if(bounds.contains(event.getX(), event.getY())){
           afterTouch(event);
       }
    }

    public void afterTouch(MotionEvent event){}

    public void setFill(Bitmap fill) {
        this.fill = fill;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
