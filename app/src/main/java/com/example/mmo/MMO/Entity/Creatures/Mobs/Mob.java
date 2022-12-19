package com.example.mmo.MMO.Entity.Creatures.Mobs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Zombie;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.GUI.HealthBar;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Quests.QuestManager;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Portals.Portal;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.R;

import java.util.ArrayList;
import java.util.Random;

public abstract class Mob extends Creature {

    //static

    public static final int ZOMBIE = 0,
                            SKELETONSOLDIER = 1,
                            MAGE = 2,
                            PUMPKIN = 3, DARKBALL = 4, GHOST = 5, PINKGOBLIN = 6, GOBLINKING = 7;

    //

    protected int damage = 2, range = 150, defense;

    protected ArrayList<Drop> drops;

    protected Random random;

    protected final int spawnX, spawnY; //coordinates where mob has spawned

    protected Paint paint;

    private final MobSpawner spawner;

    protected int ID;

    private long lastTime, timer;
    protected long attackDelay = 1000;

    protected int exp = 1;

    private boolean autoAgro = false;

    protected int spawnPortalTo = -1;

    //walk

    protected boolean freeWalk = true;

    protected float xRandom, yRandom, xDirection, yDirection; //free walk random moves

    protected final int walkRange = 200;

    public Mob(Handler handler, int width, int height, int x, int y, Bitmap[] textures, MobSpawner spawner, int ID) {
        super(handler, width, height, x, y, textures);

        this.spawner = spawner;
        this.ID = ID;
        spawnX = x;
        spawnY = y;

        drops = new ArrayList<>();
        random = new Random();

        healthBar = new HealthBar(handler, x, y, width, height / 8);
        healthBar.Refresh(health, maxHealth);

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.black));
        paint.setFakeBoldText(true);
        paint.setTextSize(25);

        lastTime = System.currentTimeMillis();
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
    public void postRender(Canvas canvas){}

    @Override
    public void hurt(int amount, boolean defense, boolean animation){
        int dmg;

        if(spawner != null){
            for(Mob m : spawner.getMobs())
                m.setFreeWalk(false);
        }else
            setFreeWalk(false);

        if(animation)
            addHit();

        if(health <= 0)
            return;

        if(!defense)
            dmg = Math.max(1, amount);
        else
            dmg = Math.max(1, amount - this.defense / 2);

        health -= dmg;

        healthBar.Refresh(health, maxHealth);
    }

    @Override
    protected void tick() {
        //walk

        if(autoAgro && freeWalk) {
            if (Utils.getDistance((int) x, (int) y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range) {
                freeWalk = false;
            }
        }

        if(freeWalk){
            freeWalk();
        }else {
            attackWalk();

            //attack
            if(Utils.getDistance((int)x, (int)y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range){
                timer += System.currentTimeMillis() - lastTime;

                if(timer >= attackDelay){
                    timer = 0;
                    handler.getEntityManager().getPlayer().hurt(damage, true, true);
                }
            }
        }
        //keep timer updated
        lastTime = System.currentTimeMillis();

        if(health <= 0)
            die();
    }

    protected void freeWalk(){
        if(xRandom <= 0 && yRandom <= 0){
            moveX = 0;
            moveY = 0;
            if(random.nextInt(100) < 10){
                xRandom = random.nextInt(Tile.TILEWIDTH * 2) + Tile.TILEWIDTH;
                yRandom = random.nextInt(Tile.TILEWIDTH * 2) + Tile.TILEWIDTH;
                xDirection = random.nextInt(3) - 1;
                yDirection = random.nextInt(3) - 1;
            }
        }
        if(xRandom > 0){
            float tx = xDirection;

            if(Utils.getDistance((int) (tx + x), 0, spawnX, 0) <= walkRange){
                MoveX(tx);
                xRandom -= 1;
            }else
                xRandom = -1;
        }

        if(yRandom > 0){
            float ty = yDirection;

            if(Utils.getDistance(0, (int) (ty + y), 0, spawnY) <= walkRange) {
                MoveY(ty);
                yRandom -= 1;
            }else
                yRandom = -1;
        }
    }

    protected void attackWalk(){
        if(Utils.getDistance((int)x, (int)y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range){
            moveX = 0;//stop walking animation animation
            moveY = 0;
            return;
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
        if(getBounds().contains(event.getX() + handler.getCamera().getxOffset(), event.getY() + handler.getCamera().getyOffset()))
            handler.getEntityManager().setSelectedEntity(this);
    }

    @Override
    public void die() {
        if(!drops.isEmpty()){
            for(Drop d : drops){
                if(random.nextInt(100) < d.getPercentage() + handler.getStatistics().getLuck() / 100 * d.getPercentage()){
                    handler.getItemManager().addDroppedItem(x, y, d.getAmount(), d.getID(), d.getLvl(), null);
                }
            }
        }
        handler.getEntityManager().removeEntity(this);

        handler.getEntityManager().getPlayer().addXP(exp);

        if(spawner != null)
            spawner.removeMob(this);

        handler.getQuestManager().event(new QuestEvent(QuestEvent.MOBKILLED, ID, 1));

        if(spawnPortalTo != -1){
            handler.getWorld().getPortals().addPortal(new Portal(handler, x, y, spawnPortalTo));
        }
    }

    @Override
    public void event(){}

    protected void addDrop(Drop d){
        drops.add(d);
    }

    public int getID() {
        return ID;
    }

    public void setFreeWalk(boolean freeWalk) {
        this.freeWalk = freeWalk;
    }

    public void setAutoAgro(boolean autoAgro) {
        this.autoAgro = autoAgro;
    }

    public void setSpawnPortalTo(int spawnPortalTo) {
        this.spawnPortalTo = spawnPortalTo;
    }
}
