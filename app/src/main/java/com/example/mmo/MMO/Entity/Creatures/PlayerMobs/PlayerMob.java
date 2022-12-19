package com.example.mmo.MMO.Entity.Creatures.PlayerMobs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.GUI.HealthBar;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;

import java.util.ArrayList;

public class PlayerMob extends Creature {

    private long lastTime, dieTimer, attackTimer, awayFromPlayer;

    protected int attackDelay = 1000, range = 200, awayLimit = 4000;

    protected float damagePercent = 0.5f;

    protected boolean ignoreDefence = false;

    public PlayerMob(Handler handler, int width, int height, int x, int y, Bitmap[] textures) {
        super(handler, width, height, x, y, textures);

        healthBar = new HealthBar(handler, x, y, width, 20);
        healthBar.setFill(Assets.expBar);
        healthBar.Refresh(health, maxHealth);

        canDamage = false;

        loadAlways = true;

        setHealth(300);
    }

    @Override
    protected void render(Canvas canvas) {
        canvas.drawBitmap(getCurrentTexture(),
                null,
                new RectF(x - handler.getCamera().getxOffset(),
                        y - handler.getCamera().getyOffset(),
                        x + width - handler.getCamera().getxOffset(),
                        y + height - handler.getCamera().getyOffset()),
                null);

        //health

        healthBar.centerOnEntity(this);
        healthBar.Render(canvas);
    }

    @Override
    public void postRender(Canvas canvas) {

    }

    @Override
    protected void tick() {
        dieTimer += System.currentTimeMillis() - lastTime;
        attackTimer += System.currentTimeMillis() - lastTime;

        if(dieTimer >= 1000){
            dieTimer = 0;
            hurt(1, false, false);
            healthBar.Refresh(health, maxHealth);
        }

        if(attackTimer >= attackDelay){
            attack();
            attackTimer = 0;
        }

        if(Utils.getDistance((int)x, (int)y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range){
            moveX = 0;//stop walking animation animation
            moveY = 0;
            awayFromPlayer = 0;
            lastTime = System.currentTimeMillis();
            return;
        }
        awayFromPlayer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(awayFromPlayer >= awayLimit){
            while (Utils.getDistance((int)x, (int)y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) > range){
                float dx = handler.getEntityManager().getPlayer().getX() - x;
                float dy = handler.getEntityManager().getPlayer().getY() - y;
                float distance = (float) Math.sqrt(dx*dx + dy*dy);
                if (distance > 0) {
                    dx = dx * speed / distance;
                    dy = dy * speed / distance;
                }
                x += dx;
                y += dy;
            }
            awayFromPlayer = 0;
        }

        float dx = handler.getEntityManager().getPlayer().getX() - x;
        float dy = handler.getEntityManager().getPlayer().getY() - y;
        float distance = (float) Math.sqrt(dx*dx + dy*dy);
        if (distance > 0) {
            dx = dx * speed / distance;
            dy = dy * speed / distance;
        }
        Move(dx, dy);
    }

    @Override
    public void Touch(MotionEvent event) {

    }

    @Override
    public void die() {
        handler.getEntityManager().removeEntity(this);
        handler.addAnnouncement("Your Mob Disappear");
    }

    @Override
    public void event() {

    }

    private void attack(){
        Entity closest = null;
        float distance = 0;

        ArrayList<Entity> entities = new ArrayList<>(handler.getEntityManager().getEntities());

        for(Entity e : entities){
            if(e == this || !e.canDamage() || e == handler.getEntityManager().getPlayer())
                continue;

            int d = Utils.getDistance((int) x, (int) y, (int) e.getX(), (int) e.getY());
            if(d < range){
                if(closest == null) {
                    closest = e;
                    distance = d;
                }
                else{
                    if(distance > d){
                        closest = e;
                        distance = d;
                    }
                }
            }
        }

        if(closest == null)
            return;

        if(!closest.haveEvent())
            closest.hurt((int) (handler.getStatistics().getDamage() * damagePercent), !ignoreDefence, true);
    }

    public void setAttackDelay(int attackDelay) {
        this.attackDelay = attackDelay;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setAwayLimit(int awayLimit) {
        this.awayLimit = awayLimit;
    }

    public void setDamagePercent(float damagePercent) {
        this.damagePercent = damagePercent;
    }

    public void setIgnoreDefence(boolean ignoreDefence) {
        this.ignoreDefence = ignoreDefence;
    }
}
