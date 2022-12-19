package com.example.mmo.MMO.Entity.Creatures;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Containers.ContainerManager;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.GUI.HealthBar;
import com.example.mmo.MMO.GameLoop;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.DroppedItem;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Player extends Creature {

    //Base stats

    public static final float BASICHEALTH = 100,
                                BASICRANGE = 150,
                                BASEHEALDELAY = 2,
                                BASICHEALING = 2;

    //

    private boolean walk;

    private final int pickUpRange = 150;

    private long lastTime, timer = 0;

    private int xp, xpToLvl = 10;

    private final float xpIncrease = 1.02f;

    private HealthBar xpBar;

    private Paint barValues;

    private boolean showXpPercent, showHealthPercent;

    public Player(Handler handler, int width, int height, int x, int y) {
        super(handler, width, height, x, y, Assets.player);

        speed = 0.4f;

        lastTime = System.currentTimeMillis();

        healthBar = new HealthBar(handler, Tile.TILEWIDTH / 2, (int) (Tile.TILEWIDTH * 0.1f), (int) (Container.slotSize * 6.5f), (int) (Container.slotSize * 1.3f)){
            @Override
            public void afterTouch(MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_UP)
                    showHealthPercent = !showHealthPercent;
            }
        };

        xpBar = new HealthBar(handler, Tile.TILEWIDTH / 2, (int) (Tile.TILEWIDTH * 1.1f), (int) (Container.slotSize * 6.5f), (int) (Container.slotSize * 1.3f)){
            @Override
            public void afterTouch(MotionEvent event){ showXpPercent = true; }
        };
        xpBar.setFill(Assets.expBar);

        barValues = new Paint();
        barValues.setColor(handler.getGame().getResources().getColor(R.color.white));
        barValues.setTextSize(Tile.TILEWIDTH / 2);
        barValues.setTextAlign(Paint.Align.CENTER);
        barValues.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private RectF getPickUpRange(){
        return new RectF(x - pickUpRange, y - pickUpRange, x + pickUpRange, y + pickUpRange);
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
    }

    @Override
    public void postRender(Canvas canvas){
        healthBar.Render(canvas);
        xpBar.Render(canvas);

        if(!showHealthPercent)
            canvas.drawText(health + " / " + maxHealth, (healthBar.getX() + healthBar.getWidth()) / 2, healthBar.getY() + healthBar.getHeight() * 0.625f, barValues);
        else
            canvas.drawText(String.format("%.2f", (float) health / maxHealth * 100) + "%", (healthBar.getX() + healthBar.getWidth()) / 2, healthBar.getY() + healthBar.getHeight() * 0.625f, barValues);


        if(!showXpPercent)
            canvas.drawText("LVL : " + lvl, (xpBar.getX() + xpBar.getWidth()) / 2, xpBar.getY() + xpBar.getHeight() * 0.625f, barValues);
        else
            canvas.drawText(String.format("%.2f", (float) xp / xpToLvl * 100) + "%", (xpBar.getX() + xpBar.getWidth()) / 2, xpBar.getY() + xpBar.getHeight() * 0.625f, barValues);

    }

    public void pickUp(){
        for(DroppedItem i : handler.getItemManager().getDroppedItems()){
            if(getPickUpRange().contains(i.getX(), i.getY())){
                if(handler.getEq().getEq().addItem(new ContainerItem(i.getID(), handler, i.getAmount(), i.getLvl(), i.getBonuses()))) {
                    handler.getItemManager().removeDroppedItem(i);
                    return;
                }
            }
        }
    }

    @Override
    public void Move(float x, float y){
        MoveX(x * speed);
        MoveY(y * speed);
        this.moveX = x * speed;
        this.moveY = y * speed;


        //this.x += moveX;
        //this.y += moveY;

        handler.getEntityManager().setLoadedEntities();
        handler.getItemManager().setLoadedDroppedItems();
    }

    @Override
    protected void tick() {
        //natural regen

        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer > handler.getStatistics().getHealthRegenTime() * 1000){
            timer = 0;
            addHealth((int) handler.getStatistics().getHealthRegen());
        }

        //move

        if(walk)
            Move(handler.getInputManager().getJoystick().getXOffset(), handler.getInputManager().getJoystick().getYOffset());

        //scan for dropped items

        boolean found = false;
        try {
            for (DroppedItem i : handler.getItemManager().getDroppedItems()) {
                if (getPickUpRange().contains(i.getX(), i.getY())) {
                    handler.getInputManager().getPickUpButton().setShow(true);
                    found = true;
                }
            }
        }catch (ConcurrentModificationException e){
            return;
        }

        if(!found)
            handler.getInputManager().getPickUpButton().setShow(false);
    }

    @Override
    public void Touch(MotionEvent event) {
        healthBar.touch(event);
        xpBar.touch(event);

        if(event.getAction() == MotionEvent.ACTION_UP) {
            showXpPercent = false;
            walk = false;
            moveX = 0;
            moveY = 0;

        }else
            walk = true;
    }

    @Override
    public void die() {
        //Log.println(Log.ASSERT, "Damage", "YOU LOSE");
        health = maxHealth;
    }

    @Override
    public void event(){}

    @Override
    public void hurt(int amount, boolean defense, boolean animation){
        if(animation)
            addHit();

        int dmg = Math.max(1, amount - (int) (handler.getStatistics().getDefense() / 2));
        health -= dmg;

        healthBar.Refresh(health, maxHealth);

        if(health <= 0)
            die();
    }

    public void attack(){
        Entity closest = null;
        float distance = 0;

        ArrayList<Entity> entities = new ArrayList<>(handler.getEntityManager().getEntities());

        for(Entity e : entities){
            if(e == this || !e.canDamage()) {
                Log.println(Log.ASSERT, "Damage", "cannot hit");
                continue;
            }
            int d = Utils.getDistance((int) x, (int) y, (int) e.getX(), (int) e.getY());
            if(d < handler.getStatistics().getRange()){
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

        if(closest.haveEvent())
            closest.event();
        else{
            float dmg = handler.getStatistics().getDamage();

            handler.getQuestManager().event(new QuestEvent(QuestEvent.ATTACK, -1, 1));
            handler.getQuestManager().event(new QuestEvent(QuestEvent.DAMAGEDEALT, -1, (int) dmg));

            closest.hurt((int) dmg, true, true);
            Log.println(Log.ASSERT, "Damage", String.valueOf(dmg));

            try {
                handler.getEntityManager().setSelectedEntity((Creature) closest);
            }catch (ClassCastException e){}

            return;
        }
        Log.println(Log.ASSERT, "Damage", "-----");
    }

    private void setXpToLvl(int lvl){
        xpToLvl = lvl * lvl * lvl + 10;
    }

    //setters

    public void addXP(int value){
        xp += value;

        while(xp >= xpToLvl){
            lvl++;
            xp -= xpToLvl;
            setXpToLvl(lvl);
        }

        xpBar.Refresh(xp, xpToLvl);
        Log.println(Log.ASSERT, "Player", xp + " / " + xpToLvl);
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
        setXpToLvl(lvl);

        Log.println(Log.ASSERT, "Player", "To lvl : " + xpToLvl);
    }

    public void setMaxHealth(int health){
        this.maxHealth = health;
        healthBar.Refresh(this.health, maxHealth);
    }

    public void addHealth(int health){
        if(health + this.health > maxHealth)
            this.health = maxHealth;
        else
            this.health += health;

        healthBar.Refresh(this.health, maxHealth);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setPosition(float spawnX, float spawnY) {
        x = spawnX;
        y = spawnY;
    }

    public int getXp() {
        return xp;
    }
}
